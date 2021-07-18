package com.mindeurfou.golfbook.data.game.remote

import com.mindeurfou.golfbook.data.GBState

data class PutGameNetworkEntity(
    val id: Int,
    val state: GBState,
    val courseId: Int
)
