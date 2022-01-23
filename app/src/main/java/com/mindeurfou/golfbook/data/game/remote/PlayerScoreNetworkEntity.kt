package com.mindeurfou.golfbook.data.game.remote

import com.mindeurfou.golfbook.data.game.local.ScoreDetails
import kotlinx.serialization.Serializable

@Serializable
data class PlayerScoreNetworkEntity(
    val name: String,
    val scores: List<ScoreDetails>,
    val scoreSum: String,
    val netSum: String
)