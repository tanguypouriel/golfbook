package com.mindeurfou.golfbook.interactors.modifyCourse

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.data.course.remote.PostCourseNetworkEntity
import com.mindeurfou.golfbook.data.course.remote.PutCourseNetworkEntity
import com.mindeurfou.golfbook.data.hole.local.Hole
import com.mindeurfou.golfbook.datasource.network.course.CourseNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.ErrorMessages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class ModifyCourseInteractors
@Inject constructor(
    private val courseNetworkDataSourceImpl: CourseNetworkDataSourceImpl
) {

    fun sendModification(
        id: Int,
        name: String,
        gamesPlayed: Int,
        stars: Int,
        createdAt: LocalDate,
        holes: List<Hole>
    ) : Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Success(Unit))
            return@flow
        }

        val errors: MutableList<ErrorMessages> = mutableListOf()

        if (name.isBlank())
            errors.add(ErrorMessages.NAME_EMPTY)
        if (holes.any { it.par == 0 })
            errors.add(ErrorMessages.HOLES_UNCOMPLETED)
        if (holes.size != 9 || holes.size != 18)
            errors.add(ErrorMessages.BAD_INPUT)

        if (errors.size != 0) {
            emit(DataState.Failure(errors))
            return@flow
        }

        val putCourse = PutCourseNetworkEntity(
            id = id,
            name = name,
            numberOfHoles = holes.size,
            par = Hole.computePar(holes),
            gamesPlayed = gamesPlayed,
            stars = stars,
            createdAt = createdAt,
            holes = holes
        )

        try {
            courseNetworkDataSourceImpl.updateCourse(putCourse)
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }
    }
}