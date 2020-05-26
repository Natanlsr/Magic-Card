package com.magic.service.exceptions

class MovementNotAllowedException: RuntimeException {
    constructor(message: String): super(message)
}