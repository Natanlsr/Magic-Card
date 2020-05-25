package com.magic.repository

import com.magic.model.Game
import org.springframework.data.jpa.repository.JpaRepository

interface GameRepository: JpaRepository<Game, Long> {
}