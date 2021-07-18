package com.mindeurfou.golfbook.datasource.network.game

import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.game.remote.PatchGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PostGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PutGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PutScoreBookNetworkEntity
import retrofit2.http.*

interface GameApiService {

    @GET("/game/{gameId}")
    suspend fun getGame(@Path("gameId") gameId: Int): GameDetails

    @GET("/game")
    suspend fun getGames(): List<Game>

    @POST("/game")
    suspend fun postGame(@Body postGame: PostGameNetworkEntity): GameDetails

    @PUT("/game/{gameId}")
    suspend fun updateGame(@Path("gameId") gameId: Int, @Body putGame: PutGameNetworkEntity): GameDetails

    @DELETE("/game/{gameId}")
    suspend fun deleteGame(@Path("gameId") gameId: Int)

    @PATCH("/game/{gameId}")
    suspend fun addOrDeletePlayer(@Path("gameId") gameId: Int, @Body patchGame: PatchGameNetworkEntity)

    @GET("/game/{gameId}/scorebook")
    suspend fun getScoreBook(@Path("gameId") gameId: Int): Map<String, List<Int?>>

    @PUT("/game/{gameId}/scorebook")
    suspend fun putScoreBook(@Path("gameId") gameId: Int, @Body putScoreBook: PutScoreBookNetworkEntity): Map<String, List<Int?>>

}