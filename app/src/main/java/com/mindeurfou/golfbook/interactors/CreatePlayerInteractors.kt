package com.mindeurfou.golfbook.interactors

import com.mindeurfou.golfbook.data.player.remote.PostPlayerNetworkEntity
import com.mindeurfou.golfbook.datasource.network.player.PlayerNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreatePlayerInteractors
@Inject constructor(
   private val playerNetworkDataSourceImpl: PlayerNetworkDataSourceImpl
) {

   fun sendPlayerInfo(postPlayer: PostPlayerNetworkEntity) : Flow<DataState<Int>> = flow {

      emit(DataState.Loading)

      try {
         val playerId = playerNetworkDataSourceImpl.postPlayer(postPlayer).id
         emit(DataState.Success(playerId)) // TODO Save playerId in cache
      } catch (e: Exception) {
         emit(DataState.Failure(e))
      }
   }
}