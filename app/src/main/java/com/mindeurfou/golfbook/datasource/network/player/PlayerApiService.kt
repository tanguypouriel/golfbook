package com.mindeurfou.golfbook.datasource.network.player

import com.mindeurfou.golfbook.data.Credentials
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.data.player.remote.PostPlayerNetworkEntity
import com.mindeurfou.golfbook.data.player.remote.PutPlayerNetworkEntity
import retrofit2.http.*

interface PlayerApiService {

    @GET("/player/{playerId}")
    suspend fun getPlayer(@Path("playerId") playerId: Int): Player

    @POST("/login")
    suspend fun login(@Body credentials: Credentials): Map<String, String>

    @GET("/player")
    suspend fun getPlayers(@Query("limit") limit: Int? = null, @Query("offset") offset: Int? = null): List<Player>

    @POST("/player")
    suspend fun postPlayer(@Body postPlayer: PostPlayerNetworkEntity): Map<String, String>

    @PUT("/player/{playerId}")
    suspend fun updatePlayer(@Path("playerId") playerId: Int, @Body putPlayer: PutPlayerNetworkEntity): Player

    @DELETE("/player/{playerId}")
    suspend fun deletePlayer(@Path("playerId") playerId: Int): Boolean

}