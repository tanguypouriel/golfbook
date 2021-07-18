package com.mindeurfou.golfbook.datasource.network.tournament

import com.mindeurfou.golfbook.data.tournament.local.Tournament
import com.mindeurfou.golfbook.data.tournament.local.TournamentDetails
import com.mindeurfou.golfbook.data.tournament.remote.PostTournamentNetworkEntity
import com.mindeurfou.golfbook.data.tournament.remote.PutTournamentNetworkEntity

interface TournamentNetworkDataSource {
    suspend fun getTournament(tournamentId: Int): TournamentDetails
    suspend fun getTournaments(): List<Tournament>
    suspend fun postTournament(postTournament: PostTournamentNetworkEntity): TournamentDetails
    suspend fun updateTournament(putTournament: PutTournamentNetworkEntity): TournamentDetails
}