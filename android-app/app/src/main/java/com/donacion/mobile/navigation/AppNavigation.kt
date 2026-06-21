package com.donacion.mobile.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.donacion.mobile.di.ContainerProvider
import com.donacion.mobile.presentation.auth.*
import com.donacion.mobile.presentation.common.*
import com.donacion.mobile.presentation.donante.*
import com.donacion.mobile.presentation.receptor.*

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val container = remember { ContainerProvider.get(context) }
    val factory = remember { VmFactory(container.repository) }
    val auth: AuthViewModel = viewModel(factory = factory)
    val donante: DonanteViewModel = viewModel(factory = factory)
    val receptor: ReceptorViewModel = viewModel(factory = factory)
    val session by auth.session.collectAsState()
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = "splash") {
        composable("splash") { SplashScreen(session?.rol) { role -> nav.navigate(role.startRoute()) { popUpTo("splash") { inclusive = true } } } }
        composable("login") { LoginScreen(auth, onRegister = { nav.navigate("register") }, onForgot = { nav.navigate("forgot") }) }
        composable("register") { RegisterScreen(auth) { nav.popBackStack() } }
        composable("forgot") { ForgotPasswordScreen { nav.popBackStack() } }

        composable("donante") { DonanteHomeScreen(donante, { nav.navigate("publicar") }, { nav.navigate("misPublicaciones") }, { nav.navigate("solicitudesRecibidas") }, { nav.navigate("historialDonante") }) }
        composable("publicar") { PublicarAlimentoScreen(donante) { nav.popBackStack() } }
        composable("misPublicaciones") { MisPublicacionesScreen(donante) }
        composable("solicitudesRecibidas") { SolicitudesRecibidasScreen(donante) }
        composable("historialDonante") { HistorialScreen(donante) }

        composable("receptor") { ReceptorHomeScreen({ nav.navigate("alimentos") }, { nav.navigate("misSolicitudes") }) }
        composable("alimentos") { AlimentosDisponiblesScreen(receptor) { nav.navigate("detalle/$it") } }
        composable("detalle/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })) { DetalleAlimentoScreen(receptor, it.arguments!!.getLong("id")) }
        composable("misSolicitudes") { MisSolicitudesScreen(receptor) }

        composable("adminMobileBlocked") { AdminMobileBlockedScreen { auth.logout(); nav.navigate("login") { popUpTo(0) } } }
    }
}

private fun String.startRoute(): String = when (uppercase()) {
    "DONANTE" -> "donante"
    "RECEPTOR" -> "receptor"
    "ADMIN", "ADMINISTRADOR" -> "adminMobileBlocked"
    else -> "login"
}
