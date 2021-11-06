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

    private val _gameLaunched: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val gameLaunched: LiveData<DataState<Boolean>> = _gameLaunched

    fun setStateEvent(stateEvent: PrepareGameEvent) {
        when(stateEvent) {
            is PrepareGameEvent.LaunchGameEvent -> {
                prepareGameInteractors.launchGame().onEach {
                    _gameLaunched.value = it
                }.launchIn(viewModelScope)
            }
        }
    }
}
