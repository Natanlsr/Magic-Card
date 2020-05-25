package com.magic.model

import lombok.Builder
import lombok.NoArgsConstructor
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.SequenceGenerator

@Entity
@Builder
@NoArgsConstructor
data class Card (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_sequence")
    @SequenceGenerator(name = "card_sequence", sequenceName = "card_sequence", allocationSize = 1)
    val id: Long? = null,

    val name: String,

    val manaCost: Int,

    val manaDamage: Int,

    val manaRecover: Int,

    val lifeDamage: Int,

    val description: String,

    @ManyToOne
    val playerType: PlayerType
)