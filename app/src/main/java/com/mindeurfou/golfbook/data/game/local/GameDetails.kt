package com.mindeurfou.golfbook.data.game.local

import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.player.local.Player
import kotlinx.serialization.Serializable

@Serializable
data class GameDetails(
    val id : Int,
    val state: GBState,
    val scoringSystem: ScoringSystem,
    val courseName: String,
    val courseId: Int,
    val currentHole: Int,
    val players: List<Player>,
    //                  name  , list of scores (null if not played yet)
    val scoreBook : ScoreBook?
)
