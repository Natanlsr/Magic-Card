package com.magic.model

import lombok.Builder
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator

@Entity
@Builder
@NoArgsConstructor
data class Card (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_sequence")
    @SequenceGenerator(name = "card_sequence", sequenceName = "card_sequence", allocationSize = 1)
    @Setter
    val id: Long? = null,

    val mana: Int,

    val damage: Int,

    val playerType: String,

    val description: String
)