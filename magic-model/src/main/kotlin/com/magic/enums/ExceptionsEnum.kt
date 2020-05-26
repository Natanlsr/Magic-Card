package com.magic.enums

enum class ExceptionsEnum(val message: String) {
    MANA_INSUFFICIENT("Player não possui mana suficiente"),
    NUMBER_CARD_EXCEEDED("Número de cartas acima do permitido")
}