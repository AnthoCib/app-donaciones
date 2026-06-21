package com.donacion.mobile.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.donacion.mobile.core.ui.UiState
import com.donacion.mobile.data.dto.*

@Composable fun AppScaffold(title: String, actions: @Composable RowScope.() -> Unit = {}, content: @Composable PaddingValues.() -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text(title) }, actions = actions) }) { padding -> padding.content() }
}

@Composable fun <T> StateContent(state: UiState<T>, empty: String = "No hay datos", content: @Composable (T) -> Unit) = when (state) {
    UiState.Idle, UiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    is UiState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(state.message, color = MaterialTheme.colorScheme.error) }
    is UiState.Success -> if (state.data is Collection<*> && state.data.isEmpty()) Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(empty) } else content(state.data)
}

@Composable fun StatusBadge(text: String?) { AssistChip(onClick = {}, label = { Text(text ?: "SIN ESTADO") }) }

@Composable fun PublicationCard(item: PublicacionAlimentoResponse, onClick: () -> Unit = {}, actions: @Composable RowScope.() -> Unit = {}) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            if (!item.imagenUrl.isNullOrBlank()) AsyncImage(model = item.imagenUrl, contentDescription = item.nombreAlimento, modifier = Modifier.fillMaxWidth().height(130.dp))
            Text(item.nombreAlimento, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("${item.categoria ?: "Categoría"} • ${item.cantidadDisponible ?: ""} ${item.unidadMedida ?: ""}")
            Text("Vence: ${item.fechaVencimiento ?: "-"} • ${item.distrito ?: "-"}")
            StatusBadge(item.estado)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), content = actions)
        }
    }
}

@Composable fun SolicitudCard(item: SolicitudDonacionResponse, actions: @Composable RowScope.() -> Unit = {}) {
    Card(Modifier.fillMaxWidth().padding(8.dp)) { Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(item.alimento ?: "Solicitud", fontWeight = FontWeight.Bold)
        Text("Cantidad: ${item.cantidadSolicitada ?: "-"} • Personas: ${item.personasBeneficiadas ?: "-"}")
        Text(item.motivo ?: "Sin mensaje")
        StatusBadge(item.estado)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), content = actions)
    } }
}

@Composable fun MetricCard(title: String, value: String) { Card(Modifier.padding(8.dp).fillMaxWidth()) { Column(Modifier.padding(16.dp)) { Text(title); Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold) } } }
