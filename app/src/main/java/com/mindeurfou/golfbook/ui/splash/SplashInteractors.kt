package com.mindeurfou.golfbook.ui.splash

import android.content.SharedPreferences
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors.Companion.TOKEN_KEY
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors.Companion.VALIDITY_KEY
import com.mindeurfou.golfbook.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SplashInteractors
@Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun checkToken() : Flow<DataState<Boolean>> = flow {

        if (sharedPreferences.getString(TOKEN_KEY, null) != null) {
            val validity = sharedPreferences.getLong(VALIDITY_KEY, 0)
            if (validity > System.currentTimeMillis())
                emit(DataState.Success(true))
            else {
                sharedPreferences.edit().remove(TOKEN_KEY).remove(VALIDITY_KEY).apply()
                emit(DataState.Success(false))
            }
        } else
            emit(DataState.Success(false))
    }
}