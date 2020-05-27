package com.magic.extensions

import com.magic.enums.ExceptionsEnum
import com.magic.exceptions.ManaInsufficientException
import com.magic.exceptions.NumberCardsExceeded
import com.magic.model.Card
import com.magic.model.Player

fun Player.setCardsFromDeckCards(deckGame: List<Card>, numberCards: Int = Player.MaximmumNumberCards): List<Card>{
    if(numberCards > Player.MaximmumNumberCards)
        throw NumberCardsExceeded(ExceptionsEnum.NUMBER_CARD_EXCEEDED.message)

    this.deck += deckGame.take(numberCards)
    return deckGame.drop(numberCards)
}

fun Player.useCardAndRemoveFromDeck(card: Card){
    if(card.manaCost <= this.mana){
        this.mana -= card.manaCost
    }else{
        throw ManaInsufficientException(ExceptionsEnum.MANA_INSUFFICIENT.message)
    }
    this.mana += card.manaRecover
    this.mana += Player.RecoverManaPerRound
    this.deck = deck.filter { it -> it.id != card.id }
}

fun Player.damageCard(card: Card){
    this.life = minusValue(this.life, card.lifeDamage)
    this.mana = minusValue(this.mana, card.manaDamage)
}

private fun minusValue(value: Int, valueMinus: Int): Int{
    return if(valueMinus > value) 0
    else
        value - valueMinus
}

fun Player.recoverManaJumpRound(){
    this.mana += Player.RecoverManaJumpRouund
}

fun Player.canUseAnyCard(): Card?{
    return this.deck.firstOrNull{
        card -> card.manaCost <= this.mana
    }
}
