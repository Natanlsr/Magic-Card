package com.magic.service.response

import com.magic.enums.PlayerTypeEnum

data class PlayerResponse(
    val id: Int?,

    val name: String,

    var life: Int,

    var mana: Int,

    var deck: List<CardResponse?>?,

    var playerType: String
)