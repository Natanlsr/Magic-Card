package com.magic.service.exceptions

class CardNotFoundException: RuntimeException {
    constructor(message: String): super(message)
}