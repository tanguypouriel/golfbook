package com.mindeurfou.golfbook.datasource.network.game

import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.game.remote.GameDetailsNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PostGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.ScoreBookNetworkEntity
import com.mindeurfou.golfbook.data.player.local.Player

interface GameNetworkDataSource {
    suspend fun getGame(gameId: Int): GameDetailsNetworkEntity
    suspend fun getInitGames() : List<Game>
    suspend fun getGamesByTournamentId(tournamentId: Int): List<Game>
    suspend fun getGamesByPlayerId(playerId: Int): List<Game>
    suspend fun postGame(postGame: PostGameNetworkEntity): GameDetailsNetworkEntity
    suspend fun deleteGame(gameId: Int)
    suspend fun updatePlayers(gameId: Int, body: Map<String, List<Player>>)
    suspend fun updateState(gameId: Int, body: Map<String, GBState>)
    suspend fun getPlayersReady(gameId: Int) : List<String>
    suspend fun updatePlayersReady(gameId: Int, playersReady: List<String>?)
    suspend fun getScoreBook(gameId: Int): ScoreBookNetworkEntity
    suspend fun putScoreBook(gameId: Int, scoreBookNetworkEntity: ScoreBookNetworkEntity): ScoreBookNetworkEntity
}