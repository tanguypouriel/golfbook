package com.mindeurfou.golfbook.ui.gameDetails

import androidx.lifecycle.*
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.interactors.gameDetails.GameDetailsEvent
import com.mindeurfou.golfbook.interactors.gameDetails.GameDetailsInteractors
import com.mindeurfou.golfbook.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class GameDetailsViewModel
@Inject constructor(
    private val gameDetailsInteractors: GameDetailsInteractors,
    state: SavedStateHandle
) : ViewModel() {

    private val _gameDetails: MutableLiveData<DataState<GameDetails>> = MutableLiveData()
    val gameDetails: LiveData<DataState<GameDetails>> = _gameDetails

    private val gameId: Int = state.get("gameId")!!

    fun setStateEvent(stateEvent : GameDetailsEvent) {
        when(stateEvent) {
            is GameDetailsEvent.GetGameDetailsEvent -> {
                gameDetailsInteractors.getGameDetails(gameId).onEach {
                    _gameDetails.value = it
                }.launchIn(viewModelScope)
            }
        }
    }

}
