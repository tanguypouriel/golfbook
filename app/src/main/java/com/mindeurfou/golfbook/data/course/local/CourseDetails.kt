package com.mindeurfou.golfbook.data.course.local

import android.os.Parcelable
import com.mindeurfou.golfbook.data.course.remote.PostCourseNetworkEntity
import com.mindeurfou.golfbook.data.course.remote.PutCourseNetworkEntity
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
    val numberOfHoles : Int,
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

    fun equalsPostCourse(postCourse: PostCourseNetworkEntity) : Boolean {
        val baseIsEqual = name == postCourse.name &&
                numberOfHoles == postCourse.numberOfHOles &&
                par == postCourse.par &&
                stars == postCourse.stars

        var holesAreEqual = true
        postCourse.holes.forEachIndexed  { index, par ->
            if (par != holes[index].par) {
                holesAreEqual = false
                return@forEachIndexed
            }
        }

        return baseIsEqual && holesAreEqual
    }

    fun toCourse() = Course(
        id = id,
        name = name,
        numberOfHoles = numberOfHoles,
        par = par,
        gamesPlayed = gamesPlayed,
        stars = stars,
        createdAt = createdAt
    )
}
