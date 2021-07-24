package com.mindeurfou.golfbook.datasource.network.tournament

import com.mindeurfou.golfbook.data.tournament.local.Tournament
import com.mindeurfou.golfbook.data.tournament.local.TournamentDetails
import com.mindeurfou.golfbook.data.tournament.remote.PostTournamentNetworkEntity
import com.mindeurfou.golfbook.data.tournament.remote.PutTournamentNetworkEntity
import retrofit2.http.*

interface TournamentApiService {

    @GET("/tournament/{tournamentId}")
    suspend fun getTournament(@Path("tournamentId}") tournamentId: Int): TournamentDetails

    @GET("/tournament")
    suspend fun getTournaments(@Query("limit") limit: Int? = null, @Query("offset") offset: Int? = null): List<Tournament>

    @POST("/tournament")
    suspend fun postTournament(@Body postTournament: PostTournamentNetworkEntity): TournamentDetails

    @PUT("/tournament/{tournamentId}")
    suspend fun updateTournament(@Path("tournamentId") tournamentId: Int, @Body putTournament: PutTournamentNetworkEntity): TournamentDetails

}