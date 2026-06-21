package com.donacion.mobile.di

import android.content.Context

object ContainerProvider {
    @Volatile private var container: AppContainer? = null
    fun get(context: Context): AppContainer = container ?: synchronized(this) {
        container ?: AppContainer(context.applicationContext).also { container = it }
    }
}
