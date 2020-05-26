package com.magic.service.enums

enum class ExceptionEnum(name: String) {
    GAME_NOT_FOUND("Game mão encontrado"),
    PLAYER_NOT_FOUND("Player não encontrado"),
    CARD_NOT_FOUND("Carta não encontrada"),
    NOT_TURN_PLAYER("Não é a vez do player"),
    MADA_INSUFFICIENT("Player não possui mana suficiente para o movimento")
}