package com.cuarteldelascofias.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier

private val DarkColorScheme = darkColorScheme(
    primary = DarkPurple,
    secondary = DarkOrange,
    tertiary = DarkTurquoise,
    background = BrandPurpleDark,
    surface = DarkSurface,
    onPrimary = BrandPurpleDark,
    onSecondary = BrandPurpleDark,
    onTertiary = BrandPurpleDark,
    onBackground = BrandOnDark,
    onSurface = BrandOnDark
)

private val LightColorScheme = lightColorScheme(
    primary = BrandPurple,
    secondary = BrandOrange,
    tertiary = BrandTurquoise,
    background = BrandCream,
    surface = BrandSurface,
    onPrimary = BrandSurface,
    onSecondary = BrandOnLight,
    onTertiary = BrandOnLight,
    onBackground = BrandOnLight,
    onSurface = BrandOnLight
)

@Composable
fun CuartelDeLasCofiasTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorScheme.background
        ) {
            content()
        }
    }
}
