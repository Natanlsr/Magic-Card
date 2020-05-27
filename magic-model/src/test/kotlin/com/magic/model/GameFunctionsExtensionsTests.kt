package com.magic.model

import com.magic.enums.GameStatusEnum
import com.magic.enums.GameTypeEnum
import com.magic.enums.PlayerTypeEnum
import com.magic.extensions.randomizeDecks
import com.magic.extensions.setCardsFromDeckCards
import com.magic.extensions.setCardsInPlayers
import org.junit.Assert
import org.junit.Test


class GameFunctionsExtensionsTests {

    private val game = Game()
    private val card: Card = Card(1, "Test1", 2, 0, 0, 3, "retira 3 de life custa 2 mana", null)
    private val card1: Card = Card(2, "Test2", 2, 0, 0, 3, "retira 3 de life custa 2 mana", null)
    private val card2: Card = Card(3, "Test3", 2, 0, 0, 3, "retira 3 de life custa 2 mana", null)
    private val card3: Card = Card(4, "Test4", 2, 0, 0, 3, "retira 3 de life custa 2 mana", null)
    private val card4: Card = Card(5, "Test5", 2, 0, 0, 3, "retira 3 de life custa 2 mana", null)
    private val testDeck: List<Card> = mutableListOf(card, card1, card2, card3, card4)
    private val playerPVP: Player = Player(1, "TestPlayer", 20, 20, testDeck, PlayerTypeEnum.PLAYER)
    private val playerPC: Player = Player(1, "TestPlayer", 20, 20, testDeck, PlayerTypeEnum.PLAYER)
    private val listPlayers : List<Player> = mutableListOf(playerPC,playerPVP)
    private var game2 : Game = Game(1,0, emptyList(),GameTypeEnum.PVP, GameStatusEnum.PREPARING,testDeck,testDeck,0)

    @Test
    fun `When to need randomize deck`(){
        game2.randomizeDecks()

        Assert.assertNotEquals(testDeck, game2.cardsToBuyByPlayers)
    }

    @Test
    fun `When to Set Cards in Players`(){
        game2.setCardsInPlayers(3)
        playerPC.setCardsFromDeckCards(testDeck, 3)
        
    }
}