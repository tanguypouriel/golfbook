package com.mindeurfou.golfbook.datasource.network.game

import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.game.local.ScoreBook
import com.mindeurfou.golfbook.data.game.remote.PostGameNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PutScoreBookNetworkEntity
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.utils.GBException
import com.mindeurfou.golfbook.utils.GBHttpStatusCode
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

@ExperimentalSerializationApi
class GameNetworkDataSourceImpl @Inject constructor(
    private val gameApiService: GameApiService
) : GameNetworkDataSource {

    override suspend fun getGame(gameId: Int): GameDetails {
        try {
            return gameApiService.getGame(gameId)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NOT_FOUND)
                throw GBException(GBException.GAME_NOT_FIND_MESSAGE)
            else
                throw UnknownError("status code is ${e.code()}")
        }
    }

    override suspend fun getInitGames(): List<Game>? =
            gameApiService.getInitGames()

    override suspend fun getGamesByTournamentId(tournamentId: Int): List<Game> {
        try {
            return gameApiService.getGamesByTournamentId(tournamentId)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NO_CONTENT)
                throw GBException(GBException.NO_RESOURCES_MESSAGE)
            else
                throw UnknownError("status code is ${e.code()}")
        }
    }

    override suspend fun getGamesByPlayerId(playerId: Int): List<Game> {
        try {
            return gameApiService.getGamesByPlayerId(playerId)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NO_CONTENT)
                throw GBException(GBException.NO_RESOURCES_MESSAGE)
            else
                throw UnknownError("status code is ${e.code()}")
        }
    }

    override suspend fun postGame(postGame: PostGameNetworkEntity): GameDetails {
        try {
            return gameApiService.postGame(postGame)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NOT_FOUND)
                throw GBException(GBException.TOURNAMENT_NOT_FIND_MESSAGE)
            else if (e.code() == GBHttpStatusCode.valueA)
                throw GBException(GBException.TOURNAMENT_DONE_MESSAGE)
            else
                throw UnknownError("status code is ${e.code()}")
        }
    }

    override suspend fun deleteGame(gameId: Int) {
        try {
            gameApiService.deleteGame(gameId)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NOT_FOUND)
                throw GBException(GBException.GAME_NOT_FIND_MESSAGE)
            else
                throw UnknownError("status code is ${e.code()}")
        }
    }

    override suspend fun updatePlayers(gameId: Int, body: Map<String, List<Player>>) {
        try {
            gameApiService.patchGame(gameId, body)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NOT_FOUND)
                throw GBException(GBException.GAME_NOT_FIND_MESSAGE)
            else
                throw UnknownError("status code is ${e.code()}")
        }
    }

    override suspend fun updateState(gameId: Int, body: Map<String, GBState>) {
        try {
            gameApiService.patchGame(gameId, body)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NOT_FOUND)
                throw GBException(GBException.GAME_NOT_FIND_MESSAGE)
            else
                throw UnknownError("status code is ${e.code()}")

        }
    }

    override suspend fun getScoreBook(gameId: Int): ScoreBook {
        try {
            return gameApiService.getScoreBook(gameId)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NOT_FOUND)
                throw GBException(GBException.GAME_NOT_FIND_MESSAGE)
            else
                throw UnknownError("status code is ${e.code()}")
        }
    }

    override suspend fun getPlayersReady(gameId: Int) : List<String> {
        try {
            return gameApiService.getPlayersReady(gameId)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NOT_FOUND)
                throw GBException(GBException.GAME_NOT_FIND_MESSAGE)
            else
                throw UnknownError("status code is ${e.code()}")
        }
    }

    override suspend fun updatePlayersReady(gameId: Int, playersReady: List<String>?) {
        try {
            gameApiService.updatePlayersReady(gameId, playersReady)
        } catch (e: HttpException) {
            // TODO handle concurrent access
            if (e.code() == HttpURLConnection.HTTP_NOT_FOUND)
                throw GBException(GBException.GAME_NOT_FIND_MESSAGE)
            else
                throw UnknownError("status code is ${e.code()}")
        }
    }

    override suspend fun putScoreBook(putScoreBook: PutScoreBookNetworkEntity): ScoreBook {
        try {
            return gameApiService.putScoreBook(putScoreBook.gameId, putScoreBook)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NOT_FOUND)
                throw GBException(GBException.GAME_NOT_FIND_MESSAGE)
            else if (e.code() == GBHttpStatusCode.valueA)
                throw GBException(GBException.INVALID_OPERATION_MESSAGE)
            else
                throw UnknownError("status code is ${e.code()}")
        }
    }
}