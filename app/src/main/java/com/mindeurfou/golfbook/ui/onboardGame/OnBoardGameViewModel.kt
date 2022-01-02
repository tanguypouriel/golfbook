package com.mindeurfou.golfbook.ui.onboardGame

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.player.local.Player
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
    private val onBoardGameInteractors: OnBoardGameInteractors,
) : ViewModel() {

    private val _initGames: MutableLiveData<DataState<List<Game>?>> = MutableLiveData()
    val initGames: LiveData<DataState<List<Game>?>> = _initGames

    private val _joinGameStatus: MutableLiveData<DataState<Int>> = MutableLiveData()
    val joinGameStatus: LiveData<DataState<Int>> = _joinGameStatus

    fun setStateEvent(stateEvent: OnBoardGameEvent) {
        when (stateEvent) {
            is OnBoardGameEvent.CheckPendingGameEvent -> {
                onBoardGameInteractors.getInitGames().onEach {
                    _initGames.value = it
                }.launchIn(viewModelScope)
            }
            is OnBoardGameEvent.JoinGameEvent -> {
                onBoardGameInteractors.joinGame(stateEvent.gameId, stateEvent.players).onEach {
                    _joinGameStatus.value = it
                }.launchIn(viewModelScope)
            }
        }
    }

}
