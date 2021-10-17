package com.mindeurfou.golfbook.interactors.courses

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class CoursesEvent : StateEvent {
    object GetCoursesEvent : CoursesEvent()
}
