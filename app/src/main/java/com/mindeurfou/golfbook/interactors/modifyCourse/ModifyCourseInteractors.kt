package com.mindeurfou.golfbook.interactors.modifyCourse

import com.mindeurfou.golfbook.BuildConfig
import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.datasource.network.course.CourseNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ModifyCourseInteractors
@Inject constructor(
    private val courseNetworkDataSourceImpl: CourseNetworkDataSourceImpl
) {

    fun sendModification(courseDetails: CourseDetails) : Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)

        if (BuildConfig.fakeData) {
            kotlinx.coroutines.delay(1000)
            emit(DataState.Success(true))
            return@flow
        }

//        try {
//            val returnedCourse = courseNetworkDataSourceImpl.updateCourse()
//            emit(DataState.Success(true))
//        } catch (e: Exception) {
//            emit(DataState.Failure(e))
//        }
    }
}