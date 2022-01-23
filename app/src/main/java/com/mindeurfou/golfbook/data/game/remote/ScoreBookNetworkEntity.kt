package com.mindeurfou.golfbook.data.game.remote

import com.mindeurfou.golfbook.data.game.local.PlayerScore
import kotlinx.serialization.Serializable

@Serializable
data class ScoreBookNetworkEntity(
    val playerScores : List<PlayerScoreNetworkEntity>
)
