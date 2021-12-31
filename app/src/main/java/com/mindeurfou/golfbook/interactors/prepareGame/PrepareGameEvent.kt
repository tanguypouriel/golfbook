package com.mindeurfou.golfbook.interactors.prepareGame

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class PrepareGameEvent : StateEvent {
    class GetCourseEvent(val courseId: Int) : PrepareGameEvent()
    object CheckPlayerReady : PrepareGameEvent()
    object GetGameDetailsEvent : PrepareGameEvent()
    object AcceptGameStart : PrepareGameEvent()
    object RejectGameStart : PrepareGameEvent()
}
