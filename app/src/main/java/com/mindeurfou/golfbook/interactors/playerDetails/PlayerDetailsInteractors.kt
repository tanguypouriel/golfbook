package com.mindeurfou.golfbook.interactors.playerDetails

import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.datasource.network.player.PlayerNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayerDetailsInteractors
@Inject constructor(
    private val playerNetworkDataSourceImpl: PlayerNetworkDataSourceImpl
){

    fun getPlayer(playerId: Int): Flow<DataState<Player>> = flow {
        emit(DataState.Loading)

        try {
            val player = playerNetworkDataSourceImpl.getPlayer(playerId)
            emit(DataState.Success(player))
        } catch (e: Exception) {
            emit(DataState.Failure(e))
        }
    }
}