package com.mindeurfou.golfbook.datasource.network.player

import com.mindeurfou.golfbook.data.player.local.Player

interface PlayerNetworkDataSource {

    suspend fun getPlayer(playerId: Int): Player

    suspend fun getPlayers(): List<Player>
}