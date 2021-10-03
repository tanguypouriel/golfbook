package com.mindeurfou.golfbook.datasource.network.tournament

import com.mindeurfou.golfbook.data.tournament.local.Tournament
import com.mindeurfou.golfbook.data.tournament.local.TournamentDetails
import com.mindeurfou.golfbook.data.tournament.remote.PostTournamentNetworkEntity
import com.mindeurfou.golfbook.data.tournament.remote.PutTournamentNetworkEntity
import com.mindeurfou.golfbook.utils.GBException
import com.mindeurfou.golfbook.utils.GBHttpStatusCode
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

@ExperimentalSerializationApi
class TournamentNetworkDataSourceImpl @Inject constructor(
    private val tournamentApiService: TournamentApiService
) : TournamentNetworkDataSource {

    override suspend fun getTournament(tournamentId: Int): TournamentDetails {
        try {
            return tournamentApiService.getTournament(tournamentId)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NOT_FOUND)
                throw GBException(GBException.TOURNAMENT_NOT_FIND_MESSAGE)
            else
                throw Exception("status code is ${e.code()}")
        }
    }

    override suspend fun getTournaments(limit: Int?, offset: Int?): List<Tournament> {
        try {
            return tournamentApiService.getTournaments(limit, offset)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NO_CONTENT)
                throw GBException(GBException.NO_RESOURCES_MESSAGE)
            else
                throw Exception("status code is ${e.code()}")
        }
    }

    override suspend fun postTournament(postTournament: PostTournamentNetworkEntity): TournamentDetails =
        tournamentApiService.postTournament(postTournament)

    override suspend fun updateTournament(putTournament: PutTournamentNetworkEntity): TournamentDetails {
        try {
            return tournamentApiService.updateTournament(putTournament.id, putTournament)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NOT_FOUND)
                throw GBException(GBException.TOURNAMENT_NOT_FIND_MESSAGE)
            else if (e.code() == GBHttpStatusCode.valueA)
                throw GBException(GBException.INVALID_OPERATION_MESSAGE)
            else
                throw Exception("status code is ${e.code()}")
        }
    }

}