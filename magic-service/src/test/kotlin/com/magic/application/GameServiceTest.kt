package com.magic.application

import com.magic.enums.GameStatusEnum
import com.magic.enums.MovementEnum
import com.magic.enums.PlayerTypeEnum
import com.magic.model.Card
import com.magic.model.Game
import com.magic.model.Player
import com.magic.model.PlayerType
import com.magic.repository.GameRepository
import com.magic.service.CardService
import com.magic.service.GameService
import com.magic.service.PlayerService
import com.magic.service.exceptions.GameNotFoundException
import com.magic.service.exceptions.MovementNotAllowedException
import com.magic.service.exceptions.NotTurnPlayerException
import com.magic.service.exceptions.PlayerNotFoundException
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert
import org.junit.Test
import org.springframework.messaging.simp.SimpMessagingTemplate
import java.util.Optional

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

        gameService.startVsCPU(buildPlayer())

        Assert.assertEquals(GameStatusEnum.PROGRESS,gameCaptured.captured.gameStatus)
    }

    @Test
    fun `should find game by id with success`(){
        val gameId = 1
        every { gameRepository.findById(gameId) } returns Optional.of(buildGame())
        val gameReturned = gameService.findGameById(gameId)

        Assert.assertNotNull(gameReturned)
        Assert.assertEquals(gameId,gameReturned.id)
    }

    @Test(expected = GameNotFoundException::class)
    fun `should throw exception because game not found by id`(){
        val gameId = 2
        every { gameRepository.findById(gameId) } returns Optional.empty()
        gameService.findGameById(gameId)
    }

    @Test
    fun `should execute cpu movement with success`(){
        val gameId = 1
        var gameCaptured = slot<Game>()

        every { gameRepository.findById(gameId) } returns Optional.of(buildGame())
        every { playerService.findPlayerById(2) } returns buildPlayerCpu()
        every { cardService.findCardById(1) } returns buildCardsComputer()[0]
        every { gameRepository.save(capture(gameCaptured))} returns Game(id =1)
        every { template.send(allAny())} returns Unit

        gameService.cpuMovement(gameId)

        Assert.assertEquals(gameId,gameCaptured.captured.id)
    }

    @Test(expected = PlayerNotFoundException::class)
    fun `should execute cpu movement with error because player Cpu not found`(){
        val gameId = 1
        var gameCaptured = slot<Game>()
        var game = buildGame()
        game.players = listOf(buildPlayer())

        every { gameRepository.findById(gameId) } returns Optional.of(game)
        every { playerService.findPlayerById(2) } returns buildPlayerCpu()
        every { cardService.findCardById(1) } returns buildCardsPlayers()[0]
        every { gameRepository.save(capture(gameCaptured))} returns Game(id =1)
        every { template.send(allAny())} returns Unit

        gameService.cpuMovement(gameId)
    }

    @Test(expected = MovementNotAllowedException::class)
    fun `should execute cpu movement with error because card not in deck player`(){
        val gameId = 1
        var gameCaptured = slot<Game>()


        every { gameRepository.findById(gameId) } returns Optional.of(buildGame())
        every { playerService.findPlayerById(2) } returns buildPlayerCpu()
        every { cardService.findCardById(1) } returns buildCardsPlayers()[0]
        every { gameRepository.save(capture(gameCaptured))} returns Game(id =1)
        every { template.send(allAny())} returns Unit

        gameService.cpuMovement(gameId)
    }

    @Test(expected = NotTurnPlayerException::class)
    fun `should execute cpu movement with error because card not player turn`(){
        val gameId = 1
        var gameCaptured = slot<Game>()

        every { gameRepository.findById(gameId) } returns Optional.of(buildGame())
        every { playerService.findPlayerById(2) } returns buildPlayer()
        every { cardService.findCardById(1) } returns buildCardsPlayers()[0]
        every { gameRepository.save(capture(gameCaptured))} returns Game(id =1)
        every { template.send(allAny())} returns Unit

        gameService.cpuMovement(gameId)
    }

    @Test
    fun `should execute movement with success`(){
        val gameId = 1
        var gameCaptured = slot<Game>()

        every { gameRepository.findById(gameId) } returns Optional.of(buildGame())
        every { playerService.findPlayerById(2) } returns buildPlayerCpu()
        every { cardService.findCardById(1) } returns buildCardsComputer()[0]
        every { gameRepository.save(capture(gameCaptured))} returns Game(id =1)
        every { template.send(allAny())} returns Unit

        gameService.executeMovement(gameId,2,MovementEnum.APPLY_CARD,1)

        Assert.assertEquals(gameId,gameCaptured.captured.id)
    }

    @Test
    fun `should execute player movement with success`(){
        val gameId = 1
        var gameCaptured = slot<Game>()

        every { gameRepository.findById(gameId) } returns Optional.of(buildGame())
        every { playerService.findPlayerById(2) } returns buildPlayerCpu()
        every { cardService.findCardById(1) } returns buildCardsComputer()[0]
        every { gameRepository.save(capture(gameCaptured))} returns Game(id =1)
        every { template.send(allAny())} returns Unit

        gameService.executePlayerMovement(gameId,2,MovementEnum.APPLY_CARD,1)

        Assert.assertEquals(gameId,gameCaptured.captured.id)
    }



    private fun buildCardsComputer() = listOf(
        Card(id =1, name = "teste1", playerType = PlayerType(type = PlayerTypeEnum.COMPUTER),manaCost = 3),
        Card(id =2, name = "teste2", playerType =  PlayerType(type = PlayerTypeEnum.COMPUTER),manaCost = 30)
    )

    private fun buildCardsPlayers() =  listOf(
        Card(id =3, name = "teste1", playerType =  PlayerType(type = PlayerTypeEnum.PLAYER)),
        Card(id =4, name = "teste2", playerType =  PlayerType(type = PlayerTypeEnum.PLAYER))
    )

    private fun buildPlayer() = Player(id=1,name="Teste",deck = buildCardsPlayers(),playerType = PlayerTypeEnum.PLAYER)
    private fun buildPlayerCpu() = Player(id=2,name="CPU",deck = buildCardsComputer(),playerType = PlayerTypeEnum.COMPUTER
    )

    private fun buildGame() = Game(
        id = 1,
        cardsToBuyByPlayers = buildCardsPlayers(),
        cardsToBuyByComputer = buildCardsComputer(),
        players = listOf(buildPlayer(),buildPlayerCpu()),
        indexPlayerTurn = 1
    )
}