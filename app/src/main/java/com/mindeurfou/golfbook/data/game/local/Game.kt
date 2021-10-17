package com.mindeurfou.golfbook.data.game.local

import com.mindeurfou.golfbook.data.GBData
import com.mindeurfou.golfbook.data.GBState
import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id : Int,
    val state: GBState,
    val currentHole: Int,
    val players: List<String>?
) : GBData
