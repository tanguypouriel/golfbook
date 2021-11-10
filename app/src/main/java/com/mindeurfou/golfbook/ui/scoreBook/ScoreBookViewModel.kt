package com.mindeurfou.golfbook.ui.scoreBook

import androidx.lifecycle.*
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
    private val scoreBookInteractors: ScoreBookInteractors,
    state: SavedStateHandle
) : ViewModel() {

    private val _scoreBook: MutableLiveData<DataState<ScoreBook>> = MutableLiveData()
    val scoreBook: LiveData<DataState<ScoreBook>> = _scoreBook

    private val gameId: Int = state.get("gameId") ?: 1 // TODO safeargs

    fun setStateEvent(stateEvent: ScoreBookEvent) {
        when(stateEvent) {
            is ScoreBookEvent.GetScoreBookEvent -> {
                scoreBookInteractors.getScoreBook(gameId).onEach {
                    _scoreBook.value = it
                }.launchIn(viewModelScope)
            }
        }
    }

}
