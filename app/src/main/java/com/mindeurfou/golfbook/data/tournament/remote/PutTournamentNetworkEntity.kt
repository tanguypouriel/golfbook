package com.mindeurfou.golfbook.data.tournament.remote

import com.mindeurfou.golfbook.data.GBState
import kotlinx.serialization.Serializable

@Serializable
data class PutTournamentNetworkEntity(
    val id: Int,
    val name: String? = null,
    val state: GBState? = null
)
