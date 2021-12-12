package com.mindeurfou.golfbook.data.course.local

import android.os.Parcelable
import com.mindeurfou.golfbook.data.hole.local.Hole
import com.mindeurfou.golfbook.utils.DateAsLongSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Parcelize
@Serializable
data class CourseDetails(
    val id: Int,
    val name : String,
    val numberOfHOles : Int,
    val par : Int,
    val gamesPlayed : Int,
    val stars: Int,
    @Serializable(with = DateAsLongSerializer::class)
    val createdAt : LocalDate,
    val holes : List<Hole>
) : Parcelable {

    fun getParList() : List<Int> {
        return holes.map { it.par }
    }
}
