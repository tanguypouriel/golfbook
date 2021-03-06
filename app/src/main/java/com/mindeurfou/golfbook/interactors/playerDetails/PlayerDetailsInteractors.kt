package com.mindeurfou.golfbook.interactors.playerDetails

import android.content.SharedPreferences
import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.datasource.network.player.PlayerNetworkDataSourceImpl
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors.Companion.PLAYER_ID_KEY
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.ErrorMessages
import com.mindeurfou.golfbook.utils.FakeData
import com.mindeurfou.golfbook.utils.GBException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayerDetailsInteractors
@Inject constructor(
    private val playerNetworkDataSourceImpl: PlayerNetworkDataSourceImpl,
    private val sharedPreferences: SharedPreferences
){

    fun getPlayer(playerId: Int): Flow<DataState<Player>> = flow {
        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(2000)
            emit(DataState.Success(FakeData.player()))
            return@flow
        }

        try {
            val player = playerNetworkDataSourceImpl.getPlayer(playerId)
            emit(DataState.Success(player))
        } catch (e: Exception) {
            emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }
    }

    fun checkIfIsSelf(playerId: Int): Flow<DataState<Boolean>> = flow {
        val selfId = sharedPreferences.getInt(PLAYER_ID_KEY, 0)
        if (selfId == 0) {
            emit(DataState.Failure(listOf(ErrorMessages.INTERNAL_ERROR)))
            return@flow
        }

        emit(DataState.Success(selfId == playerId))
    }
}