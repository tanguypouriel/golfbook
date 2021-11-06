package com.mindeurfou.golfbook.interactors.prepareGame

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class PrepareGameEvent : StateEvent {
    object LaunchGameEvent : PrepareGameEvent()
}
