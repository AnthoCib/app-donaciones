package com.donacion.mobile.presentation.donante

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.donacion.mobile.data.dto.PublicacionAlimentoRequest
import com.donacion.mobile.presentation.common.*
import java.math.BigDecimal

@Composable fun DonanteHomeScreen(vm: DonanteViewModel, goPublicar: () -> Unit, goPublicaciones: () -> Unit, goSolicitudes: () -> Unit, goHistorial: () -> Unit) {
    LaunchedEffect(Unit) { vm.cargar() }
    AppScaffold("Home Donante") { Column(Modifier.padding(this).padding(12.dp)) {
        Button(onClick = goPublicar, modifier = Modifier.fillMaxWidth()) { Text("Publicar alimento") }
        Button(onClick = goPublicaciones, modifier = Modifier.fillMaxWidth()) { Text("Mis publicaciones") }
        Button(onClick = goSolicitudes, modifier = Modifier.fillMaxWidth()) { Text("Solicitudes recibidas") }
        Button(onClick = goHistorial, modifier = Modifier.fillMaxWidth()) { Text("Historial de donaciones") }
        MetricCard("Publicaciones", "Ver detalle")
        MetricCard("Solicitudes pendientes", "Gestionar")
    } }
}

@Composable fun PublicarAlimentoScreen(vm: DonanteViewModel, onDone: () -> Unit) {
    var nombre by remember { mutableStateOf("") }; var categoria by remember { mutableStateOf("1") }; var cantidad by remember { mutableStateOf("1") }; var unidad by remember { mutableStateOf("UNIDAD") }; var fecha by remember { mutableStateOf("2026-12-31T18:00:00") }
    var descripcion by remember { mutableStateOf("") }; var imagen by remember { mutableStateOf("") }; var direccion by remember { mutableStateOf("") }; var distrito by remember { mutableStateOf("") }; var lat by remember { mutableStateOf("0") }; var lon by remember { mutableStateOf("0") }
    AppScaffold("Publicar alimento") { Column(Modifier.padding(this).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre alimento") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(categoria, { categoria = it }, label = { Text("ID categoría") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(cantidad, { cantidad = it }, label = { Text("Cantidad disponible") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(unidad, { unidad = it.uppercase() }, label = { Text("Unidad medida") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(fecha, { fecha = it }, label = { Text("Fecha vencimiento ISO") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(descripcion, { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(imagen, { imagen = it }, label = { Text("Imagen URL") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(direccion, { direccion = it }, label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(distrito, { distrito = it }, label = { Text("Distrito") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(lat, { lat = it }, label = { Text("Latitud") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(lon, { lon = it }, label = { Text("Longitud") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = { vm.publicar(PublicacionAlimentoRequest(categoria.toLong(), nombre, descripcion, cantidad.toBigDecimal(), unidad, fecha, imagen, distrito, direccion, lat.toBigDecimal(), lon.toBigDecimal()), onDone) }, modifier = Modifier.fillMaxWidth()) { Text("Publicar") }
    } }
}

@Composable fun MisPublicacionesScreen(vm: DonanteViewModel) { LaunchedEffect(Unit) { vm.misPublicaciones() }; val state by vm.publicaciones.collectAsState(); AppScaffold("Mis publicaciones") { StateContent(state) { items -> LazyColumn(Modifier.padding(this)) { items(items) { p -> PublicationCard(p, actions = { Button({ vm.confirmarDisponibilidad(p.idPublicacion) }) { Text("Disponible") } }) } } } } }
@Composable fun SolicitudesRecibidasScreen(vm: DonanteViewModel) { LaunchedEffect(Unit) { vm.solicitudesRecibidas() }; val state by vm.solicitudes.collectAsState(); AppScaffold("Solicitudes recibidas") { StateContent(state) { items -> LazyColumn(Modifier.padding(this)) { items(items) { s -> SolicitudCard(s) { Button({ vm.aceptar(s.idSolicitud) }) { Text("Aceptar") }; OutlinedButton({ vm.rechazar(s.idSolicitud) }) { Text("Rechazar") }; Button({ vm.confirmarEntrega(s.idSolicitud) }) { Text("Entrega") } } } } } } }
@Composable fun HistorialScreen(vm: DonanteViewModel) { LaunchedEffect(Unit) { vm.historial() }; val state by vm.historial.collectAsState(); AppScaffold("Historial") { StateContent(state) { items -> LazyColumn(Modifier.padding(this)) { items(items) { Text("${it.tipo}: ${it.descripcion}", Modifier.padding(12.dp)) } } } } }
