package com.mindeurfou.golfbook.datasource.network.player

import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.data.player.remote.PlayerNetworkEntity
import com.mindeurfou.golfbook.data.player.remote.PostPlayerNetworkEntity
import com.mindeurfou.golfbook.data.player.remote.PutPlayerNetworkEntity
import retrofit2.http.*

interface PlayerApiService {

    @GET("/player/{playerId}")
    suspend fun getPlayer(@Path("playerId") playerId: Int): PlayerNetworkEntity

    @GET("/player")
    suspend fun getPlayers(): List<PlayerNetworkEntity>

    @POST("/player")
    suspend fun postPlayer(@Body postPlayer: PostPlayerNetworkEntity): Player

    @PUT("/player/{playerId}")
    suspend fun updatePlayer(@Path("playerId") playerId: Int, @Body putPlayer: PutPlayerNetworkEntity): Player

    @DELETE("/player/{playerId}")
    suspend fun deletePlayer(@Path("playerId") playerId: Int): Boolean

}