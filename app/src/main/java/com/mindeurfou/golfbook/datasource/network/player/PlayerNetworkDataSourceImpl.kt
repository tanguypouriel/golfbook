package com.mindeurfou.golfbook.datasource.network.player

import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.data.player.remote.PostPlayerNetworkEntity
import com.mindeurfou.golfbook.data.player.remote.toPutPlayerNetworkEntity
import com.mindeurfou.golfbook.datasource.network.RetrofitBuilder
import javax.inject.Inject

class PlayerNetworkDataSourceImpl @Inject constructor(
    private val playerApiService: PlayerApiService
): PlayerNetworkDataSource {

    override suspend fun getPlayer(playerId: Int): Player =
        playerApiService.getPlayer(playerId)

    override suspend fun getPlayers(): List<Player> =
        playerApiService.getPlayers()

    override suspend fun postPlayer(postPlayer: PostPlayerNetworkEntity): Player =
        playerApiService.postPlayer(postPlayer)

    override suspend fun updatePlayer(player: Player): Player =
        playerApiService.updatePlayer(player.id, player.toPutPlayerNetworkEntity())

    override suspend fun deletePlayer(playerId: Int): Boolean =
        playerApiService.deletePlayer(playerId)

}