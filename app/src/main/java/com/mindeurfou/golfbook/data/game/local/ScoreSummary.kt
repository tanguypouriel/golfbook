package com.mindeurfou.golfbook.data.game.local

import kotlinx.serialization.Serializable

@Serializable
data class ScoreSummary(
    val rank: String,
    val name: String,
    val score: String
)
