package com.mindeurfou.golfbook.data.game.local

import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.game.remote.PostGameNetworkEntity
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.utils.DateAsLongSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class GameDetails(
    val id : Int,
    val name: String,
    val state: GBState,
    @Serializable(with = DateAsLongSerializer::class)
    val date: LocalDate,
    val scoringSystem: ScoringSystem,
    val courseName: String,
    val par: List<Int>,
    val players: List<Player>,
    //                  name  , list of scores (null if not played yet)
    val scoreSummaries: List<ScoreSummary>,
    val scoreBook : ScoreBook?
) {

    fun equalsPostGame(postGame: PostGameNetworkEntity): Boolean {
        return name == postGame.name &&
                scoringSystem == postGame.scoringSystem &&
                courseName == postGame.courseName && state == GBState.INIT
    }
}
