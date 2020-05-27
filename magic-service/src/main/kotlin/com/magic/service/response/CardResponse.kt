package com.magic.service.response

import com.magic.model.PlayerType

data class CardResponse(
    val id: Int? = null,

    var name: String,

    var manaCost: Int,

    var manaDamage: Int,

    var manaRecover: Int,

    var lifeDamage: Int,

    var description: String

)