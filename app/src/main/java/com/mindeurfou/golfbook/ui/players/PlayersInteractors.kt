package com.mindeurfou.golfbook.ui.players

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.data.player.remote.GetPlayersResponse
import com.mindeurfou.golfbook.datasource.network.player.PlayerNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.FakeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayersInteractors
@Inject constructor(
    private val playerNetworkDataSourceImpl: PlayerNetworkDataSourceImpl
) {

    fun getPlayers() : Flow<DataState<GetPlayersResponse>> = flow {

        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(500)
            emit(DataState.Success(FakeData.getPlayersResponse()))
            return@flow
        }

        try {
            val getPlayersResponse = playerNetworkDataSourceImpl.getPlayers()
            emit(DataState.Success(getPlayersResponse))
        } catch (e: Exception) {
            emit(DataState.Failure(e))
        }
    }
}
