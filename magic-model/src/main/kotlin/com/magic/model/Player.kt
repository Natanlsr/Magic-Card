package com.magic.model

import com.magic.enums.PlayerTypeEnum
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
data class Player(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_sequence")
    @SequenceGenerator(name = "player_sequence", sequenceName = "player_sequence", allocationSize = 1)
    val id: Int? = null,

    val name: String = "",

    var life: Int = 20,

    var mana: Int = 20,



    @ManyToMany()
    @JoinTable(
        name="player_deck",
        joinColumns = [JoinColumn(name = "player_id")],
        inverseJoinColumns = [JoinColumn(name="card_id")]

    )
    var deck: List<Card> = emptyList(),

    @Enumerated(EnumType.STRING)
    var playerType: PlayerTypeEnum = PlayerTypeEnum.PLAYER
){
    companion object {
        @JvmField val MaximmumNumberCards = 4
        @JvmField val RecoverManaJumpRouund = 2
        @JvmField val RecoverManaPerRound = 2

    }
}