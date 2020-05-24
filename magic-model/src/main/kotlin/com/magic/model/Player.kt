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
    @JvmField val MaximmumNumberCards = 4

    fun setCardsFromDeckCards(deckGame: List<Card>): List<Card>{
        this.deck = deckGame.take(this.MaximmumNumberCards)
        return deckGame.drop(this.MaximmumNumberCards)
    }

}