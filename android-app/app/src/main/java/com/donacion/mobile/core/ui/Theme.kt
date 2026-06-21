package com.donacion.mobile.core.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF2E7D32), secondary = Color(0xFFF9A825), tertiary = Color(0xFF00897B),
    background = Color(0xFFFFFBF2), surface = Color(0xFFFFFFFF), error = Color(0xFFC62828)
)

@Composable
fun RedDonacionTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = LightColors, typography = Typography(), content = content)
}
