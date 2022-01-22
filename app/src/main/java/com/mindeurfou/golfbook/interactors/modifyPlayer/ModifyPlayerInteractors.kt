package com.mindeurfou.golfbook.interactors.modifyPlayer

import android.content.SharedPreferences
import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.player.PlayerDbEntity
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.datasource.database.PlayerDao
import com.mindeurfou.golfbook.datasource.network.player.PlayerNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.ErrorMessages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject


class ModifyPlayerInteractors
@Inject constructor(
    private val playerNetworkDataSourceImpl: PlayerNetworkDataSourceImpl,
    private val playerDao: PlayerDao,
) {

    fun sendModification(
        id: Int,
        name: String,
        lastName: String,
        username: String,
        drawableResourceId: Int,
        realUser: Boolean
    ): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Success(Unit))
            return@flow
        }

        val errors: MutableList<ErrorMessages> = mutableListOf()

        if (name.isBlank())
            errors.add(ErrorMessages.NAME_EMPTY)
        if (lastName.isBlank())
            errors.add(ErrorMessages.LASTNAME_EMPTY)
        if (username.isBlank())
            errors.add(ErrorMessages.USERNAME_EMPTY)

        if (errors.size != 0) {
            emit(DataState.Failure(errors))
            return@flow
        }

        val player = Player(id, name, lastName, username, drawableResourceId, realUser)

        try {
            val returnedPlayer = playerNetworkDataSourceImpl.updatePlayer(player)
            if (returnedPlayer == player) {
                playerDao.insertPlayer(player.toDBEntity())
                emit(DataState.Success(Unit))
            } else
                emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        } catch (e: Exception) {
            emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }
    }
}