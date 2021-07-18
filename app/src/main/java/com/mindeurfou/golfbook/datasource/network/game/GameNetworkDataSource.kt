package com.mindeurfou.golfbook.datasource.network.game

import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.game.remote.PatchGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PostGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PutGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PutScoreBookNetworkEntity

interface GameNetworkDataSource {
    suspend fun getGame(gameId: Int): GameDetails
    suspend fun getGames(): List<Game>
    suspend fun postGame(postGame: PostGameNetworkEntity): GameDetails
    suspend fun updateGame(putGame: PutGameNetworkEntity): GameDetails
    suspend fun deleteGame(gameId: Int)
    suspend fun addOrDeletePlayer(gameId: Int, patchGame: PatchGameNetworkEntity)
    suspend fun getScoreBook(gameId: Int): Map<String, List<Int?>>
    suspend fun putScoreBook(putScoreBook: PutScoreBookNetworkEntity): Map<String, List<Int?>>
}