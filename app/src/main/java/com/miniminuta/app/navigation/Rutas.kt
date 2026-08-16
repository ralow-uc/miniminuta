package com.miniminuta.app.navigation

import android.net.Uri

/**
 * Rutas de navegación de la aplicación.
 */
object Rutas {
    const val LOGIN = "login"
    const val REGISTRO = "registro"
    const val RECUPERAR = "recuperar"
    const val MINUTA = "minuta"

    /** El detalle y el selector reciben el día de la minuta como argumento. */
    const val DETALLE = "detalle/{dia}"
    const val SELECCION = "seleccion/{dia}"
    const val ARG_DIA = "dia"

    fun detalleDe(dia: String): String = "detalle/${Uri.encode(dia)}"

    fun seleccionDe(dia: String): String = "seleccion/${Uri.encode(dia)}"
}
