package com.mindeurfou.golfbook.data.course.remote

import com.mindeurfou.golfbook.data.hole.remote.PostHoleNetworkEntity

data class PostCourseNetworkEntity(
    val name : String,
    val numberOfHOles : Int,
    val par : Int,
    val holes : List<PostHoleNetworkEntity>
)
