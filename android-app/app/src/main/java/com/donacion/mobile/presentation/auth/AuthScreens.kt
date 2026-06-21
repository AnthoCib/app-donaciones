package com.donacion.mobile.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.donacion.mobile.core.ui.UiState
import com.donacion.mobile.data.dto.RegistroRequest
import com.donacion.mobile.presentation.common.AuthViewModel
import java.math.BigDecimal

@Composable fun SplashScreen(sessionRole: String?, onNavigate: (String) -> Unit) { LaunchedEffect(sessionRole) { onNavigate(if (sessionRole.isNullOrBlank()) "login" else sessionRole.lowercase()) }; Box(Modifier.fillMaxSize()) { CircularProgressIndicator() } }

@Composable fun LoginScreen(vm: AuthViewModel, onRegister: () -> Unit, onForgot: () -> Unit) {
    var correo by remember { mutableStateOf("") }; var password by remember { mutableStateOf("") }
    val state by vm.state.collectAsState()
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text("Red de Donación", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(correo, { correo = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(password, { password = it }, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Button(onClick = { vm.login(correo, password) }, modifier = Modifier.fillMaxWidth()) { Text("Iniciar sesión") }
        TextButton(onClick = onRegister) { Text("Crear cuenta") }
        TextButton(onClick = onForgot) { Text("Recuperar contraseña") }
        if (state is UiState.Loading) LinearProgressIndicator(Modifier.fillMaxWidth())
        if (state is UiState.Error) Text((state as UiState.Error).message, color = MaterialTheme.colorScheme.error)
    }
}

@Composable fun RegisterScreen(vm: AuthViewModel, onBack: () -> Unit) {
    var nombres by remember { mutableStateOf("") }; var apellidos by remember { mutableStateOf("") }; var correo by remember { mutableStateOf("") }; var pass by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }; var rol by remember { mutableStateOf("RECEPTOR") }; var tipo by remember { mutableStateOf("PERSONA") }; var distrito by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Registro", style = MaterialTheme.typography.headlineMedium)
        listOf("DONANTE", "RECEPTOR").forEach { FilterChip(selected = rol == it, onClick = { rol = it }, label = { Text(it) }) }
        OutlinedTextField(nombres, { nombres = it }, label = { Text("Nombres") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(apellidos, { apellidos = it }, label = { Text("Apellidos") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(correo, { correo = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(pass, { pass = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        OutlinedTextField(telefono, { telefono = it }, label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(tipo, { tipo = it.uppercase() }, label = { Text("Tipo entidad") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(distrito, { distrito = it }, label = { Text("Distrito") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = { vm.register(RegistroRequest(nombres, apellidos, correo, pass, telefono, rol, tipo, distrito, null, BigDecimal.ZERO, BigDecimal.ZERO)) }, modifier = Modifier.fillMaxWidth()) { Text("Registrarme") }
        TextButton(onClick = onBack) { Text("Volver") }
    }
}

@Composable fun ForgotPasswordScreen(onBack: () -> Unit) { Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) { Text("Recuperación básica"); Text("Contacta al administrador o solicita restablecimiento de contraseña."); Button(onClick = onBack) { Text("Volver") } } }
