package com.mindeurfou.golfbook.data.tournament.local

import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.utils.DateAsLongSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Tournament(
    val id : Int,
    val name : String,
    val state: GBState,
    @Serializable(with = DateAsLongSerializer::class)
    val  createdAt: LocalDate
)
