package com.mindeurfou.golfbook.interactors.tournaments

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.tournament.local.Tournament
import com.mindeurfou.golfbook.datasource.network.tournament.TournamentNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.ErrorMessages
import com.mindeurfou.golfbook.utils.FakeData
import com.mindeurfou.golfbook.utils.GBException
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

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(500)
            emit(DataState.Success(FakeData.tournaments()))
            return@flow
        }

        try {
            val tournaments = tournamentNetworkDataSource.getTournaments()
            emit(DataState.Success(tournaments))
        } catch (e: Exception) {
            if (e is GBException && e.message == GBException.NO_RESOURCES_MESSAGE)
                emit(DataState.Failure(listOf(ErrorMessages.EMPTY_RESSOURCE)))
            else
                emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }
    }
}