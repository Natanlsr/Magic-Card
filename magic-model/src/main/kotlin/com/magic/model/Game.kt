package com.magic.model

import com.magic.enums.GameStatusEnum
import com.magic.enums.GameTypeEnum
import lombok.NoArgsConstructor
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.SequenceGenerator

@NoArgsConstructor
@Entity
data class Game(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_sequence")
    @SequenceGenerator(name = "game_sequence", sequenceName = "game_sequence", allocationSize = 1)
    val id: Int? = null,

    var round: Int = 0,

    @ManyToMany
    var players: List<Player>  = emptyList(),

    @Enumerated(EnumType.STRING)
    var gameType: GameTypeEnum = GameTypeEnum.CPU,

    @Enumerated(EnumType.STRING)
    var gameStatus: GameStatusEnum = GameStatusEnum.PREPARING,

    @ManyToMany
    var cardsToBuyByPlayers: List<Card> = emptyList(),

    @ManyToMany
    var cardsToBuyByComputer: List<Card> = emptyList(),

    var indexPlayerTurn: Int = 0
)