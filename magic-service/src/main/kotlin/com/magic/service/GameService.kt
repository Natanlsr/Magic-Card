package com.magic.service

import com.magic.enums.GameStatusEnum
import com.magic.enums.GameTypeEnum
import com.magic.enums.MovementEnum
import com.magic.enums.PlayerTypeEnum
import com.magic.extensions.setCardsInPlayers
import com.magic.model.Card
import com.magic.model.Game
import com.magic.model.Movement
import com.magic.model.Player
import com.magic.model.PlayerType
import com.magic.service.movements.AplyCardMovement
import com.magic.service.movements.InitialMovement
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Service

@Service
class GameService
@Autowired constructor(
    val cardService: CardService
){
    private var gameGlobal: Game? = null

    fun startVsCPU(player: Player): Game{

        val cpuPlayer = Player(id = 1,name = "CPU",playerTypeEnum = PlayerTypeEnum.COMPUTER)

        val game = Game(
            gameType = GameTypeEnum.CPU,
            cardsToBuyByComputer = cardService.getCardsPlayers(PlayerTypeEnum.COMPUTER)!!,
            cardsToBuyByPlayers  = cardService.getCardsPlayers(PlayerTypeEnum.PLAYER)!!,
            players = listOf(player,cpuPlayer),
            gameStatus = GameStatusEnum.PROGRESS
        )

        game.setCardsInPlayers()
        gameGlobal = game
        return game
    }

    fun executeMovement(player: Player, movement: Movement, card: Card): Game{
        val movement: InitialMovement = InitialMovement(
            movementEnum = movement.type!!,
            players = gameGlobal!!.players.filter { it -> it.id != player.id },
            playerRound = player,
            card = card
        )

        //Execute game logic's
        movement.checkMovementTypeAndProcess()
        checkAndSetStatusGame()
        checkAndAtualizeDeckPlayersAndGame(player,card)
        return gameGlobal!!
    }

    fun checkAndSetStatusGame(){
        val playersDead: List<Player> = gameGlobal!!.players.filter { it -> it.life == 0 }

        when(playersDead.isEmpty()){
             true  -> gameGlobal!!.gameStatus = GameStatusEnum.FINISHED
             false -> gameGlobal!!.gameStatus = GameStatusEnum.PROGRESS
        }
    }

    fun checkAndAtualizeDeckPlayersAndGame(playerRound: Player,cardUsed: Card){

        when(playerRound.playerTypeEnum){
            PlayerTypeEnum.PLAYER -> gameGlobal!!.cardsToBuyByPlayers.toMutableList().add(cardUsed)
            PlayerTypeEnum.COMPUTER -> gameGlobal!!.cardsToBuyByComputer.toMutableList().add(cardUsed)
        }
        gameGlobal!!.setCardsInPlayers(1)
    }

}