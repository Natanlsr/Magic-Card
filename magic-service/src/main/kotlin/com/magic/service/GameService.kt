package com.magic.service

import com.magic.enums.GameTypeEnum
import com.magic.enums.PlayerTypeEnum
import com.magic.model.Game
import com.magic.model.Player
import com.magic.model.PlayerType
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Service

@Service
class GameService
@Autowired constructor(
    val cardService: CardService
){

    fun startVsCPU(player: Player): Game{

        val cpuPlayer = Player(id = 1,name = "CPU",playerTypeEnum = PlayerTypeEnum.COMPUTER)

        val game = Game(
            gameType = GameTypeEnum.CPU,
            cardsToBuyByComputer = cardService.getCardsPlayers(PlayerTypeEnum.COMPUTER)!!,
            cardsToBuyByPlayers  = cardService.getCardsPlayers(PlayerTypeEnum.PLAYER)!!,
            players = listOf(player,cpuPlayer)
        )

        game.setCardsInPlayers()
        return game
    }


}