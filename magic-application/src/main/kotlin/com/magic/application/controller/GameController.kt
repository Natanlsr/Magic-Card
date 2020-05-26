package com.magic.application.controller

import com.magic.model.Game
import com.magic.model.Player
import com.magic.service.GameService
import org.springframework.http.HttpStatus
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@CrossOrigin("http://localhost:8081", "localhost:8081")
class GameController(
        val gameService: GameService
) {

    @PostMapping("/command/game/start")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun createGame(@Valid @RequestBody player: Player) {
        gameService.startVsCPU(player)
    }

//    @MessageMapping("/movement")
//    @SendTo("/topic/movement_executed")
//    fun receiveMovement(@Valid @RequestBody player: Player): Game {
//        return gameService.startVsCPU(player)
//    }
}