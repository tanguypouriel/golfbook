package com.mindeurfou.golfbook.interactors.modifyCourse

import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class ModifyCourseEvent : StateEvent {

    class SendModificationEvent(val courseDetails: CourseDetails) : ModifyCourseEvent()
}