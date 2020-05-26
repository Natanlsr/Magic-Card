package com.magic.exceptions

class NumberCardsExceeded : RuntimeException {
    constructor(message: String): super(message)
}