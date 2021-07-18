package com.mindeurfou.golfbook.data.game.local

import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.player.local.Player

data class GameDetails(
    val id : Int,
    val state: GBState,
    val courseName: String,
    val courseId: Int,
    val currentHole: Int,
    val players: List<Player>,
    //                  name  , list of scores (null if not played yet)
    val scoreBook : Map<String, List<Int?>>
)
