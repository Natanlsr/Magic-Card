package com.magic.service

import com.magic.enums.GameStatusEnum
import com.magic.enums.GameTypeEnum
import com.magic.enums.MovementEnum
import com.magic.enums.PlayerTypeEnum
import com.magic.extensions.setCardsInPlayers
import com.magic.model.Card
import com.magic.model.Game
import com.magic.model.Player
import com.magic.repository.GameRepository
import com.magic.service.enums.ExceptionEnum
import com.magic.service.exceptions.GameNotFoundException
import com.magic.service.exceptions.NotTurnPlayerException
import com.magic.service.exceptions.PlayerNotFoundException
import com.magic.service.movements.InitialMovement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
open class GameService
@Autowired constructor(
        val gameRepository: GameRepository,
        val cardService: CardService,
        val playerService: PlayerService,
        val template: SimpMessagingTemplate
) {

    @Transactional
    @Async
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
        template.convertAndSend("/topic/game/started", game)
    }

    @Transactional
    @Async
    open fun executeMovement(idGame: Int, idPlayer: Int, movementTypeEnum: MovementEnum, idCard: Int){
        val player = playerService.findPlayerById(idPlayer)
        val game = findGameById(idGame)

        val card = cardService.findCardById(idCard)
        val playerTurn = game.players[game.indexPlayerTurn]

        if (playerTurn.id != player.id) {
            throw NotTurnPlayerException(ExceptionEnum.NOT_TURN_PLAYER.name)
        }

        val movement: InitialMovement = InitialMovement(
                movementEnum = movementTypeEnum,
                players = game.players.filter { it -> it.id != player.id },
                playerRound = player,
                card = card
        )

        //Execute game logic's
        movement.checkMovementTypeAndProcess()
        checkAndSetStatusGame(game)
        checkAndActualizeDeckPlayersAndGame(player, card, game)
        game.indexPlayerTurn = (game.players.size)
        game.indexPlayerTurn = (game.indexPlayerTurn + 1) % game.players.size
        gameRepository.save(game)
    }

    fun cpuMovement(idGame: Int){
        val game  = findGameById(idGame)
        val player = game.players
            .find { it.playerType.name.equals(PlayerTypeEnum.COMPUTER) }
            ?: throw PlayerNotFoundException(ExceptionEnum.PLAYER_NOT_FOUND.name)
        val card = player.deck[0]

        executeMovement(idGame, player.id!!,MovementEnum.APPLY_CARD,card.id!!.toInt())
    }

    fun findGameById(id: Int): Game{
        return  gameRepository.findById(id)
            .orElseThrow{ throw GameNotFoundException(ExceptionEnum.GAME_NOT_FOUND.name) }
    }

    private fun checkAndSetStatusGame(game: Game) {
        val playersDead: List<Player> = game.players.filter { it -> it.life == 0 }

        when (playersDead.isEmpty()) {
            true -> {
                game.gameStatus = GameStatusEnum.FINISHED
                game.players.sortedBy { it.life }
            }
            false -> game.gameStatus = GameStatusEnum.PROGRESS
        }
    }

    private fun checkAndActualizeDeckPlayersAndGame(playerRound: Player, cardUsed: Card, game: Game) {
        when (playerRound.playerType) {
            PlayerTypeEnum.PLAYER -> game.cardsToBuyByPlayers.toMutableList().add(cardUsed)
            PlayerTypeEnum.COMPUTER -> game.cardsToBuyByComputer.toMutableList().add(cardUsed)
        }
        game.setCardsInPlayers(1)
    }

}