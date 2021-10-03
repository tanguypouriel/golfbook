package com.mindeurfou.golfbook.datasource.network

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mindeurfou.golfbook.utils.AuthInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitBuilder
@Inject constructor(
    @ApplicationContext context: Context,
    authInterceptor: AuthInterceptor
){

    private val BASE_URL = "http://192.168.1.98:8080/"
    private val contentType = "application/json; charset=utf-8".toMediaType()
    private val cacheSize = (5 * 1024 * 1024).toLong()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .cache(Cache(context.cacheDir, cacheSize))
        .addInterceptor(authInterceptor)
        .build()

    @ExperimentalSerializationApi
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()

}