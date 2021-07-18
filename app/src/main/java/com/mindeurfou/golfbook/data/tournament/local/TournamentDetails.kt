package com.mindeurfou.golfbook.data.tournament.local

import com.mindeurfou.golfbook.data.GBState
import java.time.LocalDate

data class TournamentDetails(
    val id : Int,
    val name : String,
    val state: GBState,
    val leaderBoard: Map<String, Int>? = null,
    val createdAt: LocalDate
)
