package com.donacion.mobile.core.network

import com.donacion.mobile.core.session.SessionManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { sessionManager.session.firstOrNull()?.token }
        val request = if (token.isNullOrBlank()) chain.request() else chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token").build()
        return chain.proceed(request)
    }
}
