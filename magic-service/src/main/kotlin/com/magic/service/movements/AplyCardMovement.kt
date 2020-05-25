package com.magic.service.movements

import com.magic.enums.MovementEnum
import com.magic.extensions.damageCard
import com.magic.extensions.useCardAndRemoveFromDeck
import com.magic.model.Card
import com.magic.model.Movement
import com.magic.model.Player
import org.springframework.stereotype.Component

class AplyCardMovement : Movement {

    constructor(card: Card, players: List<Player>, playerRound: Player, movementEnum: MovementEnum):
        super(card,players,playerRound,movementEnum) {
        this.netxMovement = JumpRoundMovement(card,players,playerRound,movementEnum)
    }

    override fun checkMovementTypeAndProcess() {

        when (this.type) {
            MovementEnum.APPLY_CARD -> {
                this.playerRound?.let {
                    it.useCardAndRemoveFromDeck(this.card!!)
                }
                this.players?.let {
                    it.forEach { player ->
                        player.damageCard(this.card!!)
                    }
                }
            }
            else -> processNextMovement()
        }

    }

}