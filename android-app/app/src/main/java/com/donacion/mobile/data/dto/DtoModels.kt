package com.donacion.mobile.data.dto

import java.math.BigDecimal

data class ApiResponse<T>(val success: Boolean, val mensaje: String?, val data: T?)
data class LoginRequest(val correo: String, val password: String)
data class LoginResponse(val idUsuario: Long, val nombres: String, val apellidos: String, val correo: String, val rol: String, val tipoEntidad: String?, val token: String, val tipoToken: String?)
data class RegistroRequest(val nombres: String, val apellidos: String, val correo: String, val password: String, val telefono: String?, val rol: String, val tipoEntidad: String, val distrito: String, val direccion: String?, val latitud: BigDecimal?, val longitud: BigDecimal?)
data class UsuarioResponse(val idUsuario: Long, val nombres: String, val apellidos: String, val correo: String, val telefono: String?, val rol: String, val tipoEntidad: String?, val distrito: String?, val direccion: String?, val estado: String?)
data class PublicacionAlimentoRequest(val idCategoria: Long, val nombreAlimento: String, val descripcion: String?, val cantidadDisponible: BigDecimal, val unidadMedida: String, val fechaVencimiento: String, val imagenUrl: String?, val distrito: String, val direccion: String, val latitud: BigDecimal, val longitud: BigDecimal)
data class PublicacionAlimentoResponse(val idPublicacion: Long, val codigo: String?, val idDonante: Long?, val donante: String?, val idCategoria: Long?, val categoria: String?, val nombreAlimento: String, val descripcion: String?, val cantidadDisponible: BigDecimal?, val unidadMedida: String?, val fechaVencimiento: String?, val imagenUrl: String?, val distrito: String?, val direccion: String?, val latitud: BigDecimal?, val longitud: BigDecimal?, val estado: String?, val fechaPublicacion: String?, val motivoObservacion: String?)
data class SolicitudDonacionRequest(val idPublicacion: Long, val motivo: String, val cantidadSolicitada: BigDecimal, val personasBeneficiadas: Int)
data class SolicitudDonacionResponse(val idSolicitud: Long, val codigo: String?, val idPublicacion: Long?, val alimento: String?, val idReceptor: Long?, val receptor: String?, val motivo: String?, val cantidadSolicitada: BigDecimal?, val personasBeneficiadas: Int?, val estado: String?, val fechaSolicitud: String?, val fechaRespuesta: String?, val observacionRespuesta: String?)
data class CategoriaAlimentoResponse(val idCategoria: Long, val nombre: String, val estado: Boolean?)
data class DashboardResumenResponse(val cantidadAlimentosDonados: Long, val kilosEntregados: BigDecimal, val personasBeneficiadas: Int, val distritosImpactados: Long, val donantesActivos: Long, val reservasRealizadas: Long)
data class ReporteResponse(val idReporte: Long?, val codigo: String?, val asunto: String?, val descripcion: String?, val estado: String?)
data class HistorialOperacionResponse(val idHistorial: Long, val tipo: String, val descripcion: String, val fechaOperacion: String?)
