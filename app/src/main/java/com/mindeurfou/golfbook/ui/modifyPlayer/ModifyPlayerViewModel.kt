package com.mindeurfou.golfbook.ui.modifyPlayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindeurfou.golfbook.interactors.modifyPlayer.ModifyPlayerEvent
import com.mindeurfou.golfbook.interactors.modifyPlayer.ModifyPlayerInteractors
import com.mindeurfou.golfbook.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyPlayerViewModel
@Inject constructor(
    private val modifyPlayerInteractors: ModifyPlayerInteractors
) : ViewModel(){

    private val _modificationAccepted: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val modificationAccepted: LiveData<DataState<Boolean>> = _modificationAccepted

    fun setStateEvent(stateEvent: ModifyPlayerEvent) {
        when(stateEvent) {
            is ModifyPlayerEvent.SendModificationEvent -> {
                modifyPlayerInteractors.sendModification(
                    stateEvent.id,
                    stateEvent.name,
                    stateEvent.lastName,
                    stateEvent.username,
                    stateEvent.drawableResourceId
                ).onEach {
                    _modificationAccepted.value = it
                }.launchIn(viewModelScope)
            }
        }
    }
}