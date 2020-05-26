package com.magic.application.converter

import com.magic.application.request.PlayerRequest
import com.magic.model.Player

fun PlayerRequest.mapToModel() =
    Player(
        name = this.name
    )
