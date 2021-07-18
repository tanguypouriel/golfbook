package com.mindeurfou.golfbook.datasource.network.course

import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.data.course.remote.PostCourseNetworkEntity
import com.mindeurfou.golfbook.data.course.remote.PutCourseNetworkEntity
import com.mindeurfou.golfbook.datasource.network.RetrofitBuilder
import retrofit2.HttpException
import javax.inject.Inject

class CourseNetworkDataSourceImpl @Inject constructor(
    private val courseApiService: CourseApiService
) : CourseNetworkDataSource {

    override suspend fun getCourse(courseId: Int): CourseDetails? {
        return try {
            courseApiService.getCourse(courseId)
        } catch (e: HttpException) {
            print(e.response())
            null
        }
    }

    override suspend fun getCourses(): List<Course> =
        courseApiService.getCourses()

    override suspend fun postCourse(postCourse: PostCourseNetworkEntity): CourseDetails =
        courseApiService.postCourse(postCourse)

    override suspend fun updateCourse(putCourse: PutCourseNetworkEntity): CourseDetails =
        courseApiService.updateCourse(putCourse.id, putCourse)

    override suspend fun deleteCourse(courseId: Int): Boolean =
        courseApiService.deleteCourse(courseId)
}