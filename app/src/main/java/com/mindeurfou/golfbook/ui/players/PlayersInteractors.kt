package com.mindeurfou.golfbook.ui.players

import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.datasource.network.player.PlayerNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayersInteractors
@Inject constructor(
    private val playerNetworkDataSourceImpl: PlayerNetworkDataSourceImpl
) {

    fun getPlayers() : Flow<DataState<List<Player>>> = flow {

        emit(DataState.Loading)

        try {
            val players = playerNetworkDataSourceImpl.getPlayers()
            emit(DataState.Success(players))
        } catch (e: Exception) {
            emit(DataState.Failure(e))
        }
    }
}
