package com.magic.application.controller

import com.magic.application.converter.mapToModel
import com.magic.application.request.PlayerRequest
import com.magic.enums.MovementEnum
import com.magic.service.GameService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("http://localhost:8081", "localhost:8081")
@RequestMapping("/command/game")
class GameController(
        val gameService: GameService
) {

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun createGame(@RequestBody player: PlayerRequest) {
        gameService.startVsCPU(player.mapToModel())
    }

    @PutMapping("movement/{gameId}/player/{playerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun executeMovement(@PathVariable(value = "gameId") gameId: Int,
                        @PathVariable(value = "playerId") playerId: Int,
                        @RequestParam cardId: Int,
                        @RequestParam type: String){
        gameService.executeMovement(gameId,playerId, MovementEnum.valueOf(type),cardId)
    }



}