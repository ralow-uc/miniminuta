package com.miniminuta.app.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.miniminuta.app.data.RecetasRepository
import com.miniminuta.app.ui.screens.DetalleRecetaScreen
import com.miniminuta.app.ui.screens.LoginScreen
import com.miniminuta.app.ui.screens.MinutaScreen
import com.miniminuta.app.ui.screens.RecuperarPasswordScreen
import com.miniminuta.app.ui.screens.RegistroScreen

/**
 * Grafo de navegación de la aplicación.
 *
 * Cada pantalla recibe funciones de navegación en lugar del NavHostController,
 * de manera que las pantallas se puedan previsualizar y probar por separado.
 */
@Composable
fun AppNavGraph(
    anchoPantalla: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Rutas.LOGIN,
        modifier = modifier
    ) {
        composable(Rutas.LOGIN) {
            LoginScreen(
                onIngresar = {
                    navController.navigate(Rutas.MINUTA) {
                        // El login queda fuera de la pila para que el botón atrás
                        // no devuelva al formulario una vez dentro.
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                },
                onIrARegistro = { navController.navigate(Rutas.REGISTRO) },
                onIrARecuperar = { navController.navigate(Rutas.RECUPERAR) }
            )
        }

        composable(Rutas.REGISTRO) {
            RegistroScreen(
                onRegistroCompleto = { navController.popBackStack() },
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Rutas.RECUPERAR) {
            RecuperarPasswordScreen(
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Rutas.MINUTA) {
            MinutaScreen(
                recetas = RecetasRepository.obtenerMinutaSemanal(),
                anchoPantalla = anchoPantalla,
                onVerReceta = { receta ->
                    navController.navigate(Rutas.detalleDe(receta.id))
                },
                onCerrarSesion = {
                    navController.navigate(Rutas.LOGIN) {
                        popUpTo(Rutas.MINUTA) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Rutas.DETALLE,
            arguments = listOf(navArgument(Rutas.ARG_RECETA_ID) { type = NavType.IntType })
        ) { entradaPila ->
            val recetaId = entradaPila.arguments?.getInt(Rutas.ARG_RECETA_ID) ?: 0
            val receta = RecetasRepository.obtenerPorId(recetaId)
            if (receta == null) {
                // Si el identificador no existe se vuelve a la minuta en vez de
                // dejar la pantalla en blanco.
                navController.popBackStack()
            } else {
                DetalleRecetaScreen(
                    receta = receta,
                    onVolver = { navController.popBackStack() }
                )
            }
        }
    }
}
