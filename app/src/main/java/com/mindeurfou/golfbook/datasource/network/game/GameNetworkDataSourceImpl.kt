package com.mindeurfou.golfbook.datasource.network.game

import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.game.remote.PatchGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PostGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PutGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PutScoreBookNetworkEntity
import com.mindeurfou.golfbook.datasource.network.RetrofitBuilder
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class GameNetworkDataSourceImpl @Inject constructor(
    private val gameApiService: GameApiService
) : GameNetworkDataSource {

    override suspend fun getGame(gameId: Int): GameDetails =
        gameApiService.getGame(gameId)

    override suspend fun getGames(): List<Game> =
        gameApiService.getGames()

    override suspend fun postGame(postGame: PostGameNetworkEntity): GameDetails =
        gameApiService.postGame(postGame)

    override suspend fun updateGame(putGame: PutGameNetworkEntity): GameDetails =
        gameApiService.updateGame(putGame.id, putGame)

    override suspend fun deleteGame(gameId: Int) =
        gameApiService.deleteGame(gameId)

    override suspend fun addOrDeletePlayer(gameId: Int, patchGame: PatchGameNetworkEntity) =
        gameApiService.addOrDeletePlayer(gameId, patchGame)

    override suspend fun getScoreBook(gameId: Int): Map<String, List<Int?>> =
        gameApiService.getScoreBook(gameId)

    override suspend fun putScoreBook(putScoreBook: PutScoreBookNetworkEntity): Map<String, List<Int?>> =
        gameApiService.putScoreBook(putScoreBook.gameId, putScoreBook)
}