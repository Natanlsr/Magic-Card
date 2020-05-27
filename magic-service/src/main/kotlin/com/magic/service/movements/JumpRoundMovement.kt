package com.magic.service.movements

import com.magic.enums.MovementEnum
import com.magic.extensions.recoverManaJumpRound
import com.magic.model.Card
import com.magic.model.Movement
import com.magic.model.Player

class JumpRoundMovement: Movement {

    constructor(card: Card?, players: List<Player>, playerRound: Player, movementEnum: MovementEnum):
        super(card,players,playerRound,movementEnum) {
        this.netxMovement = null
    }

    override fun checkMovementTypeAndProcess() {
        when (this.type) {
            MovementEnum.JUMP_ROUND -> {
                this.playerRound?.let {
                    playerRound!!.recoverManaJumpRound()
                }
            }
            else -> processNextMovement()
        }
    }
}