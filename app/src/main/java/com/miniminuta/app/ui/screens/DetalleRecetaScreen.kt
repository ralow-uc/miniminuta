package com.miniminuta.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miniminuta.app.data.Receta
import com.miniminuta.app.data.RecetasRepository
import com.miniminuta.app.ui.components.BotonSecundario
import com.miniminuta.app.ui.components.EtiquetaDia
import com.miniminuta.app.ui.components.FilaDato
import com.miniminuta.app.ui.components.TablaDatos
import com.miniminuta.app.ui.components.TituloSeccion
import com.miniminuta.app.ui.theme.MiniMinutaTheme

/**
 * Ficha completa de una receta: ingredientes, preparación paso a paso y la
 * tabla con la información nutricional.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleRecetaScreen(
    receta: Receta,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(receta.dia) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver a la minuta"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = receta.emoji, style = MaterialTheme.typography.displaySmall)
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    EtiquetaDia(dia = receta.dia)
                    Text(
                        text = receta.nombre,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }

            Text(
                text = receta.descripcion,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilaDato("Tiempo de preparación", "${receta.tiempoMinutos} minutos")
                    FilaDato("Rinde para", "${receta.porciones} personas")
                }
            }

            TituloSeccion("Ingredientes")
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                receta.ingredientes.forEach { ingrediente ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "•", style = MaterialTheme.typography.bodyLarge)
                        Text(text = ingrediente, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

            TituloSeccion("Preparación")
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                receta.preparacion.forEachIndexed { indice, paso ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${indice + 1}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(text = paso, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

            TituloSeccion("Información nutricional por porción")
            TablaDatos(
                encabezadoIzquierdo = "Nutriente",
                encabezadoDerecho = "Cantidad",
                filas = listOf(
                    "Calorías" to "${receta.nutricion.calorias} kcal",
                    "Proteínas" to "${receta.nutricion.proteinas} g",
                    "Carbohidratos" to "${receta.nutricion.carbohidratos} g",
                    "Grasas" to "${receta.nutricion.grasas} g"
                )
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Recomendación",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = receta.nutricion.recomendacion,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            BotonSecundario(
                texto = "Volver a la minuta",
                icono = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = onVolver
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetalleRecetaScreenPreview() {
    MiniMinutaTheme {
        DetalleRecetaScreen(
            receta = RecetasRepository.obtenerMinutaSemanal().first(),
            onVolver = {}
        )
    }
}
