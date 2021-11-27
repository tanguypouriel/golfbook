package com.mindeurfou.golfbook.interactors.scoreInput

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class ScoreInputEvent : StateEvent {
    class GetGameDetailsEvent(val gameId: Int) : ScoreInputEvent()
    class PutScoreEvent(val gameId: Int, val score: Int) : ScoreInputEvent()
}
