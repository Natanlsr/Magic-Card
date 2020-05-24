package com.magic.model

import com.magic.enums.GameStatusEnum
import com.magic.enums.GameTypeEnum
import com.magic.enums.PlayerTypeEnum
import lombok.NoArgsConstructor

@NoArgsConstructor
data class Game(
    var round: Int = 0,
    var players: List<Player>  = emptyList(),
    val gameType: GameTypeEnum,
    var gameStatus: GameStatusEnum = GameStatusEnum.PREPARING,
    var cardsToBuyByPlayers: List<Card> = emptyList(),
    var cardsToBuyByComputer: List<Card> = emptyList()
){

    fun randomizeDecks(){
        this.cardsToBuyByPlayers = this.cardsToBuyByPlayers.shuffled()
        this.cardsToBuyByComputer = this.cardsToBuyByComputer.shuffled()
    }

    fun setCardsInPlayers(){
        this.randomizeDecks()
        for (player in this.players){
            when(player.playerTypeEnum!!.name){
                PlayerTypeEnum.COMPUTER.name -> this.cardsToBuyByComputer = player.setCardsFromDeckCards(this.cardsToBuyByComputer)
                PlayerTypeEnum.PLAYER.name -> this.cardsToBuyByPlayers = player.setCardsFromDeckCards(this.cardsToBuyByPlayers)
            }
        }
    }

}