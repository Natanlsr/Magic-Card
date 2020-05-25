package com.magic.service.movements

import com.magic.enums.MovementEnum
import com.magic.model.Card
import com.magic.model.Movement
import com.magic.model.Player

class InitialMovement : Movement {

    constructor(card: Card, players: List<Player>, playerRound: Player, movementEnum: MovementEnum):
        super(card,players,playerRound,movementEnum) {
        this.netxMovement = AplyCardMovement(card,players,playerRound,movementEnum)
    }

    override fun checkMovementTypeAndProcess() {
       processNextMovement()
    }
}