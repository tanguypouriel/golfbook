package com.mindeurfou.golfbook.ui.prepareGame

import android.content.SharedPreferences
import androidx.lifecycle.*
import com.mindeurfou.golfbook.data.game.local.GameDetails
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors.Companion.USERNAME_KEY
import com.mindeurfou.golfbook.interactors.prepareGame.PrepareGameEvent
import com.mindeurfou.golfbook.interactors.prepareGame.PrepareGameInteractors
import com.mindeurfou.golfbook.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject


@ExperimentalSerializationApi
@HiltViewModel
class PrepareGameViewModel
@Inject constructor(
    private val prepareGameInteractors: PrepareGameInteractors,
    sharedPreferences: SharedPreferences,
    state: SavedStateHandle
) : ViewModel() {

    private val _gameDetails: MutableLiveData<DataState<GameDetails>> = MutableLiveData()
    val gameDetails: LiveData<DataState<GameDetails>> = _gameDetails

    val selfName: String? = sharedPreferences.getString(USERNAME_KEY, null)

    private val gameId: Int = state.get("gameId")!!

    fun setStateEvent(stateEvent: PrepareGameEvent) {
        when(stateEvent) {
            is PrepareGameEvent.CheckPlayerReady -> {
                prepareGameInteractors.tryStartingGame().onEach {
                    _gameDetails.value = it
                }.launchIn(viewModelScope)
            }
            is PrepareGameEvent.GetGameDetailsEvent -> {
                prepareGameInteractors.getGameDetails(gameId).onEach {
                    _gameDetails.value = it
                }.launchIn(viewModelScope)
            }
            is PrepareGameEvent.AcceptGameStart -> {

            }
            is PrepareGameEvent.RejectGameStart -> {

            }
        }
    }
}
