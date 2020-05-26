package com.magic.service

import com.magic.enums.PlayerTypeEnum
import com.magic.model.Card
import com.magic.repository.CardRepository
import com.magic.repository.PlayerTypeRepository
import com.magic.service.enums.ExceptionEnum
import com.magic.service.exceptions.CardNotFoundException
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

    fun getAllCards(): List<Card>{
        return cardRepository.findAll()
    }

    fun findCardById(id: Int): Card{
        return cardRepository.findById(id).orElseThrow{ throw CardNotFoundException(ExceptionEnum.CARD_NOT_FOUND.message) }
    }


}