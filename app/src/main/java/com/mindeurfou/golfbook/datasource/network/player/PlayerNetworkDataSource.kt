package com.mindeurfou.golfbook.datasource.network.player

import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.data.player.remote.GetPlayersResponse
import com.mindeurfou.golfbook.data.player.remote.PostPlayerNetworkEntity

interface PlayerNetworkDataSource {
    suspend fun login(username: String, password: String): Map<String, String>?
    suspend fun getPlayer(playerId: Int): Player
    suspend fun getPlayers(limit: Int? = null, offset: Int? = null): GetPlayersResponse
    suspend fun postPlayer(postPlayer: PostPlayerNetworkEntity): Map<String, String>
    suspend fun updatePlayer(player: Player): Player
    suspend fun deletePlayer(playerId: Int): Boolean
}