package com.mindeurfou.golfbook.interactors.courseDetails

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.datasource.network.course.CourseNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.FakeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CourseDetailsInteractors @Inject constructor(
    private val courseNetworkDataSourceImpl: CourseNetworkDataSourceImpl
) {

    fun getCourse(courseId: Int): Flow<DataState<CourseDetails>> = flow {
        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(2000)
            emit(DataState.Success(FakeData.courseDetails()))
        }

        try {
            val course = courseNetworkDataSourceImpl.getCourse(courseId)
            emit(DataState.Success(course))
        } catch (e: Exception) {
            emit(DataState.Failure(e))
        }
    }

}