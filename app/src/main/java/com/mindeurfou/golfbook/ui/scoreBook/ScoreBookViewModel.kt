package com.mindeurfou.golfbook.ui.scoreBook

import androidx.lifecycle.*
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.data.game.local.ScoreBook
import com.mindeurfou.golfbook.interactors.scoreBook.ScoreBookEvent
import com.mindeurfou.golfbook.interactors.scoreBook.ScoreBookInteractors
import com.mindeurfou.golfbook.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class ScoreBookViewModel
@Inject constructor(
    private val scoreBookInteractors: ScoreBookInteractors
) : ViewModel() {

    private val _gameDetails: MutableLiveData<DataState<GameDetails>> = MutableLiveData()
    val gameDetails: LiveData<DataState<GameDetails>> = _gameDetails


    fun setStateEvent(stateEvent: ScoreBookEvent) {
        when(stateEvent) {
            is ScoreBookEvent.GetGameDetailsEvent -> {
                scoreBookInteractors.getGameDetails(stateEvent.gameId).onEach {
                    _gameDetails.value = it
                }.launchIn(viewModelScope)
            }
        }
    }

}
