package com.mindeurfou.golfbook.data.game.remote

import kotlinx.serialization.Serializable

@Serializable
data class PutScoreBookNetworkEntity(
    val gameId: Int,
    val scoreBook: Map<Int, List<Int?>>
)
