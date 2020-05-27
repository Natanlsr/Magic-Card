package com.magic.application.controller.exceptions

import com.magic.service.exceptions.GameNotFoundException
import com.magic.service.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GameControllerExceptionHandler {

    @ExceptionHandler(GameNotFoundException::class)
    fun gameNotFoundException(exception: GameNotFoundException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(HttpStatus.NOT_FOUND.value(),exception.message!!)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)

    }
}