package com.mindeurfou.golfbook.utils

import android.content.SharedPreferences
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors.Companion.TOKEN_KEY
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor
@Inject constructor(
    private val sharedPreferences: SharedPreferences
): Interceptor {

    private var token: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        fetchToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }


    private fun fetchToken() : String? {
        if (token == null)
            token = sharedPreferences.getString(TOKEN_KEY, null)

        return token
    }
}