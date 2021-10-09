package com.mindeurfou.golfbook.interactors.tournaments

import com.mindeurfou.golfbook.data.tournament.local.Tournament
import com.mindeurfou.golfbook.datasource.network.tournament.TournamentNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class TournamentsInteractors
@Inject constructor(
    private val tournamentNetworkDataSource: TournamentNetworkDataSourceImpl
) {

    fun getTournaments() : Flow<DataState<List<Tournament>>> = flow {

        emit(DataState.Loading)

        try {
            val tournaments = tournamentNetworkDataSource.getTournaments()
            emit(DataState.Success(tournaments))
        } catch (e: Exception) {
            emit(DataState.Failure(e))
        }
    }
}