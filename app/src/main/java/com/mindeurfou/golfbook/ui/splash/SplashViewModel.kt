package com.mindeurfou.golfbook.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindeurfou.golfbook.interactors.splash.SplashInteractors
import com.mindeurfou.golfbook.utils.state.StateEvent
import kotlinx.coroutines.launch

class SplashViewModel(
    private val splashInteractors: SplashInteractors? = null
) : ViewModel() {

    fun getCredentials() : Pair<String, String> {
        return Pair("tanguy","password")
    }
}