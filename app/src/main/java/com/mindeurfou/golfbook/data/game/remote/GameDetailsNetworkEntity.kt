package com.mindeurfou.golfbook.data.game.remote

import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.game.local.ScoreBook
import com.mindeurfou.golfbook.data.game.local.ScoringSystem
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.utils.DateAsLongSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class GameDetailsNetworkEntity(
    val id : Int,
    val name: String,
    val state: GBState,
    @Serializable(with = DateAsLongSerializer::class)
    val date: LocalDate,
    val scoringSystem: ScoringSystem,
    val courseName: String,
    val par: List<Int>,
    val players: List<Player>,
    val scoreBook : ScoreBookNetworkEntity
) {

    fun equalsPostGame(postGame: PostGameNetworkEntity): Boolean {
        return name == postGame.name &&
                scoringSystem == postGame.scoringSystem &&
                courseName == postGame.courseName && state == GBState.INIT
    }
}
