package com.mindeurfou.golfbook.ui.scoreInput

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.interactors.scoreInput.ScoreInputEvent
import com.mindeurfou.golfbook.interactors.scoreInput.ScoreInputInteractors
import com.mindeurfou.golfbook.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class ScoreInputViewModel
@Inject constructor(
    private val scoreInputInteractors: ScoreInputInteractors
) : ViewModel() {

    private val _gameDetails: MutableLiveData<DataState<GameDetails>> = MutableLiveData()
    val gameDetails: LiveData<DataState<GameDetails>> = _gameDetails

    fun setStateEvent(stateEvent: ScoreInputEvent) {
        when(stateEvent) {
            is ScoreInputEvent.GetGameDetailsEvent -> {
                scoreInputInteractors.getGameDetails(stateEvent.gameId).onEach {
                    _gameDetails.value = it
                }.launchIn(viewModelScope)
            }
            is ScoreInputEvent.PutScoreEvent -> {
                scoreInputInteractors.putScore(stateEvent.gameId, stateEvent.score).onEach {
                    _gameDetails.value = it
                }.launchIn(viewModelScope)
            }
        }
    }

}
