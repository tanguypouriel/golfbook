package com.mindeurfou.golfbook.data.game.remote

import kotlinx.serialization.Serializable

@Serializable
data class PostGameNetworkEntity(
    val courseId: Int,
    val tournamentId: Int? = null
)