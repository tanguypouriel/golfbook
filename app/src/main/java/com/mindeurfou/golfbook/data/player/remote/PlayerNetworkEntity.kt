package com.mindeurfou.golfbook.data.player.remote

import com.mindeurfou.golfbook.data.player.local.Player

data class PlayerNetworkEntity(
    val id: Int,
    val name: String,
    val lastName : String,
    val username : String,
    val drawableResourceId: Int
) {

    fun networkEntityToPlayer() = Player(
        id = id,
        name = name,
        lastName = lastName,
        username = username,
        drawableResourceId = drawableResourceId
    )

    fun playerToNetworkEntity() = PlayerNetworkEntity(
        id = id,
        name = name,
        lastName = lastName,
        username = username,
        drawableResourceId = drawableResourceId
    )

}

