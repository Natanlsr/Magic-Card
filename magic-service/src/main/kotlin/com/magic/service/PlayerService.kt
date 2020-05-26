package com.magic.service

import com.magic.model.Player
import com.magic.repository.PlayerRepository
import com.magic.service.enums.ExceptionEnum
import com.magic.service.exceptions.PlayerNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PlayerService@Autowired constructor(
    val playerRepository: PlayerRepository
) {

    fun findPlayerById(id: Int): Player{
        return  playerRepository.findById(id)
            .orElseThrow{ throw PlayerNotFoundException(ExceptionEnum.PLAYER_NOT_FOUND.message) }
    }

    fun savePlayer(player: Player): Player{
        return playerRepository.save(player)
    }
}