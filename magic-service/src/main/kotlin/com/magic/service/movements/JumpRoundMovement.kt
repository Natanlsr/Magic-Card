package com.magic.service.movements

import com.magic.enums.MovementEnum
import com.magic.model.Card
import com.magic.model.Movement
import com.magic.model.Player

class JumpRoundMovement: Movement {


    constructor(card: Card, players: List<Player>, playerRound: Player){
        this.type = MovementEnum.JUMP_ROUND
        this.card = card
        this.players = players
        this.playerRound = playerRound
        this.netxMovement = null
    }

    override fun checkMovementTypeAndProcess() {
        if(this.type!!.name.equals(MovementEnum.JUMP_ROUND)){
            playerRound!!.recoverManaJumpRound()
        }else{
            processNextMovement()
        }
    }
}