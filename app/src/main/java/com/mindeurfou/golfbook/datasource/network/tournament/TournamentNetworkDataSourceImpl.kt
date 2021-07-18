package com.mindeurfou.golfbook.datasource.network.tournament

import com.mindeurfou.golfbook.data.tournament.local.Tournament
import com.mindeurfou.golfbook.data.tournament.local.TournamentDetails
import com.mindeurfou.golfbook.data.tournament.remote.PostTournamentNetworkEntity
import com.mindeurfou.golfbook.data.tournament.remote.PutTournamentNetworkEntity
import com.mindeurfou.golfbook.datasource.network.RetrofitBuilder
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class TournamentNetworkDataSourceImpl : TournamentNetworkDataSource {

    private val apiService: TournamentApiService = RetrofitBuilder.retrofit.create(TournamentApiService::class.java)

    override suspend fun getTournament(tournamentId: Int): TournamentDetails =
        apiService.getTournament(tournamentId)

    override suspend fun getTournaments(): List<Tournament> =
        apiService.getTournaments()

    override suspend fun postTournament(postTournament: PostTournamentNetworkEntity): TournamentDetails =
        apiService.postTournament(postTournament)

    override suspend fun updateTournament(putTournament: PutTournamentNetworkEntity): TournamentDetails =
        apiService.updateTournament(putTournament.id, putTournament)

}