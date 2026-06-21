package com.donacion.mobile.data.remote

import com.donacion.mobile.data.dto.*
import retrofit2.Response
import retrofit2.http.*
import java.math.BigDecimal

interface DonationApiService {
    @POST("api/auth/login") suspend fun login(@Body request: LoginRequest): Response<ApiResponse<LoginResponse>>
    // Backend real: /api/auth/registro, no /api/auth/register.
    @POST("api/auth/registro") suspend fun register(@Body request: RegistroRequest): Response<ApiResponse<LoginResponse>>

    @GET("api/categorias/activas") suspend fun categoriasActivas(): Response<ApiResponse<List<CategoriaAlimentoResponse>>>

    // Backend real de publicaciones: rutas bajo /api/public, /api/donante y /api/admin.
    @GET("api/public/publicaciones") suspend fun publicacionesDisponibles(@Query("distrito") distrito: String? = null, @Query("categoria") categoria: Long? = null): Response<ApiResponse<List<PublicacionAlimentoResponse>>>
    @GET("api/public/publicaciones/cercanas") suspend fun publicacionesCercanas(@Query("latitud") latitud: BigDecimal, @Query("longitud") longitud: BigDecimal, @Query("radioKm") radioKm: Double): Response<ApiResponse<List<PublicacionAlimentoResponse>>>
    @GET("api/public/publicaciones/{id}") suspend fun detallePublicacion(@Path("id") id: Long): Response<ApiResponse<PublicacionAlimentoResponse>>
    @POST("api/donante/publicaciones") suspend fun crearPublicacion(@Body request: PublicacionAlimentoRequest): Response<ApiResponse<PublicacionAlimentoResponse>>
    @GET("api/donante/publicaciones") suspend fun misPublicaciones(): Response<ApiResponse<List<PublicacionAlimentoResponse>>>
    @PUT("api/donante/publicaciones/{id}") suspend fun editarPublicacion(@Path("id") id: Long, @Body request: PublicacionAlimentoRequest): Response<ApiResponse<PublicacionAlimentoResponse>>
    @PATCH("api/donante/publicaciones/{id}/confirmar-disponibilidad") suspend fun confirmarDisponibilidad(@Path("id") id: Long): Response<ApiResponse<PublicacionAlimentoResponse>>
    @GET("api/admin/publicaciones") suspend fun adminPublicaciones(): Response<ApiResponse<List<PublicacionAlimentoResponse>>>
    @PATCH("api/admin/publicaciones/{id}/aprobar") suspend fun aprobarPublicacion(@Path("id") id: Long): Response<ApiResponse<PublicacionAlimentoResponse>>
    @PATCH("api/admin/publicaciones/{id}/bloquear") suspend fun bloquearPublicacion(@Path("id") id: Long, @Query("motivo") motivo: String): Response<ApiResponse<PublicacionAlimentoResponse>>

    @POST("api/receptor/solicitudes") suspend fun solicitarDonacion(@Body request: SolicitudDonacionRequest): Response<ApiResponse<SolicitudDonacionResponse>>
    @GET("api/receptor/solicitudes") suspend fun misSolicitudes(): Response<ApiResponse<List<SolicitudDonacionResponse>>>
    @PATCH("api/receptor/solicitudes/{id}/cancelar") suspend fun cancelarSolicitud(@Path("id") id: Long): Response<ApiResponse<SolicitudDonacionResponse>>
    @GET("api/donante/solicitudes") suspend fun solicitudesRecibidas(): Response<ApiResponse<List<SolicitudDonacionResponse>>>
    @PATCH("api/donante/solicitudes/{id}/aceptar") suspend fun aceptarSolicitud(@Path("id") id: Long): Response<ApiResponse<SolicitudDonacionResponse>>
    @PATCH("api/donante/solicitudes/{id}/rechazar") suspend fun rechazarSolicitud(@Path("id") id: Long, @Query("observacion") observacion: String? = null): Response<ApiResponse<SolicitudDonacionResponse>>
    @PATCH("api/solicitudes/{id}/confirmar-entrega") suspend fun confirmarEntrega(@Path("id") id: Long): Response<ApiResponse<SolicitudDonacionResponse>>

    @GET("api/admin/dashboard/resumen") suspend fun dashboardResumen(): Response<ApiResponse<DashboardResumenResponse>>
    @GET("api/admin/usuarios") suspend fun usuarios(): Response<ApiResponse<List<UsuarioResponse>>>
    @PATCH("api/admin/usuarios/{id}/estado") suspend fun cambiarEstadoUsuario(@Path("id") id: Long, @Body body: Map<String, String>): Response<ApiResponse<UsuarioResponse>>
    @GET("api/admin/reportes") suspend fun reportes(): Response<ApiResponse<List<ReporteResponse>>>
    @GET("api/historial/operaciones") suspend fun historial(): Response<ApiResponse<List<HistorialOperacionResponse>>>
}
