package com.mindeurfou.golfbook.data.player.remote

data class PostPlayerNetworkEntity(
    val name: String,
    val lastName : String,
    val username: String,
    val drawableResourceId : Int
)
