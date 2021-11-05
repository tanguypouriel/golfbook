package com.mindeurfou.golfbook.interactors.onboardGame

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class OnBoardGameEvent : StateEvent {
    object CheckPendingGameEvent : OnBoardGameEvent()
}