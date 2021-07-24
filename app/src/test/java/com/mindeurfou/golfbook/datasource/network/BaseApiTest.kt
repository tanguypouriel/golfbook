package com.mindeurfou.golfbook.datasource.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
abstract class BaseApiTest {

    protected val testDispatcher = TestCoroutineDispatcher()
    protected val testScope = TestCoroutineScope(testDispatcher)
    protected val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(Json.asConverterFactory("application/json; charset=utf-8".toMediaType()))
        .build()

    fun <R> getApiService(clazz: Class<R>): R {
        return retrofit.create(clazz)
    }

}

internal fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
    val file = File("../resources/responses_json/$fileName")
    val jsonString = file.readText()

    enqueue(
        MockResponse()
            .setResponseCode(code)
            .setHeader("Content-Type", "application/json")
            .setBody(jsonString)
    )
}
internal fun MockWebServer.enqueueGBErrorResponse(code: Int) {
    enqueue(
        MockResponse()
            .setResponseCode(code)
            .setHeader("Content-Type", "text/plain;charset=utf-8")
    )
}
