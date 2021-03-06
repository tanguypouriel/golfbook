package com.mindeurfou.golfbook.interactors.connection

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class ConnectionEvent : StateEvent {

    class ConnectEvent(
        val username: String,
        val password: String,
        val rememberMe: Boolean
    ) : ConnectionEvent()

    object RetrieveCredentialsEvent : ConnectionEvent()

}