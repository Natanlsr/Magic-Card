package com.magic.repository

import com.magic.model.Card
import com.magic.model.PlayerType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CardRepository: JpaRepository<Card,Long> {
    fun findByPlayerType(playerType: PlayerType): List<Card>?
}