package com.mindeurfou.golfbook.interactors.createCourse

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.course.remote.PostCourseNetworkEntity
import com.mindeurfou.golfbook.datasource.network.course.CourseNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.ErrorMessages
import com.mindeurfou.golfbook.utils.GBException
import com.mindeurfou.golfbook.utils.addError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateCourseInteractors
@Inject constructor(
    private val courseNetworkDataSourceImpl: CourseNetworkDataSourceImpl
){

    fun sendCourseInfo(name: String, holes: List<Int>): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)


        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Success(true))
            return@flow
        }

        val errors: MutableList<ErrorMessages> = mutableListOf()

        if (name.isBlank())
            errors.add(ErrorMessages.NAME_EMPTY)
        if (holes.any { it == 0 })
            errors.add(ErrorMessages.HOLES_UNCOMPLETED)

        if (errors.size != 0)
            emit(DataState.Failure(errors))

//        val postCourse = PostCourseNetworkEntity(
//
//        )

        try {
            // verify data goodness


//            val postCourseNetworkEntity = PostCourseNetworkEntity(name, )
//            courseNetworkDataSourceImpl.postCourse(postCourseNetworkEntity)
            emit(DataState.Success(true))
        } catch (e: Exception) {
            emit(DataState.Failure(listOf(ErrorMessages.NETWORK_ERROR)))
        }
    }
}