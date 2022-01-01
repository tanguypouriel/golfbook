package com.mindeurfou.golfbook.interactors.prepareGame

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class PrepareGameEvent : StateEvent {
    class GetCourseEvent(val courseName: String) : PrepareGameEvent()
    object CheckPlayerReady : PrepareGameEvent()
    object GetGameDetailsEvent : PrepareGameEvent()
    object AcceptGameStart : PrepareGameEvent()
    object RejectGameStart : PrepareGameEvent()
    class AddPlayer(
        val name: String,
        val lastName: String,
        val username: String,
        val avatarId: Int
        ) : PrepareGameEvent()
}
