package com.mindeurfou.golfbook.data.game.remote

import com.mindeurfou.golfbook.data.game.local.ScoringSystem
import kotlinx.serialization.Serializable

@Serializable
data class PostGameNetworkEntity(
    val courseId: Int,
    val tournamentId: Int? = null,
    val scoringSystem: ScoringSystem
)