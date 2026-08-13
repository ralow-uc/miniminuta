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
import com.miniminuta.app.data.Receta
import com.miniminuta.app.data.RecetasRepository
import com.miniminuta.app.ui.components.TarjetaReceta
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
    recetas: List<Receta>,
    anchoPantalla: WindowWidthSizeClass,
    onVerReceta: (Receta) -> Unit,
    onCerrarSesion: () -> Unit,
    modifier: Modifier = Modifier
) {
    val columnas = if (anchoPantalla == WindowWidthSizeClass.Compact) 1 else 2

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
            item(span = { GridItemSpan(maxLineSpan) }) {
                ResumenSemana(recetas = recetas)
            }

            items(items = recetas, key = { it.id }) { receta ->
                TarjetaReceta(receta = receta, onClick = { onVerReceta(receta) })
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = "Toca una receta para ver los ingredientes y la preparación.",
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
    recetas: List<Receta>,
    modifier: Modifier = Modifier
) {
    val promedioCalorias = if (recetas.isEmpty()) {
        0
    } else {
        recetas.sumOf { it.nutricion.calorias } / recetas.size
    }
    val tiempoPromedio = if (recetas.isEmpty()) {
        0
    } else {
        recetas.sumOf { it.tiempoMinutos } / recetas.size
    }

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
            Text(
                text = "Tu semana está lista",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DatoResumen(valor = "${recetas.size}", etiqueta = "recetas")
                DatoResumen(valor = "$promedioCalorias", etiqueta = "kcal promedio")
                DatoResumen(valor = "$tiempoPromedio min", etiqueta = "de preparación")
            }
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
            recetas = RecetasRepository.obtenerMinutaSemanal(),
            anchoPantalla = WindowWidthSizeClass.Compact,
            onVerReceta = {},
            onCerrarSesion = {}
        )
    }
}
