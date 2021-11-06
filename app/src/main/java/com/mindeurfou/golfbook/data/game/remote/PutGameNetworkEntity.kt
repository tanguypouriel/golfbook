package com.mindeurfou.golfbook.data.game.remote

import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.game.local.ScoringSystem
import kotlinx.serialization.Serializable

@Serializable
data class PutGameNetworkEntity(
    val id: Int,
    val state: GBState,
    val scoringSystem: ScoringSystem,
    val courseId: Int
)
