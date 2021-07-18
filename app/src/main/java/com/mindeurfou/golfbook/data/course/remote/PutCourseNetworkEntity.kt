package com.mindeurfou.golfbook.data.course.remote

import com.mindeurfou.golfbook.data.hole.local.Hole

data class PutCourseNetworkEntity(
    val id: Int,
    val name : String,
    val numberOfHoles : Int,
    val par : Int,
    val gamesPlayed : Int,
    val holes : List<Hole>
)
