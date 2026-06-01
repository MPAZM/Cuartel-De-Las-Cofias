package com.cuarteldelascofias.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cuarteldelascofias.app.core.common.AppContainer
import com.cuarteldelascofias.app.ui.theme.CuartelDeLasCofiasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppContainer.initialize(applicationContext)
        setContent {
            CuartelDeLasCofiasTheme {
                CuartelDeLasCofiasApp()
            }
        }
    }
}
