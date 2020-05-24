package com.magic.repository

import com.magic.enums.PlayerTypeEnum
import com.magic.model.PlayerType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayerTypeRepository: JpaRepository<PlayerType, Long> {
    fun findByType(playerTypeEnum: PlayerTypeEnum): PlayerType
}