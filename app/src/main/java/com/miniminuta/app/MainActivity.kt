package com.miniminuta.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.miniminuta.app.navigation.AppNavGraph
import com.miniminuta.app.ui.theme.MiniMinutaTheme

/**
 * Actividad única de la aplicación. Toda la interfaz se construye con Compose
 * y la navegación entre pantallas ocurre dentro de este mismo contenedor.
 */
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniMinutaTheme {
                // El tamaño de ventana permite adaptar la grilla de recetas a
                // teléfonos, tablets y a la orientación horizontal.
                val tamanoVentana = calculateWindowSizeClass(this)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavGraph(anchoPantalla = tamanoVentana.widthSizeClass)
                }
            }
        }
    }
}
