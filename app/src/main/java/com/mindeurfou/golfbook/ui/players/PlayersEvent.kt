package com.mindeurfou.golfbook.ui.players

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class PlayersEvent : StateEvent {

    class GetPlayersEvent() : StateEvent

}
