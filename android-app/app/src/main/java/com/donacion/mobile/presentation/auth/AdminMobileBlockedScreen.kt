package com.donacion.mobile.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdminMobileBlockedScreen(onLogout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Acceso administrativo", style = MaterialTheme.typography.headlineSmall)
        Text("El administrador debe usar el panel web.", modifier = Modifier.padding(vertical = 16.dp))
        Button(onClick = onLogout) { Text("Cerrar sesión") }
    }
}
