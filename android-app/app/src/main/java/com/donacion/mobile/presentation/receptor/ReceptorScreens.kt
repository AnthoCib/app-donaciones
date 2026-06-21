package com.donacion.mobile.presentation.receptor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.donacion.mobile.core.ui.UiState
import com.donacion.mobile.presentation.common.*
import java.math.BigDecimal

@Composable fun ReceptorHomeScreen(goAlimentos: () -> Unit, goSolicitudes: () -> Unit) { AppScaffold("Home Receptor") { Column(Modifier.padding(this).padding(16.dp)) { Button(goAlimentos, Modifier.fillMaxWidth()) { Text("Ver alimentos disponibles") }; Button(goSolicitudes, Modifier.fillMaxWidth()) { Text("Mis solicitudes") } } } }
@Composable fun AlimentosDisponiblesScreen(vm: ReceptorViewModel, onDetalle: (Long) -> Unit) { var distrito by remember { mutableStateOf("") }; LaunchedEffect(Unit) { vm.disponibles() }; val state by vm.alimentos.collectAsState(); AppScaffold("Alimentos disponibles") { Column(Modifier.padding(this)) { Row(Modifier.padding(8.dp)) { OutlinedTextField(distrito, { distrito = it }, label = { Text("Distrito") }, modifier = Modifier.weight(1f)); Button({ vm.disponibles(distrito, null) }) { Text("Filtrar") } }; StateContent(state) { items -> LazyColumn { items(items) { p -> PublicationCard(p, onClick = { onDetalle(p.idPublicacion) }) } } } } } }
@Composable fun DetalleAlimentoScreen(vm: ReceptorViewModel, id: Long) { var cantidad by remember { mutableStateOf("1") }; var mensaje by remember { mutableStateOf("Solicito esta donación") }; LaunchedEffect(id) { vm.detalle(id) }; val state by vm.detalle.collectAsState(); AppScaffold("Detalle alimento") { StateContent(state) { p -> Column(Modifier.padding(this).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) { PublicationCard(p); OutlinedTextField(cantidad, { cantidad = it }, label = { Text("Cantidad") }); OutlinedTextField(mensaje, { mensaje = it }, label = { Text("Mensaje") }); Button({ vm.solicitar(id, BigDecimal(cantidad), mensaje) }, Modifier.fillMaxWidth()) { Text("Solicitar donación") } } } } }
@Composable fun MisSolicitudesScreen(vm: ReceptorViewModel) { LaunchedEffect(Unit) { vm.misSolicitudes() }; val state by vm.solicitudes.collectAsState(); AppScaffold("Mis solicitudes") { StateContent(state) { items -> LazyColumn(Modifier.padding(this)) { items(items) { s -> SolicitudCard(s) { OutlinedButton({ vm.cancelar(s.idSolicitud) }) { Text("Cancelar") }; Button({ vm.confirmarRecepcion(s.idSolicitud) }) { Text("Confirmar recepción") } } } } } } }
