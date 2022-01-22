package com.mindeurfou.golfbook.interactors.modifyPlayer

import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class ModifyPlayerEvent : StateEvent {

    class SendModificationEvent(
        val id: Int,
        val name: String,
        val lastName : String,
        val username : String,
        val avatarId: Int,
        val realUser : Boolean
        ) : ModifyPlayerEvent()
}
