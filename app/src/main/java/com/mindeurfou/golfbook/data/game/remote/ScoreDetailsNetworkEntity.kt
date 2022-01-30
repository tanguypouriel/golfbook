package com.mindeurfou.golfbook.data.game.remote

import kotlinx.serialization.Serializable

@Serializable
data class ScoreDetailsNetworkEntity(
    val score: Int,
    val net: String
)
