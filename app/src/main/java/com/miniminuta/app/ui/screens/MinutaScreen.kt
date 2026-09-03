package com.miniminuta.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miniminuta.app.data.DiaMinuta
import com.miniminuta.app.data.NivelCalorico
import com.miniminuta.app.data.RecetasRepository
import com.miniminuta.app.data.nivelCalorico
import com.miniminuta.app.data.promedioCalorias
import com.miniminuta.app.data.promedioMinutos
import com.miniminuta.app.ui.components.AvisoSinConexion
import com.miniminuta.app.ui.components.TarjetaReceta
import com.miniminuta.app.ui.components.recordarEstadoConexion
import com.miniminuta.app.ui.theme.MiniMinutaTheme

/**
 * Pantalla principal con la minuta de la semana.
 *
 * Muestra las cinco recetas en una grilla que cambia de una a dos columnas
 * según el ancho disponible del dispositivo.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinutaScreen(
    minuta: List<DiaMinuta>,
    saludo: String,
    tipoDeCuenta: String,
    anchoPantalla: WindowWidthSizeClass,
    onVerReceta: (DiaMinuta) -> Unit,
    onCambiarReceta: (DiaMinuta) -> Unit,
    onCerrarSesion: () -> Unit,
    modifier: Modifier = Modifier
) {
    val columnas = if (anchoPantalla == WindowWidthSizeClass.Compact) 1 else 2
    val conectado = recordarEstadoConexion()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi minuta semanal") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    IconButton(onClick = onCerrarSesion) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Cerrar sesión"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(columnas),
            contentPadding = PaddingValues(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (!conectado) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    AvisoSinConexion()
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                ResumenSemana(minuta = minuta, saludo = saludo, tipoDeCuenta = tipoDeCuenta)
            }

            items(items = minuta, key = { it.dia }) { diaMinuta ->
                TarjetaReceta(
                    diaMinuta = diaMinuta,
                    onClick = { onVerReceta(diaMinuta) },
                    onCambiar = { onCambiarReceta(diaMinuta) }
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = "Toca una receta para ver los ingredientes y la preparación, " +
                        "o cambia la del día por otra del catálogo.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }
    }
}

/** Tarjeta de resumen con el total de recetas y el promedio de calorías. */
@Composable
private fun ResumenSemana(
    minuta: List<DiaMinuta>,
    saludo: String,
    tipoDeCuenta: String,
    modifier: Modifier = Modifier
) {
    // Todo el resumen se calcula con operaciones de colección sobre la minuta.
    val recetas = minuta.map { it.receta }
    val promedioCalorias = recetas.promedioCalorias()
    val tiempoPromedio = recetas.promedioMinutos()
    val masRapida = recetas.minByOrNull { it.tiempoMinutos }
    val livianas = recetas.count { it.nivelCalorico() == NivelCalorico.LIVIANA }
    val distintas = recetas.distinctBy { it.id }.size
    // groupBy arma un mapa de nivel calórico a recetas, que se recorre para
    // escribir el reparto de la semana en una sola línea.
    val porNivel = recetas.groupBy { it.nivelCalorico() }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = MaterialTheme.shapes.large,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = saludo,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = tipoDeCuenta,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DatoResumen(valor = "${minuta.size}", etiqueta = "recetas")
                DatoResumen(valor = "$promedioCalorias", etiqueta = "kcal promedio")
                DatoResumen(valor = "$tiempoPromedio min", etiqueta = "de preparación")
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.onSecondaryContainer)

            Text(
                text = buildString {
                    append("$livianas de ${minuta.size} son recetas livianas")
                    if (distintas < minuta.size) {
                        append(" y repites ${minuta.size - distintas}")
                    }
                    append(".")
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Text(
                text = NivelCalorico.entries
                    .filter { porNivel.containsKey(it) }
                    .joinToString(" · ") { nivel ->
                        "${nivel.etiqueta}: ${porNivel.getValue(nivel).size}"
                    },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            // El operador ?. junto con ?: evita tener que comprobar el nulo aparte.
            Text(
                text = masRapida?.let { "La más rápida es ${it.nombre}, ${it.tiempoMinutos} min." }
                    ?: "Todavía no hay recetas en la minuta.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
private fun DatoResumen(
    valor: String,
    etiqueta: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = valor,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MinutaScreenPreview() {
    MiniMinutaTheme {
        MinutaScreen(
            minuta = RecetasRepository.obtenerMinutaInicial(),
            saludo = "Hola María",
            tipoDeCuenta = "Cuenta de demostración",
            anchoPantalla = WindowWidthSizeClass.Compact,
            onVerReceta = {},
            onCambiarReceta = {},
            onCerrarSesion = {}
        )
    }
}
