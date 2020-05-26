package com.magic.model

import com.magic.enums.GameStatusEnum
import com.magic.enums.GameTypeEnum
import lombok.NoArgsConstructor
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.SequenceGenerator

@Entity
data class Game(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_sequence")
    @SequenceGenerator(name = "game_sequence", sequenceName = "game_sequence", allocationSize = 1)
    val id: Int? = null,

    var round: Int = 0,

    @ManyToMany
    @JoinTable(
        name="players_game",
        joinColumns = [JoinColumn(name = "game_id")],
        inverseJoinColumns = [JoinColumn(name="player_id")]
    )
    var players: List<Player>  = emptyList(),

    @Enumerated(EnumType.STRING)
    var gameType: GameTypeEnum = GameTypeEnum.CPU,

    @Enumerated(EnumType.STRING)
    var gameStatus: GameStatusEnum = GameStatusEnum.PREPARING,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name="game_deck_players",
        joinColumns = [JoinColumn(name = "game_id")],
        inverseJoinColumns = [JoinColumn(name="card_id")]

    )
    var cardsToBuyByPlayers: List<Card> = emptyList(),

    @ManyToMany
    @JoinTable(
        name="game_deck_cpu",
        joinColumns = [JoinColumn(name = "game_id")],
        inverseJoinColumns = [JoinColumn(name="card_id")]

    )
    var cardsToBuyByComputer: List<Card> = emptyList(),

    var indexPlayerTurn: Int = 0
)