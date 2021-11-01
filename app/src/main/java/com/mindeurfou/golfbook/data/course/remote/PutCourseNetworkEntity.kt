package com.mindeurfou.golfbook.data.course.remote

import com.mindeurfou.golfbook.data.hole.local.Hole
import kotlinx.serialization.Serializable

@Serializable
data class PutCourseNetworkEntity(
    val id: Int,
    val name : String,
    val numberOfHoles : Int,
    val par : Int,
    val gamesPlayed : Int,
    val stars : Int,
    val holes : List<Hole>
)
