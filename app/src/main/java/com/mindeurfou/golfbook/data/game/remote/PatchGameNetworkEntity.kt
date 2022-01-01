package com.mindeurfou.golfbook.data.game.remote

import kotlinx.serialization.Serializable

@Serializable
data class PatchGameNetworkEntity(
    val name: String,
    val lastName: String,
    val username: String,
    val avatarId: Int,
    val playing: Boolean
)
