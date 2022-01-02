package com.mindeurfou.golfbook.datasource.network.game

import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.game.local.ScoreBook
import com.mindeurfou.golfbook.data.game.remote.PostGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PutScoreBookNetworkEntity
import retrofit2.http.*

interface GameApiService {

    @GET("/game/{gameId}")
    suspend fun getGame(@Path("gameId") gameId: Int): GameDetails

    @GET("/game?state=init")
    suspend fun getInitGames(): List<Game>?

    @GET("/game")
    suspend fun getGamesByTournamentId(@Query("tournamentId") tournamentId : Int): List<Game>

    @GET("/game")
    suspend fun getGamesByPlayerId(@Query("playerId") playerId: Int): List<Game>

    @POST("/game")
    suspend fun postGame(@Body postGame: PostGameNetworkEntity): GameDetails

    @DELETE("/game/{gameId}")
    suspend fun deleteGame(@Path("gameId") gameId: Int)

    @PATCH("/game/{gameId}")
    suspend fun patchGame(@Path("gameId") gameId: Int, @Body body: Map<String, Any>)

    @GET("/game/{gameId}/scorebook")
    suspend fun getScoreBook(@Path("gameId") gameId: Int): ScoreBook

    @PUT("/game/{gameId}/scorebook")
    suspend fun putScoreBook(@Path("gameId") gameId: Int, @Body putScoreBook: PutScoreBookNetworkEntity): ScoreBook

    @GET("/game/{gameId}/playersReady")
    suspend fun getPlayersReady(@Path("gameId") gameId: Int) : List<String>

    @PUT("/game/{gameId}/playersReady")
    suspend fun updatePlayersReady(@Path("gameId") gameId: Int, @Body putPlayersReady: List<String>?)
}