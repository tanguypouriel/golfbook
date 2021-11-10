package com.mindeurfou.golfbook.ui.prepareGame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val prepareGameInteractors: PrepareGameInteractors
) : ViewModel() {

    private val _gameLaunchedId: MutableLiveData<DataState<Int>> = MutableLiveData()
    val gameLaunchedId: LiveData<DataState<Int>> = _gameLaunchedId

    fun setStateEvent(stateEvent: PrepareGameEvent) {
        when(stateEvent) {
            is PrepareGameEvent.LaunchGameEvent -> {
                prepareGameInteractors.launchGame().onEach {
                    _gameLaunchedId.value = it
                }.launchIn(viewModelScope)
            }
        }
    }
}
