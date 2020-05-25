package com.magic.repository

import com.magic.model.Game
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GameRepository: JpaRepository<Game, Int> {
}