package com.mindeurfou.golfbook.interactors.createGame

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class CreateGameEvent() : StateEvent {
    class SendGameEvent(val name: String, val courseName: String) : CreateGameEvent()
    object GetCoursesNames : CreateGameEvent()
}
