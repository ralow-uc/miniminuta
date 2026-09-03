package com.miniminuta.app.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.miniminuta.app.data.DiaSemana
import com.miniminuta.app.ui.MinutaViewModel
import com.miniminuta.app.ui.screens.DetalleRecetaScreen
import com.miniminuta.app.ui.screens.LoginScreen
import com.miniminuta.app.ui.screens.MinutaScreen
import com.miniminuta.app.ui.screens.RecuperarPasswordScreen
import com.miniminuta.app.ui.screens.RegistroScreen
import com.miniminuta.app.ui.screens.SeleccionRecetaScreen

/**
 * Grafo de navegación de la aplicación.
 *
 * Cada pantalla recibe funciones de navegación en lugar del NavHostController,
 * de manera que las pantallas se puedan previsualizar y probar por separado.
 * El estado de la minuta vive en un ViewModel compartido, para que la receta
 * que la usuaria elija en un día siga vigente al moverse entre pantallas.
 */
@Composable
fun AppNavGraph(
    anchoPantalla: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    minutaViewModel: MinutaViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Rutas.LOGIN,
        modifier = modifier
    ) {
        composable(Rutas.LOGIN) {
            LoginScreen(
                onIngresar = { cuenta ->
                    minutaViewModel.iniciarSesion(cuenta)
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
                minuta = minutaViewModel.minuta,
                saludo = minutaViewModel.saludo(),
                tipoDeCuenta = minutaViewModel.descripcionCuenta(),
                anchoPantalla = anchoPantalla,
                onVerReceta = { diaMinuta ->
                    navController.navigate(Rutas.detalleDe(diaMinuta.dia))
                },
                onCambiarReceta = { diaMinuta ->
                    navController.navigate(Rutas.seleccionDe(diaMinuta.dia))
                },
                onCerrarSesion = {
                    minutaViewModel.cerrarSesion()
                    navController.navigate(Rutas.LOGIN) {
                        popUpTo(Rutas.MINUTA) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Rutas.DETALLE,
            arguments = listOf(navArgument(Rutas.ARG_DIA) { type = NavType.StringType })
        ) { entradaPila ->
            val dia = DiaSemana.desdeNombre(entradaPila.arguments?.getString(Rutas.ARG_DIA))
            val diaMinuta = minutaViewModel.obtenerDia(dia)
            if (diaMinuta == null) {
                // Si el día no existe se vuelve a la minuta en vez de dejar la
                // pantalla en blanco.
                navController.popBackStack()
            } else {
                DetalleRecetaScreen(
                    diaMinuta = diaMinuta,
                    onVolver = { navController.popBackStack() },
                    onCambiarReceta = { navController.navigate(Rutas.seleccionDe(diaMinuta.dia)) }
                )
            }
        }

        composable(
            route = Rutas.SELECCION,
            arguments = listOf(navArgument(Rutas.ARG_DIA) { type = NavType.StringType })
        ) { entradaPila ->
            val dia = DiaSemana.desdeNombre(entradaPila.arguments?.getString(Rutas.ARG_DIA))
            val diaMinuta = minutaViewModel.obtenerDia(dia)
            if (diaMinuta == null) {
                navController.popBackStack()
            } else {
                SeleccionRecetaScreen(
                    dia = diaMinuta.dia,
                    catalogo = minutaViewModel.catalogo,
                    recetaActualId = diaMinuta.receta.id,
                    anchoPantalla = anchoPantalla,
                    onConfirmar = { recetaId ->
                        minutaViewModel.cambiarReceta(dia = diaMinuta.dia, recetaId = recetaId)
                        navController.popBackStack()
                    },
                    onVolver = { navController.popBackStack() }
                )
            }
        }
    }
}
