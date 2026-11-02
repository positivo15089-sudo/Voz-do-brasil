package br.com.vozdobrasil.studio.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val StudioBlack = Color(0xFF09090B)
val StudioGraphite = Color(0xFF141418)
val StudioSurface = Color(0xFF1E1E24)
val StudioGold = Color(0xFFD4AF37)
val StudioBrightGold = Color(0xFFFFD700)
val StudioAmber = Color(0xFFF59E0B)
val StudioTextPrimary = Color(0xFFF8FAFC)
val StudioTextSecondary = Color(0xFF94A3B8)

private val VozDoBrasilColorScheme = darkColorScheme(
    primary = StudioGold,
    onPrimary = StudioBlack,
    secondary = StudioBrightGold,
    onSecondary = StudioBlack,
    tertiary = StudioAmber,
    background = StudioBlack,
    onBackground = StudioTextPrimary,
    surface = StudioGraphite,
    onSurface = StudioTextPrimary,
    surfaceVariant = StudioSurface,
    onSurfaceVariant = StudioTextSecondary
)

@Composable
fun VozDoBrasilTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = VozDoBrasilColorScheme,
        content = content
    )
}
