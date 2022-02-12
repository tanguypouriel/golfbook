package com.mindeurfou.golfbook.interactors.onboardGame

import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class OnBoardGameEvent : StateEvent {
    object CheckInitGameEvent : OnBoardGameEvent()
    class JoinGameEvent(val gameId: Int, val players: List<Player>): OnBoardGameEvent()
}