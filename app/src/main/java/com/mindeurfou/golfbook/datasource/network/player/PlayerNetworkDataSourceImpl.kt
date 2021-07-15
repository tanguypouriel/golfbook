package com.mindeurfou.golfbook.datasource.network.player

import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.datasource.network.RetrofitBuilder

class PlayerNetworkDataSourceImpl : PlayerNetworkDataSource {

    private val apiService = RetrofitBuilder.retrofit.create(PlayerApiService::class.java)

    override suspend fun getPlayer(playerId: Int): Player =
        apiService.getPlayer(playerId)

    override suspend fun getPlayers(): List<Player> =
        apiService.getPlayers()


}