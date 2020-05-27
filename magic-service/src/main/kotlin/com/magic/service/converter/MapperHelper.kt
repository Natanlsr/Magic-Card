package com.magic.service.converter

import com.magic.enums.PlayerTypeEnum
import com.magic.model.Card
import com.magic.model.Game
import com.magic.model.Player
import com.magic.service.response.CardResponse
import com.magic.service.response.GameResponse
import com.magic.service.response.PlayerResponse

fun Card.toCardReponse(): CardResponse? {
    return when (this.playerType!!.type.name) {
        PlayerTypeEnum.PLAYER.name -> {
            CardResponse(
                id = this.id,
                name = this.name,
                manaCost = this.manaCost,
                manaDamage = this.manaDamage,
                manaRecover = this.manaRecover,
                lifeDamage = this.lifeDamage,
                description = this.description
            )
        }
        else -> null
    }
}

fun Player.toPlayerResponse() =
    PlayerResponse(
        id = this.id,
        name = this.name,
        life = this.life,
        mana = this.mana,
        deck = if (this.playerType.name == PlayerTypeEnum.PLAYER.name)
            this.deck.map { card -> card.toCardReponse() }
        else null,
        playerType = this.playerType.name
    )

fun Game.toGameResponse() =
    GameResponse(
        id = this.id,
        round = this.round,
        players = this.players.map { player -> player.toPlayerResponse() },
        gameStatus = this.gameStatus.name,
        indexPlayerTurn = this.indexPlayerTurn,
        gameType = this.gameType.name
    )




