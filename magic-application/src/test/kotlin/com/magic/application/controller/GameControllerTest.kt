package com.magic.application.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.magic.application.MagicApplication
import com.magic.application.request.PlayerRequest
import com.magic.repository.GameRepository
import com.magic.repository.PlayerRepository
import com.magic.repository.PlayerTypeRepository
import com.magic.service.CardService
import com.magic.service.GameService
import com.magic.service.PlayerService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath


@SpringBootTest(classes = [MagicApplication::class, GameController::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner::class)
@EnableScheduling
@AutoConfigureMockMvc
@EnableAsync
@Transactional
open class GameControllerTest  {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var gameService: GameService

    @Autowired
    lateinit var gameRepository: GameRepository

    @Autowired
    lateinit var cardRepository: GameRepository

    @Autowired
    lateinit var cardService: CardService

    @Autowired
    lateinit var playerService: PlayerService

    @Autowired
    lateinit var playerRepository: PlayerRepository

    @Autowired
    lateinit var playerTypeRepository: PlayerTypeRepository

    private val baseUrl = "/command/game"

    @Test
    fun `test Start Game vs Cpu`() {

        mockMvc.perform(MockMvcRequestBuilders.post("$baseUrl/start")
            .content(jsonPlayerRequest("teste"))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent)
    }


    @Test
    @Sql(value = ["/scripts/create-game.sql"])
    fun `should execute movement with success`(){
        mockMvc.perform(MockMvcRequestBuilders.put("$baseUrl/movement/{gameId}/player/{playerId}",1,1)
            .param("cardId","1")
            .param("type","APPLY_CARD")
            .content(jsonPlayerRequest("teste"))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent)
    }

    @Test
    @Sql(value = ["/scripts/create-game.sql"])
    fun `should get game with success`(){
        mockMvc.perform(MockMvcRequestBuilders.get("$baseUrl/{gameId}",1)
            .content(jsonPlayerRequest("teste"))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
    }


    private fun jsonPlayerRequest(name: String): String{
        val playerRequest = PlayerRequest(name = name)
        val mapper = ObjectMapper()
        return mapper.writeValueAsString(playerRequest)
    }
}