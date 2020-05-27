package com.magic.service

import com.magic.enums.GameStatusEnum
import com.magic.enums.GameTypeEnum
import com.magic.enums.MovementEnum
import com.magic.enums.PlayerTypeEnum
import com.magic.extensions.canUseAnyCard
import com.magic.extensions.returnCardToDeck
import com.magic.extensions.setCardsInPlayers
import com.magic.model.Card
import com.magic.model.Game
import com.magic.model.Player
import com.magic.repository.GameRepository
import com.magic.service.converter.toGameResponse
import com.magic.service.enums.ExceptionEnum
import com.magic.service.exceptions.GameNotFoundException
import com.magic.service.exceptions.MovementNotAllowedException
import com.magic.service.exceptions.NotTurnPlayerException
import com.magic.service.exceptions.PlayerNotFoundException
import com.magic.service.movements.InitialMovement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.RuntimeException
import javax.transaction.Transactional


@Service
open class GameService
@Autowired constructor(
        val gameRepository: GameRepository,
        val cardService: CardService,
        val playerService: PlayerService,
        val template: SimpMessagingTemplate
) {
    @Async
    @Transactional
    open fun startVsCPU(player: Player) {
        val cpuPlayer = Player(name = "CPU", playerType = PlayerTypeEnum.COMPUTER)

        val game = Game(
                gameType = GameTypeEnum.CPU,
                cardsToBuyByComputer = cardService.getCardsPlayers(PlayerTypeEnum.COMPUTER)!!,
                cardsToBuyByPlayers = cardService.getCardsPlayers(PlayerTypeEnum.PLAYER)!!,
                players = listOf(player, cpuPlayer),
                gameStatus = GameStatusEnum.PROGRESS
        )
        game.setCardsInPlayers()

        playerService.savePlayer(player)
        playerService.savePlayer(cpuPlayer)
        gameRepository.save(game)
        template.convertAndSend("/topic/game/started", game.toGameResponse())
    }

    @Async
    @Transactional
    open fun executePlayerMovement(idGame: Int, idPlayer: Int, movementTypeEnum: MovementEnum, idCard: Int) {
        try {
            val game = executeMovement(idGame, idPlayer, movementTypeEnum, idCard)
            template.convertAndSend("/topic/game/moviment/player/executed", game.toGameResponse())
            if (game.gameStatus !== GameStatusEnum.FINISHED) {
                cpuMovement(idGame)
            }
        } catch (exception: RuntimeException) {
            template.convertAndSend("/topic/erros", exception)
        }
    }


    @Transactional
    open fun executeMovement(idGame: Int, idPlayer: Int, movementTypeEnum: MovementEnum, idCard: Int = -1): Game {
        val player = playerService.findPlayerById(idPlayer)
        val game = findGameById(idGame)


        val card = if (idCard != -1)
            cardService.findCardById(idCard)
        else null

        if (card != null && card !in player.deck)
            throw MovementNotAllowedException(ExceptionEnum.MOVEMENT_NOT_ALLOWED.message)

        val playerTurn = game.players[game.indexPlayerTurn]

        if (playerTurn.id != player.id)
            throw NotTurnPlayerException(ExceptionEnum.NOT_TURN_PLAYER.message)


        val movement: InitialMovement = InitialMovement(
                movementEnum = movementTypeEnum,
                players = game.players.filter { it -> it.id != player.id },
                playerRound = player,
                card = card
        )

        //Execute game logic's
        movement.checkMovementTypeAndProcess()
        checkAndSetStatusGame(game)

        if (card != null)
            checkAndActualizeDeckPlayersAndGame(player, card, game)

        game.indexPlayerTurn = (game.indexPlayerTurn + 1) % game.players.size

        return gameRepository.save(game)
    }

    @Async
    @Transactional
    open fun cpuMovement(idGame: Int) {
        val game = findGameById(idGame)
        var movement = MovementEnum.APPLY_CARD

        val player = game.players
                .find { it.playerType.name.equals(PlayerTypeEnum.COMPUTER.name) }
                ?: throw PlayerNotFoundException(ExceptionEnum.PLAYER_NOT_FOUND.message)

        var idCard: Int = -1

        player.canUseAnyCard().let {
            if (it == null) {
                movement = MovementEnum.JUMP_ROUND
                idCard = -1
            } else {
                idCard = it.id!!
            }
        }

        executeMovement(idGame, player.id!!, movement, idCard)
        template.convertAndSend("/topic/game/moviment/cpu/executed", game.toGameResponse())
    }

    fun findGameById(id: Int): Game {
        return gameRepository.findById(id)
                .orElseThrow { throw GameNotFoundException(ExceptionEnum.GAME_NOT_FOUND.message) }
    }

    private fun checkAndSetStatusGame(game: Game) {
        val playersDead: List<Player> = game.players.filter { it -> it.life == 0 }

        when (playersDead.isNotEmpty()) {
            true -> {
                game.gameStatus = GameStatusEnum.FINISHED
                game.players.sortedBy { it.life }
            }
            false -> game.gameStatus = GameStatusEnum.PROGRESS
        }
    }

    private fun checkAndActualizeDeckPlayersAndGame(playerRound: Player, cardUsed: Card, game: Game) {
        game.setCardsInPlayers(1)
        game.returnCardToDeck(cardUsed)
    }


}