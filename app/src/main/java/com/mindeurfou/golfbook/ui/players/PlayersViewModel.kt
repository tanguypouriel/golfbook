package com.mindeurfou.golfbook.ui.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.state.StateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel
@Inject constructor(
    private val playersInteractors: PlayersInteractors

) : ViewModel() {

    private val _players : MutableLiveData<DataState<List<Player>>> = MutableLiveData()
    val players : LiveData<DataState<List<Player>>> = _players

    fun setStateEvent(stateEvent: StateEvent) {
        when(stateEvent) {
            is PlayersEvent.GetPlayersEvent -> {
                playersInteractors.getPlayers().onEach {
                    _players.value = it
                }.launchIn(viewModelScope)
            }
        }
    }
}
