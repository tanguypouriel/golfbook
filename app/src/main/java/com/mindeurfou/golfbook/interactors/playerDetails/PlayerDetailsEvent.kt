package com.mindeurfou.golfbook.interactors.playerDetails

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class PlayerDetailsEvent : StateEvent {

    object GetPlayerEvent: PlayerDetailsEvent()

    object CheckIfIsSelf: PlayerDetailsEvent()
}
