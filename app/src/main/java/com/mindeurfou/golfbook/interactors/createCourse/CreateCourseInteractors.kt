package com.mindeurfou.golfbook.interactors.createCourse

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.course.remote.PostCourseNetworkEntity
import com.mindeurfou.golfbook.data.hole.local.Hole
import com.mindeurfou.golfbook.datasource.network.course.CourseNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.ErrorMessages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateCourseInteractors
@Inject constructor(
    private val courseNetworkDataSourceImpl: CourseNetworkDataSourceImpl
){

fun sendCourseInfo(name: String, holes: List<Int>): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)


        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Success(Unit))
            return@flow
        }

        val errors: MutableList<ErrorMessages> = mutableListOf()

        if (name.isBlank())
            errors.add(ErrorMessages.NAME_EMPTY)
        if (holes.any { it == 0 })
            errors.add(ErrorMessages.HOLES_UNCOMPLETED)
        if (holes.size != 9 || holes.size != 18)
            errors.add(ErrorMessages.BAD_INPUT)

        if (errors.size != 0) {
            emit(DataState.Failure(errors))
            return@flow
        }

        val postCourse = PostCourseNetworkEntity(
            name = name,
            numberOfHOles = holes.size,
            par = Hole.computePar(holes),
            stars = 5,
            holes = holes
        )

        try {
            val courseDetails = courseNetworkDataSourceImpl.postCourse(postCourse)
            if (courseDetails.equalsPostCourse(postCourse))
                emit(DataState.Success(Unit))
            else
                emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        } catch (e: Exception) {
            emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }
    }
}