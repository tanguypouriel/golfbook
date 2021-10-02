package com.mindeurfou.golfbook.datasource.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mindeurfou.golfbook.utils.AuthInterceptor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitBuilder
@Inject constructor(
    authInterceptor: AuthInterceptor
){

    private val BASE_URL = "http://192.168.1.98:8080/"
    private val contentType = "application/json; charset=utf-8".toMediaType()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .build()

    @ExperimentalSerializationApi
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()

}