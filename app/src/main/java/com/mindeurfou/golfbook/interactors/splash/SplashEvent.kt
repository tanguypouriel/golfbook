package com.mindeurfou.golfbook.interactors.splash

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class SplashEvent : StateEvent {

    object CheckToken : SplashEvent()
}
