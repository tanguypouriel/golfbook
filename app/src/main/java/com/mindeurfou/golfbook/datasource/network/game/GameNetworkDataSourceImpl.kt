package com.mindeurfou.golfbook.datasource.network.game

import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.game.remote.PatchGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PostGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PutGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PutScoreBookNetworkEntity
import com.mindeurfou.golfbook.datasource.network.RetrofitBuilder
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class GameNetworkDataSourceImpl : GameNetworkDataSource {

    private val apiService: GameApiService = RetrofitBuilder.retrofit.create(GameApiService::class.java)

    override suspend fun getGame(gameId: Int): GameDetails =
        apiService.getGame(gameId)

    override suspend fun getGames(): List<Game> =
        apiService.getGames()

    override suspend fun postGame(postGame: PostGameNetworkEntity): GameDetails =
        apiService.postGame(postGame)

    override suspend fun updateGame(putGame: PutGameNetworkEntity): GameDetails =
        apiService.updateGame(putGame.id, putGame)

    override suspend fun deleteGame(gameId: Int) =
        apiService.deleteGame(gameId)

    override suspend fun addOrDeletePlayer(gameId: Int, patchGame: PatchGameNetworkEntity) =
        apiService.addOrDeletePlayer(gameId, patchGame)

    override suspend fun getScoreBook(gameId: Int): Map<String, List<Int?>> =
        apiService.getScoreBook(gameId)

    override suspend fun putScoreBook(putScoreBook: PutScoreBookNetworkEntity): Map<String, List<Int?>> =
        apiService.putScoreBook(putScoreBook.gameId, putScoreBook)
}