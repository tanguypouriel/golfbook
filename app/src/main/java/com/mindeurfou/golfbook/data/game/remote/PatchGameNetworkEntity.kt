package com.mindeurfou.golfbook.data.game.remote

import kotlinx.serialization.Serializable

@Serializable
data class PatchGameNetworkEntity(
    val playerId: Int,
    val playing: Boolean
)
