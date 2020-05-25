package com.magic.model

import com.magic.enums.PlayerTypeEnum
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.SequenceGenerator

@Entity
data class Player(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_sequence")
    @SequenceGenerator(name = "player_sequence", sequenceName = "player_sequence", allocationSize = 1)
    val id: Int? = null,

    val name: String? = null,

    var life: Int = 20,

    var mana: Int = 20,

    @ManyToMany
    var deck: List<Card> = emptyList(),

    @Enumerated(EnumType.STRING)
    var playerTypeEnum: PlayerTypeEnum = PlayerTypeEnum.PLAYER
){
    companion object {
        @JvmField val MaximmumNumberCards = 4
        @JvmField val RecoverManaJumpRouund = 2
    }
}