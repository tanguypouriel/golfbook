package com.mindeurfou.golfbook.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindeurfou.golfbook.interactors.splash.SplashEvent
import com.mindeurfou.golfbook.interactors.splash.SplashInteractors
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.state.StateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject constructor(
    private val splashInteractors: SplashInteractors
) : ViewModel() {

    private val _validToken: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val validToken: LiveData<DataState<Boolean>> = _validToken

    fun setStateEvent(stateEvent: SplashEvent) {
        when (stateEvent) {
            is SplashEvent.CheckToken -> {
                splashInteractors.checkToken().onEach {
                    _validToken.value = it
                }.launchIn(viewModelScope)
            }
        }
    }
}