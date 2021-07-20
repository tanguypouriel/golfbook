package com.mindeurfou.golfbook.data.game.remote

import com.mindeurfou.golfbook.data.GBState
import kotlinx.serialization.Serializable

@Serializable
data class PutGameNetworkEntity(
    val id: Int,
    val state: GBState,
    val courseId: Int
)
