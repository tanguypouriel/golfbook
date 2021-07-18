package com.mindeurfou.golfbook.data.course.local

import java.time.LocalDate

data class Course(
    val id: Int,
    val name : String,
    val numberOfHOles : Int,
    val par : Int,
    val gamesPlayed : Int,
    val createdAt : LocalDate
)
