package com.mindeurfou.golfbook.data.game.local

import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.player.local.Player
import kotlinx.serialization.Serializable

@Serializable
data class GameDetails(
    val id : Int,
    val name: String,
    val state: GBState,
    val scoringSystem: ScoringSystem,
    val courseName: String,
    val par: List<Int>,
    val players: List<Player>,
    val playersReady: List<String>,
    //                  name  , list of scores (null if not played yet)
    val scoreSummaries: List<ScoreSummary>,
    val scoreBook : ScoreBook? // TODO changer pour scorebookId ?
)
