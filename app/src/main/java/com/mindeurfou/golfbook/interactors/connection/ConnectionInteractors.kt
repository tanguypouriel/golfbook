package com.mindeurfou.golfbook.interactors.connection

import android.content.SharedPreferences
import android.util.Log
import com.mindeurfou.golfbook.datasource.network.player.PlayerNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConnectionInteractors
@Inject constructor(
    private val playerNetworkDataSourceImpl: PlayerNetworkDataSourceImpl,
    private val sharedPreferences: SharedPreferences
) {

    val USERNAME_KEY = "username_key"
    val PASSWORD_KEY = "password_key"

    fun connect(username: String, password: String, rememberMe: Boolean) : Flow<DataState<Boolean>> = flow {

        emit(DataState.Loading)

        delay(1000)

        val token = playerNetworkDataSourceImpl.login(username, password) // ready ? TODO ?
        Log.d("httpLogin", "token : $token")


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

        emit(DataState.Success(true))

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
}