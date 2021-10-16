package com.mindeurfou.golfbook.ui.playerDetails

import androidx.lifecycle.*
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.interactors.playerDetails.PlayerDetailsEvent
import com.mindeurfou.golfbook.interactors.playerDetails.PlayerDetailsInteractors
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.state.StateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerDetailsViewModel
@Inject constructor(
    private val playerDetailsInteractors: PlayerDetailsInteractors,
    state: SavedStateHandle
) : ViewModel(){

    private val _player: MutableLiveData<DataState<Player>> = MutableLiveData()
    val player: LiveData<DataState<Player>> = _player

    private val playerId: Int = state.get("playerId")!!

    fun setStateEvent(stateEvent: StateEvent) {
        viewModelScope.launch {
            when(stateEvent) {
                is PlayerDetailsEvent.GetPlayerEvent -> {
                    playerDetailsInteractors.getPlayer(playerId).onEach {
                        _player.value = it
                    }.launchIn(viewModelScope)
                }
            }
        }
    }
}