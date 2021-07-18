package com.mindeurfou.golfbook.data.game.remote

data class PutScoreBookNetworkEntity(
    val gameId: Int,
    val scoreBook: Map<Int, List<Int?>>
)
