package com.mindeurfou.golfbook.interactors.games

import android.content.SharedPreferences
import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.datasource.network.game.GameNetworkDataSourceImpl
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors.Companion.PLAYER_ID_KEY
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.ErrorMessages
import com.mindeurfou.golfbook.utils.FakeData
import com.mindeurfou.golfbook.utils.GBException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class GamesInteractors @Inject constructor(
    private val gameNetworkDataSourceImpl: GameNetworkDataSourceImpl,
    private val sharedPref: SharedPreferences
){

    fun getGames() : Flow<DataState<List<Game>>> = flow {

        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(500)
            emit(DataState.Success(FakeData.games()))
            return@flow
        }


        val playerId = sharedPref.getInt(PLAYER_ID_KEY, 0)
        if (playerId == 0) {
            emit(DataState.Failure(listOf(ErrorMessages.INTERNAL_ERROR)))
            return@flow
        }

        try {
            val games = gameNetworkDataSourceImpl.getGamesByPlayerId(playerId)
            emit(DataState.Success(games))
        } catch (e: Exception) {
            if (e is GBException) {
                when (e.message) {
                    GBException.NO_RESOURCES_MESSAGE -> emit(DataState.Failure(listOf(ErrorMessages.NO_GAMES)))
                    GBException.UNAUTHORIZED -> emit(DataState.Failure(listOf(ErrorMessages.UNAUTHORIZED)))
                    else -> emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
                }
            } else
                emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }
    }

}