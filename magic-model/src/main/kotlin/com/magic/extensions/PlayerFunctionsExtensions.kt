package com.magic.extensions

import com.magic.enums.ExceptionsEnum
import com.magic.exceptions.ManaInsufficientException
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
        throw ManaInsufficientException(ExceptionsEnum.MANA_INSUFICIENTE.name)
    }
    this.mana += card.manaRecover
    this.deck = deck.filter { it -> it.id != card.id }
}

fun Player.damageCard(card: Card){
    this.life = minusValue(this.life,card.lifeDamage)
    this.mana = minusValue(this.mana,card.manaDamage)
}

fun Player.minusValue(value: Int, valueMinus: Int): Int{
    return if(valueMinus > value) 0
    else
        value - valueMinus
}

fun Player.recoverManaJumpRound(){
    this.mana += Player.RecoverManaJumpRouund
}