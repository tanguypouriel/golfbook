package com.mindeurfou.golfbook.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mindeurfou.golfbook.utils.state.StateEvent

abstract class BaseViewModel<ViewState> : ViewModel() {

    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()
    val viewState: LiveData<ViewState>
        get() = _viewState

    abstract fun handleNewData(data: ViewState)

    abstract fun setStateEvent(stateEvent: StateEvent)

    fun setViewState(viewState: ViewState) {
        _viewState.value = viewState
    }
}