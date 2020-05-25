package com.magic.application.controller

import com.magic.model.Game
import com.magic.model.Player
import com.magic.service.GameService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

@Controller
class GameController(
    val gameService: GameService
) {

    @MessageMapping("/start_game")
    @SendTo("/topic/game_started")
    fun createGame(@Valid @RequestBody player: Player): Game {
        return gameService.startVsCPU(player)
    }

    @MessageMapping("/movement")
    @SendTo("/topic/movement_executed")
    fun receiveMovement(@Valid @RequestBody player: Player): Game {
        return gameService.startVsCPU(player)
    }

}