package com.donacion.mobile.core.session

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("session")

data class UserSession(val token: String, val idUsuario: Long, val nombres: String, val correo: String, val rol: String)

class SessionManager(private val context: Context) {
    private val tokenKey = stringPreferencesKey("token")
    private val idKey = longPreferencesKey("idUsuario")
    private val nameKey = stringPreferencesKey("nombres")
    private val emailKey = stringPreferencesKey("correo")
    private val roleKey = stringPreferencesKey("rol")

    val session: Flow<UserSession?> = context.dataStore.data.map { p ->
        val token = p[tokenKey].orEmpty()
        if (token.isBlank()) null else UserSession(token, p[idKey] ?: 0, p[nameKey].orEmpty(), p[emailKey].orEmpty(), p[roleKey].orEmpty())
    }

    suspend fun save(session: UserSession) = context.dataStore.edit { p ->
        p[tokenKey] = session.token; p[idKey] = session.idUsuario; p[nameKey] = session.nombres; p[emailKey] = session.correo; p[roleKey] = session.rol
    }

    suspend fun clear() = context.dataStore.edit { it.clear() }
}
