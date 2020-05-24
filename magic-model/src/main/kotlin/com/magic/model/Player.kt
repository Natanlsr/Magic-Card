package com.magic.model

import com.magic.enums.PlayerTypeEnum

data class Player(
    val id: Int? = null,
    val name: String? = null,
    var life: Int = 20,
    var mana: Int = 20,
    var deck: List<Card> = emptyList(),
    var playerTypeEnum: PlayerTypeEnum = PlayerTypeEnum.PLAYER
){
    companion object {
        @JvmField val MaximmumNumberCards = 4
        @JvmField val RecoverManaJumpRouund = 2
    }

    fun setCardsFromDeckCards(deckGame: List<Card>, numberCards: Int = MaximmumNumberCards): List<Card>{

        if(numberCards > MaximmumNumberCards)
            throw Exception("Numero de Cartas maior do que o possivel")

        this.deck = deckGame.take(numberCards)
        return deckGame.drop(numberCards)
    }

    fun useCardAndRemoveFromDeck(card: Card){
        if(card.manaCost <= this.mana){
            this.mana -= card.manaCost
        }else{
            //lançar execeção
        }
        this.mana += card.manaRecover
        this.deck = deck.filter { it -> it.id != card.id }
    }

    fun damageCard(card: Card){
        this.life -= card.lifeDamage
        this.mana -= card.manaDamage
    }

    fun recoverManaJumpRound(){
        this.mana += RecoverManaJumpRouund
    }


}