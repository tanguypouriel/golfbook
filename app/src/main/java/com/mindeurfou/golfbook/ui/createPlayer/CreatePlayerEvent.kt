package com.mindeurfou.golfbook.ui.createPlayer

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class CreatePlayerEvent : StateEvent{

    class SendPlayerInfoEvent(
        val name: String,
        val lastName: String,
        val username: String,
        val password: String,
        val avatarId: Int
    ) : CreatePlayerEvent()

}