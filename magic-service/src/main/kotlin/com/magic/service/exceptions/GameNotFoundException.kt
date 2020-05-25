package com.magic.service.exceptions

class GameNotFoundException: RuntimeException {
    constructor(message: String): super(message)
}