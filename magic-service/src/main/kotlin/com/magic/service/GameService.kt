package com.magic.service

import com.magic.enums.GameStatusEnum
import com.magic.enums.GameTypeEnum
import com.magic.enums.PlayerTypeEnum
import com.magic.extensions.setCardsInPlayers
import com.magic.model.Card
import com.magic.model.Game
import com.magic.model.Movement
import com.magic.model.Player
import com.magic.repository.GameRepository
import com.magic.repository.PlayerRepository
import com.magic.service.movements.InitialMovement
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Service
import java.lang.Exception
import javax.transaction.Transactional

@Service
open class GameService
@Autowired constructor(
    val cardService: CardService,
    val gameRepository: GameRepository,
    val playerRepository: PlayerRepository
){
    @Transactional
    open fun startVsCPU(player: Player): Game{
        val cpuPlayer = Player(name = "CPU",playerTypeEnum = PlayerTypeEnum.COMPUTER)

        val game = Game(
            gameType = GameTypeEnum.CPU,
            cardsToBuyByComputer = cardService.getCardsPlayers(PlayerTypeEnum.COMPUTER)!!,
            cardsToBuyByPlayers  = cardService.getCardsPlayers(PlayerTypeEnum.PLAYER)!!,
            players = listOf(player,cpuPlayer),
            gameStatus = GameStatusEnum.PROGRESS
        )
        game.setCardsInPlayers()

        playerRepository.save(player)
        playerRepository.save(cpuPlayer)
        gameRepository.save(game)
        return game
    }

    fun executeMovement(idGame: Int,player: Player, movement: Movement, card: Card): Game{
        val gameOptional = gameRepository.findById(idGame)

        val game = gameOptional.orElseGet { throw Exception() }

        val movement: InitialMovement = InitialMovement(
            movementEnum = movement.type!!,
            players = game.players.filter { it -> it.id != player.id },
            playerRound = player,
            card = card
        )

        //Execute game logic's
        movement.checkMovementTypeAndProcess()
        checkAndSetStatusGame(game)
        checkAndActualizeDeckPlayersAndGame(player,card,game)
        gameRepository.save(game)
        return game
    }

    private fun checkAndSetStatusGame(game: Game){
        val playersDead: List<Player> = game.players.filter { it -> it.life == 0 }

        when(playersDead.isEmpty()){
             true  -> {
                 game.gameStatus = GameStatusEnum.FINISHED
                 game.players.sortedBy { it.life }
             }
             false -> game.gameStatus = GameStatusEnum.PROGRESS
        }
    }

    private fun checkAndActualizeDeckPlayersAndGame(playerRound: Player, cardUsed: Card, game: Game){
        when(playerRound.playerTypeEnum){
            PlayerTypeEnum.PLAYER -> game.cardsToBuyByPlayers.toMutableList().add(cardUsed)
            PlayerTypeEnum.COMPUTER -> game.cardsToBuyByComputer.toMutableList().add(cardUsed)
        }
        game.setCardsInPlayers(1)
    }

}