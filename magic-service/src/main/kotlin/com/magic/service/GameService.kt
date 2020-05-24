package com.magic.service

import com.magic.enums.GameStatusEnum
import com.magic.enums.GameTypeEnum
import com.magic.enums.PlayerTypeEnum
import com.magic.model.Card
import com.magic.model.Game
import com.magic.model.Player
import com.magic.model.PlayerType
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Service

@Service
class GameService
@Autowired constructor(
    val cardService: CardService
){

    fun startVsCPU(player: Player): Game{

        val cpuPlayer = Player(id = 1,name = "CPU",playerTypeEnum = PlayerTypeEnum.COMPUTER)

        val game = Game(
            gameType = GameTypeEnum.CPU,
            cardsToBuyByComputer = cardService.getCardsPlayers(PlayerTypeEnum.COMPUTER)!!,
            cardsToBuyByPlayers  = cardService.getCardsPlayers(PlayerTypeEnum.PLAYER)!!,
            players = listOf(player,cpuPlayer)
        )

        game.setCardsInPlayers()
        return game
    }


    fun useCard(card : Card, player: Player){
        if (card.manaCost <=  player.mana){
            player.mana = player.mana - card.manaCost
        } else {
            throw Exception ("Mana insuficiente para utilizar esta carta, favor utilizar uma carta correspondente ao valor de mana: " + player.mana)
        }
    }

    fun damageCard (card: Card, player: Player){
        if (card.manaDamage == 0 && card.lifeDamage > 0){
            player.life = player.life - card.lifeDamage
        } else {
            player.mana = player.mana   - card.manaDamage
        }
    }
     fun isAlive (player: Player, game: Game){
         if (player.life <= 0){
             game.gameStatus = GameStatusEnum.FINISHED
         }
     }

}