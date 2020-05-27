package com.magic.model

import com.magic.enums.PlayerTypeEnum
import com.magic.exceptions.ManaInsufficientException
import com.magic.exceptions.NumberCardsExceeded
import com.magic.extensions.damageCard
import com.magic.extensions.recoverManaJumpRound
import com.magic.extensions.setCardsFromDeckCards
import com.magic.extensions.useCardAndRemoveFromDeck
import org.junit.Assert
import org.junit.Test


class PlayerFunctionsExtensionsTest {


    private val playerFunctionsExtensions = Player()
    private val card: Card = Card(1, "Test1", 2, 0, 0, 3, "retira 3 de life custa 2 mana", null)
    private val card1: Card = Card(2, "Test2", 2, 0, 0, 3, "retira 3 de life custa 2 mana", null)
    private val card2: Card = Card(3, "Test3", 2, 0, 0, 3, "retira 3 de life custa 2 mana", null)
    private val card3: Card = Card(4, "Test4", 2, 0, 0, 3, "retira 3 de life custa 2 mana", null)
    private val card4: Card = Card(5, "Test5", 2, 0, 0, 3, "retira 3 de life custa 2 mana", null)
    private val testDeck: List<Card> = mutableListOf(card, card1, card2, card3, card4)
    private val player: Player = Player(1, "TestPlayer", 20, 20, testDeck, PlayerTypeEnum.PLAYER)

    @Test
    fun `when set cards from deck cards`() {
        val numberOfCardsInDeck = playerFunctionsExtensions.setCardsFromDeckCards(testDeck, 2)

        Assert.assertEquals(3, numberOfCardsInDeck.size)
    }

    @Test(expected = NumberCardsExceeded::class)
    fun `when set more cads from deck`() {
        player.useCardAndRemoveFromDeck(card)
    }

    @Test
    fun `When use card, and remove From Deck`() {
        val useCardDeck = playerFunctionsExtensions.useCardAndRemoveFromDeck(card)

        Assert.assertNotNull(player.deck)
        Assert.assertNotEquals(player.deck, useCardDeck)

    }

    @Test(expected = ManaInsufficientException::class)
    fun `When use card, and have insufficient mana`() {
        val player2: Player = Player(1, "TestPlayer", 20, 0, testDeck, PlayerTypeEnum.PLAYER)
        player2.useCardAndRemoveFromDeck(card)
    }

    @Test
    fun `When player receive a damageCard`() {
        player.damageCard(card)

        Assert.assertEquals(17, player.life)
    }

    @Test
    fun `When you recovery mana after turn`() {
        val player3: Player = Player(1, "TestPlayer", 20, 0, testDeck, PlayerTypeEnum.PLAYER)

        player3.recoverManaJumpRound()

        Assert.assertNotEquals(0, player3.mana)

    }


}