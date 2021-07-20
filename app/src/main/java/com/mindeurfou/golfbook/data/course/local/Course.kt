package com.mindeurfou.golfbook.data.course.local

import com.mindeurfou.golfbook.utils.DateAsLongSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Course(
    val id: Int,
    val name : String,
    val numberOfHOles : Int,
    val par : Int,
    val gamesPlayed : Int,
    @Serializable(with = DateAsLongSerializer::class)
    val createdAt : LocalDate
)
