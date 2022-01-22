package com.mindeurfou.golfbook.data.player.remote

import com.mindeurfou.golfbook.data.player.local.Player
import kotlinx.serialization.Serializable

@Serializable
data class PutPlayerNetworkEntity(
    val id: Int,
    val username: String,
    val avatarId: Int
)

fun Player.toPutPlayerNetworkEntity() = PutPlayerNetworkEntity(
    id = id,
    username = username,
    avatarId = avatarId
)
