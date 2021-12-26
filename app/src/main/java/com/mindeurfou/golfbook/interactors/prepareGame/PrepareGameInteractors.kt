package com.mindeurfou.golfbook.interactors.prepareGame

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.datasource.network.game.GameNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.FakeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class PrepareGameInteractors
@Inject constructor(
    private val gameNetworkDataSourceImpl: GameNetworkDataSourceImpl
){

    fun tryStartingGame(): Flow<DataState<GameDetails>> = flow {

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(500)
            emit(DataState.Success(FakeData.gameDetails(state = GBState.STARTING, playersReady = listOf("MindeurFou"))))
            kotlinx.coroutines.delay(2000)
            emit(DataState.Success(FakeData.gameDetails(state = GBState.STARTING, playersReady = listOf("Roro", "MindeurFou"))))
            kotlinx.coroutines.delay(2000)
            emit(DataState.Success(FakeData.gameDetails(state = GBState.STARTING, playersReady = listOf("Roro", "MindeurFou", "Bobby"))))
            kotlinx.coroutines.delay(2000)
            emit(DataState.Success(FakeData.gameDetails(state = GBState.PENDING, playersReady = listOf("Roro", "MindeurFou", "Bobby"))))
            return@flow
        }

        // TODO
    }

    fun getGameDetails(gameId: Int) : Flow<DataState<GameDetails>> = flow {

        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Success(FakeData.gameDetails(state = GBState.INIT)))
            return@flow
        }

    }
}