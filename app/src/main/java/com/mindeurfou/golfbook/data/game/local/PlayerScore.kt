package com.mindeurfou.golfbook.data.game.local

import kotlinx.serialization.Serializable

@Serializable
data class PlayerScore(
    val name: String,
    val scores: List<ScoreDetails?>,
    val scoreSum: Int?,
    val netSum: String?
)
