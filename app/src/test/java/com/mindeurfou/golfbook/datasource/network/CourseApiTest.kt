package com.mindeurfou.golfbook.datasource.network

import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.data.hole.local.Hole
import com.mindeurfou.golfbook.datasource.network.course.CourseApiService
import com.mindeurfou.golfbook.datasource.network.course.CourseNetworkDataSource
import com.mindeurfou.golfbook.datasource.network.course.CourseNetworkDataSourceImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockResponse
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDate

@ExperimentalSerializationApi
@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseApiTest : BaseApiTest() {

    private val courseNetworkDataSource: CourseNetworkDataSource = CourseNetworkDataSourceImpl(getApiService(CourseApiService::class.java))

    @BeforeAll
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterAll
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
        mockWebServer.shutdown()
    }

    @Test
    fun getCourse() = runBlocking {
        mockWebServer.enqueueResponse("courseDetails.json", 436)

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
}