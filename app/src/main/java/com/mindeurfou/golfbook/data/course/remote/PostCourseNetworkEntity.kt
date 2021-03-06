package com.mindeurfou.golfbook.data.course.remote

import com.mindeurfou.golfbook.data.hole.remote.PostHoleNetworkEntity
import kotlinx.serialization.Serializable

@Serializable
data class PostCourseNetworkEntity(
    val name : String,
    val numberOfHOles : Int,
    val par : Int,
    val stars : Int,
    val holes : List<Int>
)
