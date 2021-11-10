package com.mindeurfou.golfbook.interactors.gameDetails

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.datasource.network.game.GameNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.FakeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class GameDetailsInteractors @Inject constructor(
    private val gameNetworkDataSourceImpl: GameNetworkDataSourceImpl
){

    fun getGameDetails(gameId: Int) : Flow<DataState<GameDetails>> = flow {

        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Success(FakeData.gameDetails()))
            return@flow
        }


    }
}