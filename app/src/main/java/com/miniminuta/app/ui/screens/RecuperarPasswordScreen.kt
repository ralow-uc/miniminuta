package com.miniminuta.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.miniminuta.app.ui.components.GrillaSeleccionUnica
import com.miniminuta.app.ui.components.TablaDatos
import com.miniminuta.app.ui.components.TituloSeccion
import com.miniminuta.app.ui.components.Vinculo
import com.miniminuta.app.ui.theme.MiniMinutaTheme
import com.miniminuta.app.util.Validaciones

/** Formas de recibir el enlace de recuperación. */
private val MEDIOS_ENVIO = listOf("Por correo electrónico", "Por mensaje de texto")

/**
 * Pantalla de recuperación de contraseña.
 *
 * Valida el correo y confirma el envío con un diálogo. No hay servidor detrás,
 * por lo que el envío es simulado.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecuperarPasswordScreen(
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by rememberSaveable { mutableStateOf("") }
    var medioElegido by rememberSaveable { mutableStateOf(MEDIOS_ENVIO.first()) }
    var intentoEnvio by rememberSaveable { mutableStateOf(false) }
    var mostrarConfirmacion by rememberSaveable { mutableStateOf(false) }

    val errorEmail = if (intentoEnvio) Validaciones.errorEmail(email) else null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recuperar contraseña") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver al inicio de sesión"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.widthIn(max = AnchoMaximoFormulario),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "No te preocupes. Escribe tu correo y te enviaremos los " +
                            "pasos para crear una contraseña nueva.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                CampoTexto(
                    valor = email,
                    onValorCambia = { email = it },
                    etiqueta = "Correo electrónico",
                    icono = Icons.Filled.Email,
                    ayuda = "El mismo correo con el que creaste tu cuenta",
                    error = errorEmail,
                    tecladoTipo = KeyboardType.Email,
                    imeAction = ImeAction.Done
                )

                TituloSeccion("¿Cómo quieres recibir las instrucciones?")
                GrillaSeleccionUnica(
                    opciones = MEDIOS_ENVIO,
                    seleccionada = medioElegido,
                    etiqueta = { it },
                    onSelecciona = { medioElegido = it }
                )

                TituloSeccion("Qué va a pasar")
                TablaDatos(
                    encabezadoIzquierdo = "Paso",
                    encabezadoDerecho = "Qué ocurre",
                    filas = listOf(
                        "1" to "Revisas tu correo",
                        "2" to "Abres el enlace que te enviamos",
                        "3" to "Escribes tu contraseña nueva",
                        "4" to "Ingresas con la contraseña nueva"
                    )
                )

                BotonPrimario(
                    texto = "Enviar instrucciones",
                    icono = Icons.AutoMirrored.Filled.Send,
                    onClick = {
                        intentoEnvio = true
                        if (Validaciones.errorEmail(email) == null) {
                            mostrarConfirmacion = true
                        }
                    }
                )

                Vinculo(
                    texto = "Volver al inicio de sesión",
                    onClick = onVolver,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            title = { Text("Instrucciones enviadas") },
            text = {
                Text(
                    text = "Enviamos los pasos a ${email.trim()} " +
                        "(${medioElegido.lowercase()}). Revisa tu bandeja de entrada.",
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarConfirmacion = false
                        onVolver()
                    }
                ) {
                    Text("Entendido")
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RecuperarPasswordScreenPreview() {
    MiniMinutaTheme {
        RecuperarPasswordScreen(onVolver = {})
    }
}
