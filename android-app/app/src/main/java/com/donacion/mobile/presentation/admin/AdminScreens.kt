package com.donacion.mobile.presentation.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.donacion.mobile.presentation.common.*

@Composable fun AdminDashboardScreen(vm: AdminViewModel, goUsuarios: () -> Unit, goPublicaciones: () -> Unit) { LaunchedEffect(Unit) { vm.cargar() }; val state by vm.dashboard.collectAsState(); AppScaffold("Dashboard Admin") { Column(Modifier.padding(this)) { StateContent(state) { d -> Column { MetricCard("Alimentos donados", d.cantidadAlimentosDonados.toString()); MetricCard("Kilos entregados", d.kilosEntregados.toString()); MetricCard("Personas beneficiadas", d.personasBeneficiadas.toString()); MetricCard("Distritos impactados", d.distritosImpactados.toString()); MetricCard("Donantes activos", d.donantesActivos.toString()); MetricCard("Reservas realizadas", d.reservasRealizadas.toString()) } }; Button(goUsuarios, Modifier.fillMaxWidth().padding(8.dp)) { Text("Gestión de usuarios") }; Button(goPublicaciones, Modifier.fillMaxWidth().padding(8.dp)) { Text("Gestión de publicaciones") } } } }
@Composable fun GestionUsuariosScreen(vm: AdminViewModel) { LaunchedEffect(Unit) { vm.usuarios() }; val state by vm.usuarios.collectAsState(); AppScaffold("Usuarios") { StateContent(state) { items -> LazyColumn(Modifier.padding(this)) { items(items) { u -> Card(Modifier.fillMaxWidth().padding(8.dp)) { Column(Modifier.padding(12.dp)) { Text("${u.nombres} ${u.apellidos}"); Text("${u.correo} • ${u.rol} • ${u.estado}"); Button({ vm.bloquearUsuario(u.idUsuario) }) { Text("Bloquear") } } } } } } } }
@Composable fun GestionPublicacionesAdminScreen(vm: AdminViewModel) { LaunchedEffect(Unit) { vm.publicaciones() }; val state by vm.publicaciones.collectAsState(); AppScaffold("Publicaciones Admin") { StateContent(state) { items -> LazyColumn(Modifier.padding(this)) { items(items) { p -> PublicationCard(p) { Button({ vm.aprobar(p.idPublicacion) }) { Text("Aprobar") }; OutlinedButton({ vm.bloquearPublicacion(p.idPublicacion) }) { Text("Bloquear") } } } } } } }
