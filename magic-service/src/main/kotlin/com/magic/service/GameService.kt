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

    fun userRound(card: Card, playerJr : Player, playerBug : Player, game: Game){
        useCard(card, playerJr)
        damageCard(card, playerBug)
        isAlive(playerBug, game)
    }

    fun bugRound(card: Card, playerBug: Player, playerJr: Player, game: Game){
        useCard(card, playerBug)
        damageCard(card, playerJr)
        isAlive(playerJr, game)
    }

    // Enviar o player que joga a carta
    fun useCard(card : Card, player: Player){
        if (card.manaCost <=  player.mana){
            player.mana = player.mana - card.manaCost
        } else {
            throw Exception ("Mana insuficiente para utilizar esta carta, favor utilizar uma carta correspondente ao valor de mana: " + player.mana)
        }
    }

    //Aqui enviar o player que sofre o dano
    fun damageCard (card: Card, player: Player){
        player.life = player.life - card.lifeDamage
        player.mana = player.mana   - card.manaDamage

        return p
    }

    // verifica se o player esta vivo após a rodada.
     fun isAlive (player: Player, game: Game){
         if (player.life <= 0){
             game.gameStatus = GameStatusEnum.FINISHED
         } else {
             game.gameStatus = GameStatusEnum.WAIT_MOVEMENT
         }
     }

}