package com.mindeurfou.golfbook.interactors.gameDetails

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class GameDetailsEvent : StateEvent {
    object GetGameDetailsEvent : GameDetailsEvent()
}