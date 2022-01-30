package com.mindeurfou.golfbook.data.game.remote

import kotlinx.serialization.Serializable

@Serializable
data class PlayerScoreNetworkEntity(
    val name: String,
    val scores: List<ScoreDetailsNetworkEntity>,
    val scoreSum: String,
    val netSum: String
)