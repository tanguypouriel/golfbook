package com.mindeurfou.golfbook.data.tournament.local

import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.utils.DateAsLongSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class TournamentDetails(
    val id : Int,
    val name : String,
    val state: GBState,
    val leaderBoard: Map<String, Int>? = null,
    @Serializable(with = DateAsLongSerializer::class)
    val createdAt: LocalDate
)
