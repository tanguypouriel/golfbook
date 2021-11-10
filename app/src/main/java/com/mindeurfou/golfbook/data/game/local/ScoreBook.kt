package com.mindeurfou.golfbook.data.game.local

import kotlinx.serialization.Serializable

@Serializable
data class ScoreBook(
    val par: List<Int>, // TODO sortir du scoreBook minimiserait t il les données réseaux ?
    val playerScores : List<PlayerScore>
)
