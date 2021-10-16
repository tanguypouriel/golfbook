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

        kotlinx.coroutines.delay(1000)

        val players = listOf(
            Player(1, "Lisa le boss", "De la Fleche", "Petrouchka", 1),
            Player(2, "Lisa le boss", "De la Fleche", "Petrouchka", 1),
            Player(3, "Lisa le boss", "De la Fleche", "Petrouchka", 1),
            Player(4, "Lisa le boss", "De la Fleche", "Petrouchka", 1),
            Player(5, "Lisa le boss", "De la Fleche", "Petrouchka", 1),
            Player(6, "Lisa le boss", "De la Fleche", "Petrouchka", 1),
            Player(7, "Lisa le boss", "De la Fleche", "Petrouchka", 1),
        )

        emit(DataState.Success(players))
    }
}
