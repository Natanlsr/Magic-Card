package com.magic.model

import com.magic.enums.MovementEnum

abstract class Movement{
    var type: MovementEnum? = null
    var netxMovement: Movement? = null
    var players: List<Player>? = null
    var playerRound: Player? = null
    var card: Card? = null

    abstract fun checkMovementTypeAndProcess()

    protected fun processNextMovement(){
        if(netxMovement != null)
            netxMovement!!.checkMovementTypeAndProcess()
    }
}