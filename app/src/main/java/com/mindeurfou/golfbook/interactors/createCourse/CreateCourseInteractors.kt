package com.mindeurfou.golfbook.interactors.createCourse

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.course.remote.PostCourseNetworkEntity
import com.mindeurfou.golfbook.datasource.network.course.CourseNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateCourseInteractors
@Inject constructor(
    private val courseNetworkDataSourceImpl: CourseNetworkDataSourceImpl
){

    fun sendCourseInfo(name: String, holes: List<Int>): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)

        kotlinx.coroutines.delay(1000)

        if (BuildConfig.fakeData) {
            emit(DataState.Success(true))
            return@flow
        }

        try {
            // verify data goodness


//            val postCourseNetworkEntity = PostCourseNetworkEntity(name, )
//            courseNetworkDataSourceImpl.postCourse(postCourseNetworkEntity)
            emit(DataState.Success(true))
        } catch (e: Exception) {
            emit(DataState.Failure(e))
        }
    }
}