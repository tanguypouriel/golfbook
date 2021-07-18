package com.mindeurfou.golfbook.data.tournament.remote

import com.mindeurfou.golfbook.data.GBState

data class PutTournamentNetworkEntity(
    val id: Int,
    val name: String? = null,
    val state: GBState? = null
)
