package com.mindeurfou.golfbook.ui.createPlayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindeurfou.golfbook.data.player.remote.PostPlayerNetworkEntity
import com.mindeurfou.golfbook.interactors.createPlayer.CreatePlayerInteractors
import com.mindeurfou.golfbook.interactors.createPlayer.CreatePlayerEvent
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.state.StateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePlayerViewModel
@Inject constructor(
    private val createPlayerInteractors: CreatePlayerInteractors
) : ViewModel() {

    private val _playerCreated: MutableLiveData<DataState<Unit>> = MutableLiveData()
    val playerCreated: LiveData<DataState<Unit>> = _playerCreated

    fun setStateEvent(stateEvent: StateEvent) {
        when(stateEvent) {
            is CreatePlayerEvent.SendPlayerInfoEvent -> {
                createPlayerInteractors.sendPlayerInfo(
                    name = stateEvent.name,
                    lastName = stateEvent.lastName,
                    username = stateEvent.username,
                    password = stateEvent.password,
                    avatarId = stateEvent.avatarId
                ).onEach {
                    _playerCreated.value = it
                }.launchIn(viewModelScope)
            }
        }
    }

}