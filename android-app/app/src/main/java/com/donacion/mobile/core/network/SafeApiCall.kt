package com.donacion.mobile.core.network

import retrofit2.Response

suspend fun <T> safeApiCall(call: suspend () -> Response<T>): NetworkResult<T> = try {
    val response = call()
    if (response.isSuccessful) {
        response.body()?.let { NetworkResult.Success(it) } ?: NetworkResult.Error("Respuesta vacía", response.code())
    } else {
        val message = when (response.code()) {
            400 -> "Revisa los datos ingresados"
            401 -> "Sesión expirada"
            403 -> "No tienes permisos"
            404 -> "Recurso no encontrado"
            500 -> "Error del servidor"
            else -> "Error ${response.code()}"
        }
        NetworkResult.Error(message, response.code())
    }
} catch (e: Exception) {
    NetworkResult.Error(e.message ?: "No se pudo conectar con el servidor")
}
