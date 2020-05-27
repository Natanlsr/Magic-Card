package com.magic.service.response

import com.magic.enums.GameStatusEnum
import com.magic.enums.GameTypeEnum
import com.magic.model.Card
import com.magic.model.Player

data class GameResponse(
    val id: Int?,

    var round: Int = 0,

    var players: List<PlayerResponse>,

    var gameType: String,

    var gameStatus: String,

    var indexPlayerTurn: Int
)