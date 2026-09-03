package com.miniminuta.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miniminuta.app.data.CuentasRepository
import com.miniminuta.app.data.ResultadoRegistro
import com.miniminuta.app.ui.components.AnchoMaximoFormulario
import com.miniminuta.app.ui.components.BotonPrimario
import com.miniminuta.app.ui.components.CampoTexto
import com.miniminuta.app.ui.components.GrillaSeleccionMultiple
import com.miniminuta.app.ui.components.TablaDatos
import com.miniminuta.app.ui.components.TituloSeccion
import com.miniminuta.app.ui.components.Vinculo
import com.miniminuta.app.ui.theme.MiniMinutaTheme
import com.miniminuta.app.util.Validaciones
import kotlinx.coroutines.launch

/** Opciones del combo box de tipo de alimentación. */
private val TIPOS_DIETA = listOf(
    "Alimentación equilibrada",
    "Baja en calorías",
    "Vegetariana",
    "Sin azúcar añadida"
)

/** Opciones de los radio buttons de tamaño del hogar. */
private val TAMANOS_HOGAR = listOf("1 o 2 personas", "3 o 4 personas", "5 o más personas")

/** Opciones del check list de restricciones alimentarias. */
private val RESTRICCIONES = listOf(
    "Sin gluten",
    "Sin lactosa",
    "Sin frutos secos",
    "Bajo en sodio"
)

/**
 * Pantalla de registro de usuario.
 *
 * Reúne los componentes de entrada que pide la actividad: campos de texto,
 * combo box, radio buttons y check list.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    onRegistroCompleto: () -> Unit,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmacion by rememberSaveable { mutableStateOf("") }
    var tipoDieta by rememberSaveable { mutableStateOf(TIPOS_DIETA.first()) }
    var comboAbierto by remember { mutableStateOf(false) }
    var tamanoHogar by rememberSaveable { mutableStateOf(TAMANOS_HOGAR[1]) }
    var restriccionesElegidas by rememberSaveable { mutableStateOf(setOf<String>()) }
    var aceptaTerminos by rememberSaveable { mutableStateOf(false) }
    var intentoEnvio by rememberSaveable { mutableStateOf(false) }
    var mostrarExito by rememberSaveable { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val errorNombre = if (intentoEnvio) Validaciones.errorNombre(nombre) else null
    val errorEmail = if (intentoEnvio) Validaciones.errorEmail(email) else null
    val errorPassword = if (intentoEnvio) Validaciones.errorPassword(password) else null
    val errorConfirmacion = if (intentoEnvio) {
        Validaciones.errorConfirmacion(password, confirmacion)
    } else {
        null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear cuenta") },
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Text(
                    text = "Completa tus datos para armar tu minuta personalizada.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                TituloSeccion("Tus datos")

                CampoTexto(
                    valor = nombre,
                    onValorCambia = { nombre = it },
                    etiqueta = "Nombre",
                    icono = Icons.Filled.Person,
                    error = errorNombre
                )

                CampoTexto(
                    valor = email,
                    onValorCambia = { email = it },
                    etiqueta = "Correo electrónico",
                    icono = Icons.Filled.Email,
                    ayuda = "Lo usarás para ingresar a la aplicación",
                    error = errorEmail,
                    tecladoTipo = KeyboardType.Email
                )

                CampoTexto(
                    valor = password,
                    onValorCambia = { password = it },
                    etiqueta = "Contraseña",
                    icono = Icons.Filled.Lock,
                    ayuda = "Mínimo ${Validaciones.LARGO_MINIMO_PASSWORD} caracteres",
                    error = errorPassword,
                    esPassword = true
                )

                CampoTexto(
                    valor = confirmacion,
                    onValorCambia = { confirmacion = it },
                    etiqueta = "Repetir contraseña",
                    icono = Icons.Filled.Lock,
                    error = errorConfirmacion,
                    esPassword = true,
                    imeAction = ImeAction.Done
                )

                TituloSeccion("Tus preferencias")

                // Combo box de tipo de alimentación.
                ExposedDropdownMenuBox(
                    expanded = comboAbierto,
                    onExpandedChange = { comboAbierto = it }
                ) {
                    OutlinedTextField(
                        value = tipoDieta,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipo de alimentación") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = comboAbierto)
                        },
                        textStyle = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 68.dp)
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    )
                    ExposedDropdownMenu(
                        expanded = comboAbierto,
                        onDismissRequest = { comboAbierto = false }
                    ) {
                        TIPOS_DIETA.forEach { opcion ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = opcion,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                },
                                onClick = {
                                    tipoDieta = opcion
                                    comboAbierto = false
                                }
                            )
                        }
                    }
                }

                // Radio buttons de tamaño del hogar.
                TituloSeccion("¿Para cuántas personas cocinas?")
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    TAMANOS_HOGAR.forEach { opcion ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 52.dp)
                                .selectable(
                                    selected = tamanoHogar == opcion,
                                    onClick = { tamanoHogar = opcion },
                                    role = Role.RadioButton
                                )
                        ) {
                            RadioButton(
                                selected = tamanoHogar == opcion,
                                onClick = null
                            )
                            Text(
                                text = opcion,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                    }
                }

                // Check list de restricciones, presentado como grilla de dos columnas.
                TituloSeccion("¿Tienes alguna restricción?")
                GrillaSeleccionMultiple(
                    opciones = RESTRICCIONES,
                    seleccionadas = restriccionesElegidas,
                    onCambiaSeleccion = { opcion, activada ->
                        restriccionesElegidas = if (activada) {
                            restriccionesElegidas + opcion
                        } else {
                            restriccionesElegidas - opcion
                        }
                    }
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 52.dp)
                        .toggleable(
                            value = aceptaTerminos,
                            onValueChange = { aceptaTerminos = it },
                            role = Role.Checkbox
                        )
                ) {
                    Checkbox(checked = aceptaTerminos, onCheckedChange = null)
                    Text(
                        text = "Acepto los términos y condiciones",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }

                TituloSeccion("Resumen de tu registro")
                TablaDatos(
                    encabezadoIzquierdo = "Dato",
                    encabezadoDerecho = "Lo que elegiste",
                    filas = listOf(
                        "Nombre" to nombre.ifBlank { "Sin completar" },
                        "Correo" to email.ifBlank { "Sin completar" },
                        "Alimentación" to tipoDieta,
                        "Personas" to tamanoHogar,
                        "Restricciones" to if (restriccionesElegidas.isEmpty()) {
                            "Ninguna"
                        } else {
                            restriccionesElegidas.joinToString(", ")
                        }
                    )
                )

                BotonPrimario(
                    texto = "Crear mi cuenta",
                    icono = Icons.Filled.PersonAdd,
                    onClick = {
                        intentoEnvio = true
                        val formularioValido = Validaciones.errorNombre(nombre) == null &&
                            Validaciones.errorEmail(email) == null &&
                            Validaciones.errorPassword(password) == null &&
                            Validaciones.errorConfirmacion(password, confirmacion) == null
                        when {
                            !formularioValido -> scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Revisa los campos marcados en rojo."
                                )
                            }

                            !aceptaTerminos -> scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Debes aceptar los términos para continuar."
                                )
                            }

                            else -> {
                                // El registro deja la cuenta disponible para
                                // iniciar sesión, salvo que el correo ya exista.
                                val resultado = CuentasRepository.registrar(
                                    nombre = nombre,
                                    email = email,
                                    password = password,
                                    tipoDieta = tipoDieta,
                                    restricciones = restriccionesElegidas
                                )
                                when (resultado) {
                                    is ResultadoRegistro.Creada -> mostrarExito = true
                                    ResultadoRegistro.CorreoRepetido -> scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Ese correo ya tiene una cuenta creada."
                                        )
                                    }
                                }
                            }
                        }
                    }
                )

                Vinculo(
                    texto = "Ya tengo una cuenta, volver al inicio",
                    onClick = onVolver,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    if (mostrarExito) {
        AlertDialog(
            onDismissRequest = { mostrarExito = false },
            title = { Text("Cuenta creada") },
            text = {
                Text(
                    text = "Listo ${nombre.trim()}, tu cuenta quedó registrada. " +
                        "Ahora puedes ingresar con ${email.trim()} y tu contraseña.",
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarExito = false
                        onRegistroCompleto()
                    }
                ) {
                    Text("Ir al inicio de sesión")
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegistroScreenPreview() {
    MiniMinutaTheme {
        RegistroScreen(onRegistroCompleto = {}, onVolver = {})
    }
}
