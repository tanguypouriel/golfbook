package com.mindeurfou.golfbook.interactors.prepareGame

import com.mindeurfou.golfbook.interactors.gameDetails.GameDetailsEvent
import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class PrepareGameEvent : StateEvent {
    object LaunchGameEvent : PrepareGameEvent()
    object GetGameDetailsEvent : PrepareGameEvent()
}
