package com.mindeurfou.golfbook.interactors.createCourse

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class CreateCourseEvent : StateEvent {

    class SendCourseInfoEvent(
        val name: String,
        val holes: List<Int>
    ) : CreateCourseEvent()

}
