package com.donacion.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.donacion.mobile.core.ui.RedDonacionTheme
import com.donacion.mobile.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { RedDonacionTheme { AppNavigation() } }
    }
}
