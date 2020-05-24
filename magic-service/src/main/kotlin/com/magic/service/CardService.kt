package com.magic.service

import com.magic.enums.PlayerTypeEnum
import com.magic.model.Card
import com.magic.repository.CardRepository
import com.magic.repository.PlayerTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CardService
@Autowired constructor(
    val cardRepository: CardRepository,
    val playerTypeRepository: PlayerTypeRepository
){

    fun getCardsPlayers(playerTypeEnum: PlayerTypeEnum): List<Card>?{
        val playerType = playerTypeRepository.findByType(playerTypeEnum)
        return cardRepository.findByPlayerType(playerType)
    }

}