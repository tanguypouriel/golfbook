package com.mindeurfou.golfbook.ui.connection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindeurfou.golfbook.interactors.connection.ConnectionEvent
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.state.StateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel
@Inject constructor(
    private val connectionInteractors: ConnectionInteractors
) : ViewModel() {

    private val _connected: MutableLiveData<DataState<Unit>> = MutableLiveData()
    val connected: LiveData<DataState<Unit>> = _connected

    private val _credentials: MutableLiveData<DataState<Pair<String, String>?>> = MutableLiveData()
    val credentials: LiveData<DataState<Pair<String, String>?>> = _credentials

    fun setStateEvent(stateEvent: StateEvent) {
        when(stateEvent) {
            is ConnectionEvent.ConnectEvent -> {
                connectionInteractors.connect(stateEvent.username, stateEvent.password, stateEvent.rememberMe).onEach {
                    _connected.value = it
                }.launchIn(viewModelScope)
            }
            is ConnectionEvent.RetrieveCredentialsEvent -> {
                connectionInteractors.retrieveCredentials().onEach {
                    _credentials.value = it
                }.launchIn(viewModelScope)
            }
        }
    }

}