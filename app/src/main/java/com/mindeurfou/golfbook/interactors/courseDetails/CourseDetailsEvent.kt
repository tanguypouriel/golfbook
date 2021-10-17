package com.mindeurfou.golfbook.interactors.courseDetails

import com.mindeurfou.golfbook.utils.state.StateEvent

sealed class CourseDetailsEvent : StateEvent {
    object GetCourseDetails: CourseDetailsEvent()
}