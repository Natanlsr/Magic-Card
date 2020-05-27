package com.magic.application

<<<<<<< Updated upstream
import com.magic.model.Card
import com.magic.service.CardService
import org.junit.jupiter.api.Test

class CardServiceTest {
    private val cardService : CardService,
    @Test
    fun whenGetCardsPlayer(){

    }
}
=======
import com.magic.enums.PlayerTypeEnum
import com.magic.model.Card
import com.magic.model.PlayerType
import com.magic.repository.CardRepository
import com.magic.repository.PlayerTypeRepository
import com.magic.service.CardService
import com.magic.service.exceptions.CardNotFoundException
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class CardServiceTest {
    val cardRepository = Mockito.mock(CardRepository::class.java)
    val playerTypeRepository = Mockito.mock(PlayerTypeRepository::class.java)
    val cardService: CardService = CardService(cardRepository,playerTypeRepository)
    val playerType = PlayerTypeEnum.PLAYER
    val idCard = 10010

    @Test
    fun `should get list card with success for the specific player type`(){
        Mockito.`when`(playerTypeRepository.findByType(playerType)).thenReturn(buildPlayerType())
        Mockito.`when`(cardRepository.findByPlayerType(buildPlayerType())).thenReturn(buildCardList())
        val cards = cardService.getCardsPlayers(playerType)
        Assert.assertNotNull(cards)
        Assert.assertEquals(2,cards!!.size)
    }

    @Test
    fun `should get all cards with success`(){
        Mockito.`when`(cardRepository.findAll()).thenReturn(buildCardList())
        val cards = cardService.getAllCards()
        Assert.assertNotNull(cards)
        Assert.assertEquals(2,cards.size)

    }

    @Test
    fun `should find card by id with success`(){
        Mockito.`when`(cardRepository.findById(idCard)).thenReturn(buildCard())
        val card = cardService.findCardById(idCard)
        Assert.assertNotNull(card)
        Assert.assertEquals(idCard,card.id)
    }

    @Test(expected = CardNotFoundException::class)
    fun `throw exception because card not found`(){
        Mockito.`when`(cardRepository.findById(idCard)).thenReturn(Optional.empty())
        cardService.findCardById(idCard)

    }


    private fun buildPlayerType() = PlayerType(id=1L,type = PlayerTypeEnum.PLAYER)

    private fun buildCardList() = listOf(
        Card(id =1, name = "teste1"),
        Card(id =2, name = "teste2")
    )

    private fun buildCard() = Optional.of(Card(id =idCard, name ="card"))
 }
>>>>>>> Stashed changes
