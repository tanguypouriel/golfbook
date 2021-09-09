package com.mindeurfou.golfbook.interactors.connection

import com.mindeurfou.golfbook.utils.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConnectionInteractors
@Inject constructor() {

    fun connect(username: String, password: String) : Flow<DataState<Boolean>> = flow {

        emit(DataState.Loading)

        delay(3000)

        emit(DataState.Success(true))

    }
}