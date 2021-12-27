package com.mindeurfou.golfbook.interactors.connection

import android.content.SharedPreferences
import android.util.Log
import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.datasource.network.player.PlayerNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.ErrorMessages
import com.mindeurfou.golfbook.utils.GBException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConnectionInteractors
@Inject constructor(
    private val playerNetworkDataSourceImpl: PlayerNetworkDataSourceImpl,
    private val sharedPreferences: SharedPreferences
) {

    fun connect(username: String, password: String, rememberMe: Boolean) : Flow<DataState<Unit>> = flow {

        emit(DataState.Loading)

        // manage credentials cache
        if (rememberMe) {
            with(sharedPreferences.edit()) {
                putString(USERNAME_KEY, username)
                putString(PASSWORD_KEY, password)
                apply()
            }
        } else {
            if (sharedPreferences.getString(USERNAME_KEY, null) != null)
                sharedPreferences.edit().remove(USERNAME_KEY).apply()
            if (sharedPreferences.getString(PASSWORD_KEY, null) != null)
                sharedPreferences.edit().remove(PASSWORD_KEY).apply()
        }

        if (BuildConfig.fakeData) {
            delay(1000)
            emit(DataState.Success(Unit))
            return@flow
        }

        try {
            val token = playerNetworkDataSourceImpl.login(username, password)
            saveToken(token["token"])

            if (sharedPreferences.getInt(PLAYER_ID_KEY, 0) == 0)
                sharedPreferences.edit().putInt(PLAYER_ID_KEY, token["playerId"]!!.toInt()).apply()

            emit(DataState.Success(Unit))
        } catch (e: Exception){

            if (e is GBException) {
                if (e.message == GBException.BAD_CREDENTIALS)
                    emit(DataState.Failure(listOf(ErrorMessages.BAD_CREDENTIALS)))
            }
            else
                emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }
    }

    fun retrieveCredentials() : Flow<DataState<Pair<String, String>?>> = flow {
        emit(DataState.Loading)

        val username = sharedPreferences.getString(USERNAME_KEY, null)
        val password = sharedPreferences.getString(PASSWORD_KEY, null)

        if (username == null || password == null)
            emit(DataState.Success(null))
        else
            emit(DataState.Success(Pair(username, password)))
    }

    private fun saveToken(token: String?) {
        val validity = System.currentTimeMillis() + 86400000L
        with(sharedPreferences.edit()) {
            putString(TOKEN_KEY, token)
            putLong(VALIDITY_KEY, validity)
            apply()
        }
    }

    companion object {
        const val USERNAME_KEY = "username_key"
        const val PASSWORD_KEY = "password_key"
        const val PLAYER_ID_KEY = "player_id_key"
        const val TOKEN_KEY = "token_key"
        const val VALIDITY_KEY = "validity_key"
        const val REMEMBER_ME_KEY = "rmbr_key"
    }
}