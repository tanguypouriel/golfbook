package com.mindeurfou.golfbook.datasource.network.tournament

import com.mindeurfou.golfbook.data.tournament.local.Tournament
import com.mindeurfou.golfbook.data.tournament.local.TournamentDetails
import com.mindeurfou.golfbook.data.tournament.remote.PostTournamentNetworkEntity
import com.mindeurfou.golfbook.data.tournament.remote.PutTournamentNetworkEntity
import com.mindeurfou.golfbook.datasource.network.RetrofitBuilder
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class TournamentNetworkDataSourceImpl @Inject constructor(
    private val tournamentApiService: TournamentApiService
) : TournamentNetworkDataSource {

    override suspend fun getTournament(tournamentId: Int): TournamentDetails =
        tournamentApiService.getTournament(tournamentId)

    override suspend fun getTournaments(): List<Tournament> =
        tournamentApiService.getTournaments()

    override suspend fun postTournament(postTournament: PostTournamentNetworkEntity): TournamentDetails =
        tournamentApiService.postTournament(postTournament)

    override suspend fun updateTournament(putTournament: PutTournamentNetworkEntity): TournamentDetails =
        tournamentApiService.updateTournament(putTournament.id, putTournament)

}