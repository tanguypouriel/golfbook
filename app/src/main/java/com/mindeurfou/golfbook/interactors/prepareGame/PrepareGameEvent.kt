package com.mindeurfou.golfbook.interactors.prepareGame

import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class PrepareGameEvent : StateEvent {
    class GetCourseEvent(val courseName: String) : PrepareGameEvent()
    object CheckPlayerReadyEvent : PrepareGameEvent()
    object TryStartGameEvent : PrepareGameEvent()
    object GetGameDetailsEvent : PrepareGameEvent()
    object AcceptGameStartEvent : PrepareGameEvent()
    object RejectGameStartEvent : PrepareGameEvent()
    class CreateAndAddPlayerEvent(
        val name: String,
        val lastName: String,
        val username: String,
        val avatarId: Int
        ) : PrepareGameEvent()
    class AddPlayerEvent(val player: Player?) : PrepareGameEvent()
    object LeaveGameEvent : PrepareGameEvent()
    object GetPlayersEvent : PrepareGameEvent()
}
