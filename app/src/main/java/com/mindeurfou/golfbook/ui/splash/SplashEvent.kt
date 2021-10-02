package com.mindeurfou.golfbook.ui.splash

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class SplashEvent : StateEvent {

    object CheckToken : SplashEvent()
}
