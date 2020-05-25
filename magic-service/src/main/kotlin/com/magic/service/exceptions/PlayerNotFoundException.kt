package com.magic.service.exceptions

class PlayerNotFoundException: RuntimeException {
    constructor(message: String): super(message)
}