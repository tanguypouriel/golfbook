package com.mindeurfou.golfbook.datasource.network.player

import com.mindeurfou.golfbook.data.Credentials
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.data.player.remote.GetPlayersResponse
import com.mindeurfou.golfbook.data.player.remote.PostPlayerNetworkEntity
import com.mindeurfou.golfbook.data.player.remote.toPutPlayerNetworkEntity
import com.mindeurfou.golfbook.utils.GBException
import com.mindeurfou.golfbook.utils.GBHttpStatusCode
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

class PlayerNetworkDataSourceImpl @Inject constructor(
    private val playerApiService: PlayerApiService
): PlayerNetworkDataSource {

    override suspend fun login(username: String, password: String): Map<String, String> {
        try {
            return playerApiService.login(Credentials(username, password))
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_UNAUTHORIZED) // bad credentials
               throw GBException(GBException.BAD_CREDENTIALS)

            throw Exception("HttpException")
        }
    }

    override suspend fun getPlayer(playerId: Int): Player {
        try {
            return playerApiService.getPlayer(playerId)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NOT_FOUND)
                throw GBException(GBException.PLAYER_NOT_FIND_MESSAGE)
            else
                throw Exception("status code is ${e.code()}")
        }

    }

    override suspend fun getPlayers(limit: Int?, offset: Int?): GetPlayersResponse {
        try {
            return playerApiService.getPlayers(limit, offset)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NO_CONTENT)
                throw GBException(GBException.NO_RESOURCES_MESSAGE)
            else
                throw Exception("status code is ${e.code()}")
        }
    }

    override suspend fun postPlayer(postPlayer: PostPlayerNetworkEntity): Map<String, String> {
        try {
            return playerApiService.postPlayer(postPlayer)
        } catch (e: HttpException) {
            if (e.code() == GBHttpStatusCode.valueA)
                throw GBException(GBException.USERNAME_ALREADY_TAKEN_MESSAGE)
            else
                throw Exception("status code is ${e.code()}")
        }
    }

    override suspend fun updatePlayer(player: Player): Player {
        try {
            return playerApiService.updatePlayer(player.id, player.toPutPlayerNetworkEntity())
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NOT_FOUND)
                throw GBException(GBException.PLAYER_NOT_FIND_MESSAGE)
            else
                throw Exception("status code is ${e.code()}")
        }
    }

    override suspend fun deletePlayer(playerId: Int): Boolean =
        playerApiService.deletePlayer(playerId)

}