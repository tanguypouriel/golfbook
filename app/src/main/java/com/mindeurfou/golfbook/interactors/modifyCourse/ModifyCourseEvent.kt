package com.mindeurfou.golfbook.interactors.modifyCourse

import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.data.hole.local.Hole
import com.mindeurfou.golfbook.utils.state.StateEvent
import java.time.LocalDate

sealed class ModifyCourseEvent : StateEvent {

    class SendModificationEvent(
        val id: Int,
        val name: String,
        val gamesPlayed: Int,
        val stars: Int,
        val createdAt: LocalDate,
        val holes: List<Hole>
    ) : ModifyCourseEvent()
}