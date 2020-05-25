package com.magic.extensions

import com.magic.enums.PlayerTypeEnum
import com.magic.model.Game
import com.magic.model.Player

fun Game.randomizeDecks(){
    this.cardsToBuyByPlayers = this.cardsToBuyByPlayers.shuffled()
    this.cardsToBuyByComputer = this.cardsToBuyByComputer.shuffled()
}

fun Game.setCardsInPlayers(numberCards: Int = Player.MaximmumNumberCards){
    this.randomizeDecks()
    for (player in this.players){
        if(player.deck.size < Player.MaximmumNumberCards) {
            when (player.playerTypeEnum.name) {
                PlayerTypeEnum.COMPUTER.name -> this.cardsToBuyByComputer = player.setCardsFromDeckCards(this.cardsToBuyByComputer, numberCards)
                PlayerTypeEnum.PLAYER.name -> this.cardsToBuyByPlayers = player.setCardsFromDeckCards(this.cardsToBuyByPlayers, numberCards)
            }
        }
    }
}

fun Game.randomizePlayersOrder(){
    this.players = this.players.shuffled()
}
