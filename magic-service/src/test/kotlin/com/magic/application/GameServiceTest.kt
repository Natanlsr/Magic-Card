package com.magic.application

import com.magic.enums.GameStatusEnum
import com.magic.enums.PlayerTypeEnum
import com.magic.model.Card
import com.magic.model.Game
import com.magic.model.Player
import com.magic.model.PlayerType
import com.magic.repository.GameRepository
import com.magic.service.CardService
import com.magic.service.GameService
import com.magic.service.PlayerService
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import liquibase.pro.packaged.G
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.springframework.messaging.Message
import org.springframework.messaging.simp.SimpMessagingTemplate

class GameServiceTest {

    val gameRepository = mockk<GameRepository>(relaxed = true)
    val cardService = mockk<CardService>(relaxed = true)
    val playerService = mockk<PlayerService>(relaxed = true)
    val template = mockk<SimpMessagingTemplate>(relaxed = true)

    val gameService = GameService(gameRepository,cardService,playerService,template)

    @Test
    fun `should start game vs cpu with success`(){
        var gameCaptured = slot<Game>()

        every {  cardService.getCardsPlayers(PlayerTypeEnum.COMPUTER)} returns buildCardsComputer()
        every {  cardService.getCardsPlayers(PlayerTypeEnum.PLAYER)} returns buildCardsPlayers()
        every {  playerService.savePlayer(any()) } returns buildPlayer()
        every {  gameRepository.save(capture(gameCaptured))} returns Game(id =1)
        every {  template.send(allAny())} returns Unit

        val game = gameService.startVsCPU(buildPlayer())

        Assert.assertEquals(GameStatusEnum.PROGRESS,gameCaptured.captured.gameStatus)
    }

    private fun buildCardsComputer() = listOf(
        Card(id =1, name = "teste1", playerType = PlayerType(type = PlayerTypeEnum.COMPUTER)),
        Card(id =2, name = "teste2", playerType =  PlayerType(type = PlayerTypeEnum.COMPUTER))
    )

    private fun buildCardsPlayers() =  listOf(
        Card(id =3, name = "teste1", playerType =  PlayerType(type = PlayerTypeEnum.PLAYER)),
        Card(id =4, name = "teste2", playerType =  PlayerType(type = PlayerTypeEnum.PLAYER))
    )

    private fun buildPlayer() = Player(id=1,name="Teste",deck = buildCardsPlayers())
}