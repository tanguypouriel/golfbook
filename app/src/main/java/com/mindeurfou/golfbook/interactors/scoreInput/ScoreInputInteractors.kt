package com.mindeurfou.golfbook.interactors.scoreInput

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.datasource.network.game.GameNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.FakeData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class ScoreInputInteractors @Inject constructor(
    private val gameNetworkDataSourceImpl: GameNetworkDataSourceImpl
) {

    fun getGameDetails(gameId: Int): Flow<DataState<GameDetails>> = flow {

        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            delay(1000)
            emit(DataState.Success(FakeData.gameDetails()))
            return@flow
        }

        // TODO
    }

    fun putScore(gameId: Int, score: Int) : Flow<DataState<GameDetails>> = flow {
        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            delay(1000)
            emit(DataState.Success(FakeData.gameDetails()))
            return@flow
        }

        // TODO
    }
}