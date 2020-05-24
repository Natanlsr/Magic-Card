package com.magic.model

import com.magic.enums.PlayerTypeEnum
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator

@Entity
@NoArgsConstructor
data class PlayerType(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_sequence")
    @SequenceGenerator(name = "player_type_sequence", sequenceName = "player_type_sequence", allocationSize = 1)
    @Setter
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    val type: PlayerTypeEnum
)