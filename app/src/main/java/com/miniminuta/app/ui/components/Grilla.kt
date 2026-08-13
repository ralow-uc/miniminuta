package com.miniminuta.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Elemento informativo de la grilla de bienvenida. */
data class ItemInformativo(
    val emoji: String,
    val titulo: String,
    val detalle: String
)

/**
 * Grilla de dos columnas construida por filas.
 *
 * Se usa dentro de pantallas que ya tienen desplazamiento propio. El alto lo
 * define el contenido, de modo que ningún texto queda cortado aunque el
 * usuario tenga configurada una letra más grande en su teléfono.
 */
@Composable
private fun GrillaDeDosColumnas(
    cantidad: Int,
    modifier: Modifier = Modifier,
    celda: @Composable (Int) -> Unit
) {
    val filas = (0 until cantidad).chunked(2)
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        filas.forEach { fila ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                fila.forEach { indice ->
                    Column(modifier = Modifier.weight(1f).fillMaxHeight()) {
                        celda(indice)
                    }
                }
                // Mantiene alineada la última fila cuando queda incompleta.
                if (fila.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

/** Grilla de dos columnas con tarjetas informativas. */
@Composable
fun GrillaInformativa(
    items: List<ItemInformativo>,
    modifier: Modifier = Modifier
) {
    GrillaDeDosColumnas(cantidad = items.size, modifier = modifier) { indice ->
        val item = items[indice]
        Card(
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = item.emoji, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = item.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = item.detalle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Grilla de opciones que se pueden marcar y desmarcar.
 *
 * Cumple la función de un check list, presentado en dos columnas para que las
 * opciones se vean grandes y sean fáciles de tocar.
 */
@Composable
fun GrillaSeleccionMultiple(
    opciones: List<String>,
    seleccionadas: Set<String>,
    onCambiaSeleccion: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    GrillaDeDosColumnas(cantidad = opciones.size, modifier = modifier) { indice ->
        val opcion = opciones[indice]
        val marcada = opcion in seleccionadas
        Card(
            onClick = { onCambiaSeleccion(opcion, !marcada) },
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = if (marcada) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surface
                }
            ),
            border = BorderStroke(
                width = 1.dp,
                color = if (marcada) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .heightIn(min = 64.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {
                Checkbox(checked = marcada, onCheckedChange = null)
                Text(
                    text = opcion,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        }
    }
}

/**
 * Grilla de dos columnas donde solo se puede elegir una opción.
 *
 * Presenta los radio buttons como tarjetas grandes, más fáciles de tocar que
 * una lista de círculos pequeños.
 */
@Composable
fun GrillaSeleccionUnica(
    opciones: List<String>,
    seleccionada: String,
    onSelecciona: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    GrillaDeDosColumnas(cantidad = opciones.size, modifier = modifier) { indice ->
        val opcion = opciones[indice]
        val elegida = opcion == seleccionada
        Card(
            onClick = { onSelecciona(opcion) },
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = if (elegida) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surface
                }
            ),
            border = BorderStroke(
                width = 1.dp,
                color = if (elegida) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .heightIn(min = 64.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {
                RadioButton(selected = elegida, onClick = null)
                Text(
                    text = opcion,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        }
    }
}
