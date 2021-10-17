package com.mindeurfou.golfbook.interactors.modifyPlayer

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.datasource.network.player.PlayerNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject


class ModifyPlayerInteractors
@Inject constructor(
    private val playerNetworkDataSourceImpl: PlayerNetworkDataSourceImpl
) {

    fun sendModification(player: Player): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Success(true))
            return@flow
        }

        try {
            val returnedPlayer = playerNetworkDataSourceImpl.updatePlayer(player)
            emit(DataState.Success(returnedPlayer == player))
        } catch (e: Exception) {
            emit(DataState.Failure(e))
        }
    }
}