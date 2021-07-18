package com.mindeurfou.golfbook.data.player.remote

import com.mindeurfou.golfbook.data.player.local.Player

data class PutPlayerNetworkEntity(
    val id: Int,
    val username: String,
    val drawableResourceId : Int
)

fun Player.toPutPlayerNetworkEntity() = PutPlayerNetworkEntity(
    id = id,
    username = username,
    drawableResourceId = drawableResourceId
)
