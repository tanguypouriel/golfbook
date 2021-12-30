package com.mindeurfou.golfbook.datasource.network

import com.mindeurfou.golfbook.utils.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WebSocketBuilder
@Inject constructor(
    authInterceptor: AuthInterceptor
) {

    private val BASE_URL = "ws://192.168.1.98:8080/"
    private val PING_INTERVAL = 40L

    private val httpClient = OkHttpClient.Builder()
        .pingInterval(PING_INTERVAL, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .build()

    private val request = Request.Builder()
        .url(BASE_URL)
        .build()


    fun openSocket(listener: WebSocketListener) : WebSocket =
        httpClient.newWebSocket(request, listener)

}