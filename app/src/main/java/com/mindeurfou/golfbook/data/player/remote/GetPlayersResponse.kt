package com.mindeurfou.golfbook.data.player.remote

import com.mindeurfou.golfbook.data.player.local.Player
import kotlinx.serialization.Serializable

@Serializable
data class GetPlayersResponse(
    val selfPlayer: Player,
    val players: List<Player>
)
