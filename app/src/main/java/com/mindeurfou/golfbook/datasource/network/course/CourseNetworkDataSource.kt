package com.mindeurfou.golfbook.datasource.network.course

import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.data.course.remote.PostCourseNetworkEntity
import com.mindeurfou.golfbook.data.course.remote.PutCourseNetworkEntity

interface CourseNetworkDataSource {
    suspend fun getCourse(courseId: Int): CourseDetails
    suspend fun getCourses(): List<Course>
    suspend fun postCourse(postCourse: PostCourseNetworkEntity): CourseDetails
    suspend fun updateCourse(putCourse: PutCourseNetworkEntity): CourseDetails
    suspend fun deleteCourse(courseId: Int): Boolean
}