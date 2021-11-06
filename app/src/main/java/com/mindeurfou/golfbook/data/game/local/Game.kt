package com.mindeurfou.golfbook.data.game.local

import com.mindeurfou.golfbook.data.GBData
import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.utils.DateAsLongSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Game(
    val id : Int,
    val name: String,
    val state: GBState,
    val scoringSystem: ScoringSystem,
    val players: List<Player>?,
    val courseName: String,
    @Serializable(with = DateAsLongSerializer::class)
    val createdAt: LocalDate
) : GBData
