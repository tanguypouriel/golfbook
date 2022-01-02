package com.mindeurfou.golfbook.interactors.onboardGame

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.datasource.network.game.GameNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.ErrorMessages
import com.mindeurfou.golfbook.utils.FakeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class OnBoardGameInteractors @Inject constructor(
    private val gameNetworkDataSourceImpl: GameNetworkDataSourceImpl
){

    fun getInitGames(): Flow<DataState<List<Game>?>> = flow {

        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Success(FakeData.pendingGames()))
            return@flow
        }

        try {
            val games = gameNetworkDataSourceImpl.getInitGames()
            emit(DataState.Success(games))
        } catch (e: Exception) {
            emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }

    }

    fun joinGame(gameId: Int, players: List<Player>): Flow<DataState<Int>> = flow {
        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Failure(listOf(ErrorMessages.GAME_FULL)))
            return@flow
        }

        if (players.size == 4) {
            emit(DataState.Failure(listOf(ErrorMessages.GAME_FULL)))
            return@flow
        }

        val player = Player(1, "Tanguy", "Pouriel", "Tanguy P", 1) // TODO faire une database locale pour le player
        val updatedPlayers = players.toMutableList()
        updatedPlayers.add(player)
        val body = mapOf("players" to updatedPlayers)

        try {
            gameNetworkDataSourceImpl.updatePlayers(gameId, body)
            emit(DataState.Success(gameId))
        } catch (e: Exception) {
            emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }

    }
}