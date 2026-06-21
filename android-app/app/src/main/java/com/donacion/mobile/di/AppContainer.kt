package com.donacion.mobile.di

import android.content.Context
import com.donacion.mobile.core.network.AuthInterceptor
import com.donacion.mobile.core.session.SessionManager
import com.donacion.mobile.data.remote.DonationApiService
import com.donacion.mobile.data.repository.DonationRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {
    val sessionManager = SessionManager(context)
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(sessionManager))
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()
    private val retrofit = Retrofit.Builder()
        // Para emulador Android contra backend local: 10.0.2.2 apunta al localhost del host.
        .baseUrl("http://10.0.2.2:8080/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api: DonationApiService = retrofit.create(DonationApiService::class.java)
    val repository = DonationRepository(api, sessionManager)
}
