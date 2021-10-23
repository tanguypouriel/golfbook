package com.mindeurfou.golfbook.interactors.games

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class GamesEvent : StateEvent {
    object GetGamesEvent : GamesEvent()
}