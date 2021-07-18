package com.mindeurfou.golfbook.data.game.remote

data class PostGameNetworkEntity(
    val courseId: Int,
    val tournamentId: Int? = null
)