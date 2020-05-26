package com.magic.service.enums

enum class ExceptionEnum(val message: String) {
    GAME_NOT_FOUND("Game mão encontrado"),
    PLAYER_NOT_FOUND("Player não encontrado"),
    CARD_NOT_FOUND("Carta não encontrada"),
    NOT_TURN_PLAYER("Não é a vez do player"),
    MANA_INSUFFICIENT("Player não possui mana suficiente para o movimento"),
    MOVEMENT_NOT_ALLOWED("Movimento não permitido, carta invalida")
}