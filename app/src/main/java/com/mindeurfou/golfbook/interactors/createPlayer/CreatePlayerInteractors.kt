package com.mindeurfou.golfbook.interactors.createPlayer

import android.content.SharedPreferences
import com.mindeurfou.golfbook.data.player.PlayerDbEntity
import com.mindeurfou.golfbook.data.player.remote.PostPlayerNetworkEntity
import com.mindeurfou.golfbook.datasource.database.PlayerDao
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
   private val playerDao: PlayerDao,
   private val sharedPreferences: SharedPreferences
) {

   fun sendPlayerInfo(
      name: String,
      lastName: String,
      username: String,
      password: String,
      avatarId: Int
   ) : Flow<DataState<Unit>> = flow {

      emit(DataState.Loading)

      val errors: MutableList<ErrorMessages> = mutableListOf()

      if (name.isBlank())
         errors.add(ErrorMessages.NAME_EMPTY)
      if (lastName.isBlank())
         errors.add(ErrorMessages.LASTNAME_EMPTY)
      if (username.isBlank())
         errors.add(ErrorMessages.USERNAME_EMPTY)
      if (password.isBlank())
         errors.add(ErrorMessages.PASSWORD_EMPTY)

      if (errors.size != 0) {
         emit(DataState.Failure(errors))
         return@flow
      }

      val postPlayer = PostPlayerNetworkEntity(
         name = name,
         lastName = lastName,
         username = username,
         password = password,
         avatarId = avatarId,
         realUser = true
      )

      try {
         val tokenMap = playerNetworkDataSourceImpl.postPlayer(postPlayer)
         val validity = System.currentTimeMillis() + 86400000L
         val playerId = tokenMap["playerId"]!!.toInt()

         with(sharedPreferences.edit()) {
            putString(TOKEN_KEY, tokenMap["token"])
            putLong(VALIDITY_KEY, validity)
            putInt(PLAYER_ID_KEY, playerId)
            apply()
         }

         playerDao.insertPlayer(
            PlayerDbEntity(
               playerId = playerId,
               name = name,
               lastName = lastName,
               username = username,
               avatarId = avatarId,
            )
         )

         emit(DataState.Success(Unit))

      } catch (e: Exception) {
         if (e is GBException) {
            if (e.message == GBException.USERNAME_ALREADY_TAKEN_MESSAGE)
                emit(DataState.Failure(listOf(ErrorMessages.USERNAME_TAKEN)))
         } else
            emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
      }
   }
}