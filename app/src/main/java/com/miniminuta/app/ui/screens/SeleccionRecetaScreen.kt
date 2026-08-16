package com.miniminuta.app.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miniminuta.app.data.Receta
import com.miniminuta.app.data.RecetasRepository
import com.miniminuta.app.ui.components.BotonPrimario
import com.miniminuta.app.ui.theme.MiniMinutaTheme

/**
 * Pantalla para elegir qué receta se cocina un día determinado.
 *
 * Muestra el catálogo completo en una grilla. La receta que está asignada hoy
 * aparece marcada, y el cambio solo se aplica al confirmar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeleccionRecetaScreen(
    dia: String,
    catalogo: List<Receta>,
    recetaActualId: Int,
    anchoPantalla: WindowWidthSizeClass,
    onConfirmar: (Int) -> Unit,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    var elegidaId by rememberSaveable { mutableIntStateOf(recetaActualId) }
    val columnas = if (anchoPantalla == WindowWidthSizeClass.Compact) 1 else 2

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Receta del $dia") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver sin cambiar la receta"
                        )
                    }
                }
            )
        },
        bottomBar = {
            Column(modifier = Modifier.padding(20.dp)) {
                BotonPrimario(
                    texto = "Guardar esta receta",
                    icono = Icons.Filled.Check,
                    onClick = { onConfirmar(elegidaId) }
                )
            }
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
                Text(
                    text = "Toca la receta que quieres cocinar el $dia y luego guarda.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            items(items = catalogo, key = { it.id }) { receta ->
                TarjetaOpcionReceta(
                    receta = receta,
                    elegida = receta.id == elegidaId,
                    onClick = { elegidaId = receta.id }
                )
            }
        }
    }
}

/** Tarjeta del catálogo con el selector de receta. */
@Composable
private fun TarjetaOpcionReceta(
    receta: Receta,
    elegida: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = if (elegida) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = BorderStroke(
            width = if (elegida) 2.dp else 1.dp,
            color = if (elegida) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RadioButton(selected = elegida, onClick = null)
            Text(text = receta.emoji, style = MaterialTheme.typography.headlineSmall)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = receta.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${receta.tiempoMinutos} minutos  ·  " +
                        "${receta.nutricion.calorias} kcal  ·  " +
                        "${receta.porciones} porciones",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SeleccionRecetaScreenPreview() {
    MiniMinutaTheme {
        SeleccionRecetaScreen(
            dia = "Lunes",
            catalogo = RecetasRepository.obtenerCatalogo(),
            recetaActualId = 1,
            anchoPantalla = WindowWidthSizeClass.Compact,
            onConfirmar = {},
            onVolver = {}
        )
    }
}
