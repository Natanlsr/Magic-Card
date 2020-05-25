package com.magic.application.controller

import com.magic.model.Player
import com.magic.service.GameService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.xml.ws.Service

@RestController
class GameController(
    val gameService: GameService
) {

    @PostMapping("/game")
    fun createGame(@Valid @RequestBody player: Player): ResponseEntity<*> {
        return ResponseEntity.ok().body(gameService.startVsCPU(player))
    }
}