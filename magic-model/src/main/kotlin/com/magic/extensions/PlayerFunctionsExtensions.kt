package com.magic.extensions

import com.magic.model.Card
import com.magic.model.Player

fun Player.setCardsFromDeckCards(deckGame: List<Card>, numberCards: Int = Player.MaximmumNumberCards): List<Card>{

    if(numberCards > Player.MaximmumNumberCards)
        throw Exception("Numero de Cartas maior do que o possivel")

    this.deck = deckGame.take(numberCards)
    return deckGame.drop(numberCards)
}

fun Player.useCardAndRemoveFromDeck(card: Card){
    if(card.manaCost <= this.mana){
        this.mana -= card.manaCost
    }else{
        //lançar execeção
    }
    this.mana += card.manaRecover
    this.deck = deck.filter { it -> it.id != card.id }
}

fun Player.damageCard(card: Card){
    this.life -= card.lifeDamage
    this.mana -= card.manaDamage
}

fun Player.recoverManaJumpRound(){
    this.mana += Player.RecoverManaJumpRouund
}