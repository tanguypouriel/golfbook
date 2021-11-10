package com.mindeurfou.golfbook.data.game.local

import kotlinx.serialization.Serializable

@Serializable
data class ScoreDetails(
    val score: Int,
    val scoreType: ScoreType,
    val net: String
)
