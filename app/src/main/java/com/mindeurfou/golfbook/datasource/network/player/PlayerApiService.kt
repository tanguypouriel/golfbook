package com.mindeurfou.golfbook.datasource.network.player

import com.mindeurfou.golfbook.data.player.local.Player
import retrofit2.http.GET
import retrofit2.http.Path

interface PlayerApiService {

    @GET("/player/{playerId}")
    suspend fun getPlayer(@Path("playerId") playerId: Int): Player

    @GET("/player")
    suspend fun getPlayers(): List<Player>

}