package com.mindeurfou.golfbook.datasource.network.course

import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.data.course.remote.PostCourseNetworkEntity
import com.mindeurfou.golfbook.data.course.remote.PutCourseNetworkEntity
import com.mindeurfou.golfbook.utils.GBException
import com.mindeurfou.golfbook.utils.GBHttpStatusCode
import retrofit2.HttpException
import retrofit2.http.HTTP
import java.net.HttpURLConnection
import javax.inject.Inject

class CourseNetworkDataSourceImpl @Inject constructor(
    private val courseApiService: CourseApiService
) : CourseNetworkDataSource {

    override suspend fun getCourse(courseId: Int): CourseDetails {
        try {
            return courseApiService.getCourse(courseId)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NOT_FOUND)
                throw GBException(GBException.COURSE_NOT_FIND_MESSAGE)
            else
                throw UnknownError("status code is ${e.code()}")
        }
    }

    override suspend fun getCourseByName(name: String): Course {
        return courseApiService.getCourseByName(name)
    }

    override suspend fun getCourses(limit: Int?, offset: Int?): List<Course> {
        try {
            return courseApiService.getCourses(limit, offset)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NO_CONTENT)
                throw GBException(GBException.NO_RESOURCES_MESSAGE)
            else
                throw UnknownError("status code is ${e.code()}")
        }
    }


    override suspend fun postCourse(postCourse: PostCourseNetworkEntity): CourseDetails {
        try {
            return courseApiService.postCourse(postCourse)
        } catch (e: HttpException) {
            if (e.code() == GBHttpStatusCode.valueA)
                throw GBException(GBException.NAME_ALREADY_TAKEN_MESSAGE)
            else
                throw Exception("status code is ${e.code()}")
        }
    }

    override suspend fun updateCourse(putCourse: PutCourseNetworkEntity): CourseDetails {
        try {
            return courseApiService.updateCourse(putCourse.id, putCourse)
        } catch (e: HttpException) {
            if (e.code() == HttpURLConnection.HTTP_NOT_FOUND)
                throw GBException(GBException.COURSE_NOT_FIND_MESSAGE)
            else
                throw UnknownError("status code is ${e.code()}")
        }
    }

    override suspend fun deleteCourse(courseId: Int): Boolean =
        courseApiService.deleteCourse(courseId)
}