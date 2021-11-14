package com.mindeurfou.golfbook.interactors.scoreBook

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class ScoreBookEvent : StateEvent {
    class GetGameDetailsEvent(val gameId: Int) : ScoreBookEvent()
}