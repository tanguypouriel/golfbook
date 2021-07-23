package com.mindeurfou.golfbook.datasource.network

import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.data.hole.local.Hole
import com.mindeurfou.golfbook.datasource.network.course.CourseApiService
import com.mindeurfou.golfbook.datasource.network.course.CourseNetworkDataSource
import com.mindeurfou.golfbook.datasource.network.course.CourseNetworkDataSourceImpl
import com.mindeurfou.golfbook.utils.GBException
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockResponse
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.time.LocalDate

@ExperimentalSerializationApi
@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseNetworkDataSourceTest : BaseApiTest() {

    private val courseNetworkDataSource: CourseNetworkDataSource = CourseNetworkDataSourceImpl(getApiService(CourseApiService::class.java))

    @AfterAll
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getCourse() = runBlocking {
        mockWebServer.enqueueResponse("courseDetails.json", 200)

        val result = courseNetworkDataSource.getCourse(1)
        val expected = CourseDetails(
            1,
            "Parcours du test",
            9,
            30,
            0,
            LocalDate.ofEpochDay(18826),
            listOf(
                Hole(1, 1, 4),
                Hole(2, 2, 3),
                Hole(3, 3, 3),
                Hole(4, 4, 3),
                Hole(5, 5, 3),
                Hole(6, 6, 3),
                Hole(7, 7, 4),
                Hole(8, 8, 3),
                Hole(9, 9, 4)
            )
        )
        assertEquals(expected, result)

    }

    @Test
    fun `get non existing course`(): Unit = runBlocking {
        mockWebServer.enqueueGBErrorResponse(HttpURLConnection.HTTP_NOT_FOUND)
        mockWebServer.enqueueGBErrorResponse(HttpURLConnection.HTTP_NOT_FOUND)

        assertThrows<GBException> {
            courseNetworkDataSource.getCourse(1)
        }

        try {
            courseNetworkDataSource.getCourse(1)
        } catch (e: GBException) {
            assertEquals(GBException.COURSE_NOT_FIND_MESSAGE, e.message)
        }
    }

    @Test
    fun getCourseList(): Unit = runBlocking {
        mockWebServer.enqueueResponse("courseList.json", 200)

        val courses = courseNetworkDataSource.getCourses()
        val expected = listOf(
            Course(
                3,
                "Parcours de retest",
                9,
                33,
                0,
                LocalDate.ofEpochDay(18828)
            ),
            Course(
                1,
                "Parcours du test",
                9,
                30,
                0,
                LocalDate.ofEpochDay(18826)
            )
        )

        assertEquals(expected, courses)
    }


}