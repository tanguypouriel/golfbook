package com.mindeurfou.golfbook.ui.onboardGame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.interactors.onboardGame.OnBoardGameEvent
import com.mindeurfou.golfbook.interactors.onboardGame.OnBoardGameInteractors
import com.mindeurfou.golfbook.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class OnBoardGameViewModel @Inject constructor(
    private val onBoardGameInteractors: OnBoardGameInteractors
) : ViewModel() {

    private val _pendingGames: MutableLiveData<DataState<List<Game>?>> = MutableLiveData()
    val pendingGames: LiveData<DataState<List<Game>?>> = _pendingGames

    fun setStateEvent(stateEvent: OnBoardGameEvent) {
        when (stateEvent) {
            is OnBoardGameEvent.CheckPendingGameEvent -> {
                onBoardGameInteractors.checkPendingGame().onEach {
                    _pendingGames.value = it
                }.launchIn(viewModelScope)
            }
        }
    }

}
