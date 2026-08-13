package com.miniminuta.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EsquemaClaro = lightColorScheme(
    primary = VerdeHoja,
    onPrimary = SuperficieCrema,
    primaryContainer = VerdeHojaClaro,
    onPrimaryContainer = VerdeSobreClaro,
    secondary = NaranjaZanahoria,
    onSecondary = SuperficieCrema,
    secondaryContainer = NaranjaClaro,
    onSecondaryContainer = NaranjaSobreClaro,
    background = FondoCrema,
    onBackground = TextoOscuro,
    surface = SuperficieCrema,
    onSurface = TextoOscuro,
    surfaceVariant = GrisVerdoso,
    onSurfaceVariant = TextoSuave,
    outline = BordeSuave,
    error = RojoError,
    onError = SuperficieCrema,
    errorContainer = RojoErrorContenedor,
    onErrorContainer = RojoError
)

private val EsquemaOscuro = darkColorScheme(
    primary = VerdeHojaOscuro,
    onPrimary = VerdeSobreClaro,
    primaryContainer = VerdeContenedorOscuro,
    onPrimaryContainer = VerdeHojaClaro,
    secondary = NaranjaOscuro,
    onSecondary = NaranjaSobreClaro,
    secondaryContainer = NaranjaContenedorOscuro,
    onSecondaryContainer = NaranjaClaro,
    background = FondoNoche,
    onBackground = TextoClaro,
    surface = SuperficieNoche,
    onSurface = TextoClaro,
    surfaceVariant = SuperficieNoche,
    onSurfaceVariant = BordeOscuro,
    outline = BordeOscuro,
    error = RojoErrorOscuro,
    onError = RojoError
)

/**
 * Tema de la aplicación. Sigue el modo claro u oscuro del sistema.
 */
@Composable
fun MiniMinutaTheme(
    modoOscuro: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (modoOscuro) EsquemaOscuro else EsquemaClaro,
        typography = Tipografia,
        content = content
    )
}
