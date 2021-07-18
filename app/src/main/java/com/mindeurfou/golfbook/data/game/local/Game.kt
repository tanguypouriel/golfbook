package com.mindeurfou.golfbook.data.game.local

import com.mindeurfou.golfbook.data.GBState

data class Game(
    val id : Int,
    val state: GBState,
    val currentHole: Int,
    val players: List<String>?
)
