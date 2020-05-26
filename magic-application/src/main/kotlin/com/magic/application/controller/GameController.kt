package com.magic.application.controller

import com.magic.application.converter.mapToModel
import com.magic.application.request.PlayerRequest
import com.magic.service.GameService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("http://localhost:8081", "localhost:8081")
class GameController(
        val gameService: GameService
) {

    @PostMapping("/command/game/start")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun createGame(@RequestBody player: PlayerRequest) {
        gameService.startVsCPU(player.mapToModel())
    }


}