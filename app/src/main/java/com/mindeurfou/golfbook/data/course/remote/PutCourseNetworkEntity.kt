package com.mindeurfou.golfbook.data.course.remote

import com.mindeurfou.golfbook.data.hole.local.Hole
import com.mindeurfou.golfbook.utils.DateAsLongSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class PutCourseNetworkEntity(
    val id: Int,
    val name : String,
    val numberOfHoles : Int,
    val par : Int,
    val stars : Int,
    val holes : List<Hole>
)
