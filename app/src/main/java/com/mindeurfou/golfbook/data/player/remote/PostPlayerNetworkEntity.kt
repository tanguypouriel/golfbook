package com.mindeurfou.golfbook.data.player.remote

import kotlinx.serialization.Serializable

@Serializable
data class PostPlayerNetworkEntity(
    val name: String,
    val lastName : String,
    val username: String,
    val password: String,
    val avatarId : Int,
    val realUser: Boolean
)
