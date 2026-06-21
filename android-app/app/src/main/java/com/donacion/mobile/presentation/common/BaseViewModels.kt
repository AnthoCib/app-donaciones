package com.donacion.mobile.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.donacion.mobile.core.network.NetworkResult
import com.donacion.mobile.core.session.UserSession
import com.donacion.mobile.core.ui.UiState
import com.donacion.mobile.data.dto.*
import com.donacion.mobile.data.repository.DonationRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal

class AuthViewModel(private val repo: DonationRepository) : ViewModel() {
    val session = repo.sessionFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
    private val _state = MutableStateFlow<UiState<LoginResponse>>(UiState.Idle); val state = _state.asStateFlow()
    fun login(correo: String, password: String) = viewModelScope.launch { run(_state) { repo.login(correo, password) } }
    fun register(request: RegistroRequest) = viewModelScope.launch { run(_state) { repo.register(request) } }
    fun logout() = viewModelScope.launch { repo.logout() }
}

class DonanteViewModel(private val repo: DonationRepository) : ViewModel() {
    val publicaciones = MutableStateFlow<UiState<List<PublicacionAlimentoResponse>>>(UiState.Idle)
    val solicitudes = MutableStateFlow<UiState<List<SolicitudDonacionResponse>>>(UiState.Idle)
    val historial = MutableStateFlow<UiState<List<HistorialOperacionResponse>>>(UiState.Idle)
    fun cargar() { misPublicaciones(); solicitudesRecibidas(); historial() }
    fun misPublicaciones() = viewModelScope.launch { run(publicaciones) { repo.misPublicaciones() } }
    fun solicitudesRecibidas() = viewModelScope.launch { run(solicitudes) { repo.solicitudesRecibidas() } }
    fun publicar(r: PublicacionAlimentoRequest, done: () -> Unit) = viewModelScope.launch { if (repo.crearPublicacion(r) is NetworkResult.Success) { misPublicaciones(); done() } }
    fun confirmarDisponibilidad(id: Long) = viewModelScope.launch { repo.confirmarDisponibilidad(id); misPublicaciones() }
    fun aceptar(id: Long) = viewModelScope.launch { repo.aceptarSolicitud(id); solicitudesRecibidas() }
    fun rechazar(id: Long) = viewModelScope.launch { repo.rechazarSolicitud(id, "Rechazado desde app móvil"); solicitudesRecibidas() }
    fun confirmarEntrega(id: Long) = viewModelScope.launch { repo.confirmarEntrega(id); solicitudesRecibidas() }
    fun historial() = viewModelScope.launch { run(historial) { repo.historial() } }
}

class ReceptorViewModel(private val repo: DonationRepository) : ViewModel() {
    val alimentos = MutableStateFlow<UiState<List<PublicacionAlimentoResponse>>>(UiState.Idle)
    val solicitudes = MutableStateFlow<UiState<List<SolicitudDonacionResponse>>>(UiState.Idle)
    val detalle = MutableStateFlow<UiState<PublicacionAlimentoResponse>>(UiState.Idle)
    fun disponibles(distrito: String? = null, categoria: Long? = null) = viewModelScope.launch { run(alimentos) { repo.disponibles(distrito?.ifBlank { null }, categoria) } }
    fun cercanas(lat: BigDecimal, lon: BigDecimal, radio: Double) = viewModelScope.launch { run(alimentos) { repo.cercanas(lat, lon, radio) } }
    fun detalle(id: Long) = viewModelScope.launch { run(detalle) { repo.detallePublicacion(id) } }
    fun solicitar(id: Long, cantidad: BigDecimal, mensaje: String) = viewModelScope.launch { repo.solicitar(SolicitudDonacionRequest(id, mensaje, cantidad, 1)); misSolicitudes() }
    fun misSolicitudes() = viewModelScope.launch { run(solicitudes) { repo.misSolicitudes() } }
    fun cancelar(id: Long) = viewModelScope.launch { repo.cancelarSolicitud(id); misSolicitudes() }
    fun confirmarRecepcion(id: Long) = viewModelScope.launch { repo.confirmarEntrega(id); misSolicitudes() }
}

private suspend fun <T> run(state: MutableStateFlow<UiState<T>>, block: suspend () -> NetworkResult<T>) {
    state.value = UiState.Loading
    state.value = when (val r = block()) { is NetworkResult.Success -> UiState.Success(r.data); is NetworkResult.Error -> UiState.Error(r.message) }
}

class VmFactory(private val repo: DonationRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST") override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        AuthViewModel::class.java -> AuthViewModel(repo)
        DonanteViewModel::class.java -> DonanteViewModel(repo)
        ReceptorViewModel::class.java -> ReceptorViewModel(repo)
        else -> error("ViewModel no soportado")
    } as T
}
