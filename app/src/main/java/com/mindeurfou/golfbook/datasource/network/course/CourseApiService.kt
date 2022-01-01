package com.mindeurfou.golfbook.datasource.network.course

import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.data.course.remote.PostCourseNetworkEntity
import com.mindeurfou.golfbook.data.course.remote.PutCourseNetworkEntity
import retrofit2.http.*

interface CourseApiService {

    @GET("/course/{courseId}")
    suspend fun getCourse(@Path("courseId") courseId: Int): CourseDetails

    @GET("/course")
    suspend fun getCourseByName(@Query("name") name: String): Course

    @GET("/course")
    suspend fun getCourses(@Query("limit") limit: Int? = null, @Query("offset") offset: Int? = null): List<Course>

    @POST("/course")
    suspend fun postCourse(@Body postCourse: PostCourseNetworkEntity): CourseDetails

    @PUT("/course/{courseId}")
    suspend fun updateCourse(@Path("courseId") courseId: Int, @Body putCourse: PutCourseNetworkEntity): CourseDetails

    @DELETE("/course/{courseId}")
    suspend fun deleteCourse(@Path("courseId") courseId: Int): Boolean

}