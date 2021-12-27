package com.mindeurfou.golfbook.interactors.createPlayer

import android.content.SharedPreferences
import com.mindeurfou.golfbook.data.player.remote.PostPlayerNetworkEntity
import com.mindeurfou.golfbook.datasource.network.player.PlayerNetworkDataSourceImpl
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors.Companion.PLAYER_ID_KEY
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors.Companion.TOKEN_KEY
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors.Companion.VALIDITY_KEY
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.ErrorMessages
import com.mindeurfou.golfbook.utils.GBException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreatePlayerInteractors
@Inject constructor(
   private val playerNetworkDataSourceImpl: PlayerNetworkDataSourceImpl,
   private val sharedPreferences: SharedPreferences
) {

   fun sendPlayerInfo(postPlayer: PostPlayerNetworkEntity) : Flow<DataState<Boolean>> = flow {

      emit(DataState.Loading)

      try {
         val tokenMap = playerNetworkDataSourceImpl.postPlayer(postPlayer)
         val validity = System.currentTimeMillis() + 86400000L
          
         with(sharedPreferences.edit()) {
            putString(TOKEN_KEY, tokenMap["token"])
            putLong(VALIDITY_KEY, validity)
            putInt(PLAYER_ID_KEY, tokenMap["playerId"]!!.toInt())
            apply()
         }
            emit(DataState.Success(true))
      } catch (e: Exception) {
         if (e is GBException) {
            if (e.message == GBException.USERNAME_ALREADY_TAKEN_MESSAGE)
                emit(DataState.Failure(listOf(ErrorMessages.USERNAME_TAKEN)))
         } else
            emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
      }
   }
}