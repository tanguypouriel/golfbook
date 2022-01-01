package com.mindeurfou.golfbook.datasource.network.game

import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.game.local.ScoreBook
import com.mindeurfou.golfbook.data.game.remote.PatchGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PostGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PutGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PutScoreBookNetworkEntity

interface GameNetworkDataSource {
    suspend fun getGame(gameId: Int): GameDetails
    suspend fun getPendingGames() : List<Game>?
    suspend fun getGamesByTournamentId(tournamentId: Int): List<Game>
    suspend fun getGamesByPlayerId(playerId: Int): List<Game>
    suspend fun postGame(postGame: PostGameNetworkEntity): GameDetails
    suspend fun updateGame(putGame: PutGameNetworkEntity): GameDetails
    suspend fun deleteGame(gameId: Int)
    suspend fun addOrDeletePlayer(gameId: Int, patchGame: PatchGameNetworkEntity)
    suspend fun getPlayersReady(gameId: Int) : List<String>
    suspend fun updatePlayersReady(gameId: Int, playersReady: List<String>?)
    suspend fun getScoreBook(gameId: Int): ScoreBook
    suspend fun putScoreBook(putScoreBook: PutScoreBookNetworkEntity): ScoreBook
}