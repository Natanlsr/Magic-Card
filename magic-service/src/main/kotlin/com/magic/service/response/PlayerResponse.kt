package com.magic.service.response

import com.magic.enums.PlayerTypeEnum
import com.magic.model.Card

data class PlayerResponse(
    val name: String,

    var life: Int,

    var mana: Int,

    var deck: List<Card>,

    var playerType: PlayerTypeEnum
)