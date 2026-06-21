package com.donacion.mobile.data.repository

import com.donacion.mobile.core.network.*
import com.donacion.mobile.core.session.*
import com.donacion.mobile.data.dto.*
import com.donacion.mobile.data.remote.DonationApiService
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

class DonationRepository(private val api: DonationApiService, private val session: SessionManager) {
    val sessionFlow: Flow<UserSession?> = session.session

    suspend fun login(correo: String, password: String): NetworkResult<LoginResponse> = unwrap(safeApiCall { api.login(LoginRequest(correo, password)) }).also {
        if (it is NetworkResult.Success) session.save(UserSession(it.data.token, it.data.idUsuario, it.data.nombres, it.data.correo, it.data.rol))
    }
    suspend fun register(request: RegistroRequest): NetworkResult<LoginResponse> = unwrap(safeApiCall { api.register(request) }).also {
        if (it is NetworkResult.Success) session.save(UserSession(it.data.token, it.data.idUsuario, it.data.nombres, it.data.correo, it.data.rol))
    }
    suspend fun logout() = session.clear()

    suspend fun categorias() = unwrap(safeApiCall { api.categoriasActivas() })
    suspend fun disponibles(distrito: String?, categoria: Long?) = unwrap(safeApiCall { api.publicacionesDisponibles(distrito, categoria) })
    suspend fun cercanas(lat: BigDecimal, lon: BigDecimal, radio: Double) = unwrap(safeApiCall { api.publicacionesCercanas(lat, lon, radio) })
    suspend fun detallePublicacion(id: Long) = unwrap(safeApiCall { api.detallePublicacion(id) })
    suspend fun crearPublicacion(request: PublicacionAlimentoRequest) = unwrap(safeApiCall { api.crearPublicacion(request) })
    suspend fun misPublicaciones() = unwrap(safeApiCall { api.misPublicaciones() })
    suspend fun confirmarDisponibilidad(id: Long) = unwrap(safeApiCall { api.confirmarDisponibilidad(id) })
    suspend fun adminPublicaciones() = unwrap(safeApiCall { api.adminPublicaciones() })
    suspend fun aprobarPublicacion(id: Long) = unwrap(safeApiCall { api.aprobarPublicacion(id) })
    suspend fun bloquearPublicacion(id: Long, motivo: String) = unwrap(safeApiCall { api.bloquearPublicacion(id, motivo) })

    suspend fun solicitar(request: SolicitudDonacionRequest) = unwrap(safeApiCall { api.solicitarDonacion(request) })
    suspend fun misSolicitudes() = unwrap(safeApiCall { api.misSolicitudes() })
    suspend fun cancelarSolicitud(id: Long) = unwrap(safeApiCall { api.cancelarSolicitud(id) })
    suspend fun solicitudesRecibidas() = unwrap(safeApiCall { api.solicitudesRecibidas() })
    suspend fun aceptarSolicitud(id: Long) = unwrap(safeApiCall { api.aceptarSolicitud(id) })
    suspend fun rechazarSolicitud(id: Long, observacion: String?) = unwrap(safeApiCall { api.rechazarSolicitud(id, observacion) })
    suspend fun confirmarEntrega(id: Long) = unwrap(safeApiCall { api.confirmarEntrega(id) })

    suspend fun dashboardResumen() = unwrap(safeApiCall { api.dashboardResumen() })
    suspend fun usuarios() = unwrap(safeApiCall { api.usuarios() })
    suspend fun bloquearUsuario(id: Long) = unwrap(safeApiCall { api.cambiarEstadoUsuario(id, mapOf("estado" to "BLOQUEADO")) })
    suspend fun reportes() = unwrap(safeApiCall { api.reportes() })
    suspend fun historial() = unwrap(safeApiCall { api.historial() })

    private fun <T> unwrap(result: NetworkResult<ApiResponse<T>>): NetworkResult<T> = when (result) {
        is NetworkResult.Success -> result.data.data?.let { NetworkResult.Success(it) } ?: NetworkResult.Error(result.data.mensaje ?: "Sin datos")
        is NetworkResult.Error -> result
    }
}
