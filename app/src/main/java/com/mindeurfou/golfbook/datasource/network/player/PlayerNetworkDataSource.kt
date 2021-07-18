package com.mindeurfou.golfbook.datasource.network.player

import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.data.player.remote.PostPlayerNetworkEntity

interface PlayerNetworkDataSource {
    suspend fun getPlayer(playerId: Int): Player
    suspend fun getPlayers(): List<Player>
    suspend fun postPlayer(postPlayer: PostPlayerNetworkEntity): Player
    suspend fun updatePlayer(player: Player): Player
    suspend fun deletePlayer(playerId: Int): Boolean
}