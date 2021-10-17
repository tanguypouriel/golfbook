package com.mindeurfou.golfbook.interactors.modifyPlayer

import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.datasource.network.player.PlayerNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ModifyPlayerInteractors
@Inject constructor(
    private val playerNetworkDataSourceImpl: PlayerNetworkDataSourceImpl
) {

    fun sendModification(player: Player): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        kotlinx.coroutines.delay(1000)
        emit(DataState.Success(true))
    }
}