package com.mindeurfou.golfbook.interactors.courses

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.datasource.network.course.CourseNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.FakeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoursesInteractors
@Inject constructor(
    private val courseNetworkDataSourceImpl: CourseNetworkDataSourceImpl
) {

    fun getCourses(): Flow<DataState<List<Course>>> = flow {
        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Success(FakeData.courses()))
            return@flow
        }

        try {
            val courses = courseNetworkDataSourceImpl.getCourses()
            emit(DataState.Success(courses))
        } catch (e : Exception) {
            emit(DataState.Failure(e))
        }
    }
}