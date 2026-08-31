package com.miniminuta.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miniminuta.app.ui.components.AnchoMaximoFormulario
import com.miniminuta.app.ui.components.BotonPrimario
import com.miniminuta.app.ui.components.CampoTexto
import com.miniminuta.app.ui.components.EncabezadoApp
import com.miniminuta.app.ui.components.GrillaInformativa
import com.miniminuta.app.ui.components.ItemInformativo
import com.miniminuta.app.ui.components.TablaDatos
import com.miniminuta.app.ui.components.Vinculo
import com.miniminuta.app.ui.theme.MiniMinutaTheme
import com.miniminuta.app.ui.components.TituloSeccion
import com.miniminuta.app.util.Validaciones
import kotlinx.coroutines.launch

/** Credenciales de demostración, ya que esta entrega no contempla servidor. */
private const val EMAIL_DEMO = "maria@correo.com"
private const val PASSWORD_DEMO = "minuta123"

/** Contenido de la grilla de bienvenida que se muestra bajo el formulario. */
private val BENEFICIOS = listOf(
    ItemInformativo("📅", "5 recetas", "Una para cada día de la semana"),
    ItemInformativo("🥕", "Ingredientes", "La lista completa de cada plato"),
    ItemInformativo("👩‍🍳", "Paso a paso", "Instrucciones simples y ordenadas"),
    ItemInformativo("💚", "Aporte nutricional", "Calorías y recomendación de cada receta")
)

/**
 * Pantalla de inicio de sesión.
 *
 * Recibe las acciones de navegación por parámetro para poder previsualizarse y
 * probarse sin depender del NavController.
 */
@Composable
fun LoginScreen(
    onIngresar: () -> Unit,
    onIrARegistro: () -> Unit,
    onIrARecuperar: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var recordarCorreo by rememberSaveable { mutableStateOf(false) }
    var intentoEnvio by rememberSaveable { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val errorEmail = if (intentoEnvio) Validaciones.errorEmail(email) else null
    val errorPassword = if (intentoEnvio) Validaciones.errorPassword(password) else null

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.widthIn(max = AnchoMaximoFormulario),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                EncabezadoApp(
                    titulo = "MiniMinuta",
                    subtitulo = "Tu minuta de recetas para toda la semana"
                )

                Spacer(Modifier.height(4.dp))

                CampoTexto(
                    valor = email,
                    onValorCambia = { email = it },
                    etiqueta = "Correo electrónico",
                    icono = Icons.Filled.Email,
                    ayuda = "Ejemplo: nombre@correo.com",
                    error = errorEmail,
                    tecladoTipo = KeyboardType.Email
                )

                CampoTexto(
                    valor = password,
                    onValorCambia = { password = it },
                    etiqueta = "Contraseña",
                    icono = Icons.Filled.Lock,
                    error = errorPassword,
                    esPassword = true,
                    imeAction = ImeAction.Done
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = recordarCorreo,
                        onCheckedChange = { recordarCorreo = it }
                    )
                    Text(
                        text = "Recordar mi correo",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                BotonPrimario(
                    texto = "Ingresar",
                    icono = Icons.AutoMirrored.Filled.Login,
                    onClick = {
                        intentoEnvio = true
                        val datosCorrectos = Validaciones.errorEmail(email) == null &&
                            Validaciones.errorPassword(password) == null
                        when {
                            !datosCorrectos -> Unit
                            email.trim().equals(EMAIL_DEMO, ignoreCase = true) &&
                                password == PASSWORD_DEMO -> onIngresar()

                            else -> scope.launch {
                                snackbarHostState.showSnackbar(
                                    "El correo o la contraseña no coinciden. Revisa los datos."
                                )
                            }
                        }
                    }
                )

                Vinculo(
                    texto = "¿Olvidaste tu contraseña?",
                    onClick = onIrARecuperar,
                    modifier = Modifier.fillMaxWidth()
                )

                Vinculo(
                    texto = "¿No tienes cuenta? Crear una cuenta",
                    onClick = onIrARegistro,
                    modifier = Modifier.fillMaxWidth()
                )

                TituloSeccion("¿Qué encontrarás en la aplicación?")

                GrillaInformativa(items = BENEFICIOS)

                TituloSeccion("Datos de prueba")

                Text(
                    text = "Solo para la revisión de esta entrega. Una aplicación real " +
                        "nunca debe mostrar contraseñas en pantalla.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                TablaDatos(
                    encabezadoIzquierdo = "Dato",
                    encabezadoDerecho = "Valor",
                    filas = listOf(
                        "Correo" to EMAIL_DEMO,
                        "Contraseña" to PASSWORD_DEMO
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    MiniMinutaTheme {
        LoginScreen(onIngresar = {}, onIrARegistro = {}, onIrARecuperar = {})
    }
}
