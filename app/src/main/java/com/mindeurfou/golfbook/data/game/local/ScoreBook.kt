package com.mindeurfou.golfbook.data.game.local

import kotlinx.serialization.Serializable

@Serializable
data class ScoreBook(
    val value : Map<String, List<Int?>>
)
