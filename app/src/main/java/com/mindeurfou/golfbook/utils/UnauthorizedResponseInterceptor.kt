package com.mindeurfou.golfbook.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors.Companion.TOKEN_KEY
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors.Companion.VALIDITY_KEY
import com.mindeurfou.golfbook.ui.StartActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class UnauthorizedResponseInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED)
            handleUnauthorizedResponse()

        return response
    }

    private fun handleUnauthorizedResponse() {
        sharedPreferences.edit()
            .remove(TOKEN_KEY)
            .remove(VALIDITY_KEY)
            .apply()
    }
}