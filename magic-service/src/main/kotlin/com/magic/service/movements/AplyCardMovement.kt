package com.magic.service.movements

import com.magic.enums.MovementEnum
import com.magic.model.Card
import com.magic.model.Movement
import com.magic.model.Player
import org.springframework.stereotype.Component

@Component
class AplyCardMovement: Movement {

    constructor(card: Card,players: List<Player>,playerRound: Player, movementEnum: MovementEnum){
        this.type = movementEnum
        this.card = card
        this.players = players
        this.playerRound = playerRound
        this.netxMovement = JumpRoundMovement(card,players,playerRound)
    }

    override fun checkMovementTypeAndProcess() {
        if(this.type!!.name.equals(MovementEnum.APLY_CARD)){
            playerRound!!.useCardAndRemoveFromDeck(this.card!!)
            players!!.forEach { player-> player.damageCard(this.card!!) }
        }else{
            processNextMovement()
        }
    }


}