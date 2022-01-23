package com.mindeurfou.golfbook.datasource.network.game

import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.game.remote.GameDetailsNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PostGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.ScoreBookNetworkEntity
import retrofit2.http.*

interface GameApiService {

    @GET("/game/{gameId}")
    suspend fun getGame(@Path("gameId") gameId: Int): GameDetailsNetworkEntity

    @GET("/game?state=init")
    suspend fun getInitGames(): List<Game>?

    @GET("/game")
    suspend fun getGamesByTournamentId(@Query("tournamentId") tournamentId : Int): List<Game>

    @GET("/game")
    suspend fun getGamesByPlayerId(@Query("playerId") playerId: Int): List<Game>

    @POST("/game")
    suspend fun postGame(@Body postGame: PostGameNetworkEntity): GameDetailsNetworkEntity

    @DELETE("/game/{gameId}")
    suspend fun deleteGame(@Path("gameId") gameId: Int)

    @PATCH("/game/{gameId}")
    suspend fun patchGame(@Path("gameId") gameId: Int, @Body body: Map<String, Any>)

    @GET("/game/{gameId}/scorebook")
    suspend fun getScoreBook(@Path("gameId") gameId: Int): ScoreBookNetworkEntity

    @PUT("/game/{gameId}/scorebook")
    suspend fun putScoreBook(@Path("gameId") gameId: Int, @Body scoreBookNetworkEntity: ScoreBookNetworkEntity): ScoreBookNetworkEntity

    @GET("/game/{gameId}/playersReady")
    suspend fun getPlayersReady(@Path("gameId") gameId: Int) : List<String>

    @PUT("/game/{gameId}/playersReady")
    suspend fun updatePlayersReady(@Path("gameId") gameId: Int, @Body putPlayersReady: List<String>?)
}