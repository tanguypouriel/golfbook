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

    private val _playerCreated: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val playerCreated: LiveData<DataState<Boolean>> = _playerCreated

    fun setStateEvent(stateEvent: StateEvent) {
        viewModelScope.launch {
            when(stateEvent) {
                is CreatePlayerEvent.SendPlayerInfoEvent -> {
                    createPlayerInteractors.sendPlayerInfo(
                        PostPlayerNetworkEntity(
                            name = stateEvent.name,
                            lastName = stateEvent.lastName,
                            username = stateEvent.username,
                            password = stateEvent.password,
                            drawableResourceId = stateEvent.avatarId
                        )
                    ).onEach {
                        _playerCreated.value = it
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

}