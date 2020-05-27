package com.magic.application

import com.magic.model.Player
import com.magic.repository.PlayerRepository
import com.magic.service.PlayerService
import com.magic.service.exceptions.PlayerNotFoundException
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import java.util.Optional


class PlayServiceTest {

    val playerRepository = Mockito.mock(PlayerRepository::class.java)
    val playerService = PlayerService(playerRepository)
    val playerId = 1
    val playerName = "Teste"

    @Test
    fun `should find player by id with success`(){
        val playerId = 1
        Mockito.`when`(playerRepository.findById(1)).thenReturn(buildPlayerOptional())
        val player = playerService.findPlayerById(1)
        Assert.assertEquals(playerId,player.id)
        Assert.assertEquals(playerName,player.name)
    }

    @Test
    fun `should save player with success`(){
        Mockito.`when`(playerRepository.save(buildPlayer())).thenReturn(buildPlayer())
        val player = playerService.savePlayer(buildPlayer())
        Assert.assertEquals(playerId,player.id)
        Assert.assertEquals(playerName,player.name)
    }

    @Test(expected = PlayerNotFoundException::class)
    fun `should throw error because player notFound`(){
        Mockito.`when`(playerRepository.findById(2)).thenReturn(Optional.empty())
        playerService.findPlayerById(2)
    }

    private fun buildPlayerOptional() = Optional.of(buildPlayer())

    private fun buildPlayer() = Player(id=playerId,name=playerName)
}