package com.magic.model

import lombok.Builder
import lombok.NoArgsConstructor
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.SequenceGenerator

@Entity
@Builder
@NoArgsConstructor
data class Card(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_sequence")
    @SequenceGenerator(name = "card_sequence")
    val id: Int? = null,

    var name: String = "",

    var manaCost: Int = 0,

    var manaDamage: Int = 0,

    var manaRecover: Int = 0,

    var lifeDamage: Int = 0,

    var description: String = "",

    @ManyToOne
    @JoinColumn(name="player_type", referencedColumnName = "id")
    var playerType: PlayerType? = null

)