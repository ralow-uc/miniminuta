package com.miniminuta.app.navigation

/**
 * Rutas de navegación de la aplicación.
 */
object Rutas {
    const val LOGIN = "login"
    const val REGISTRO = "registro"
    const val RECUPERAR = "recuperar"
    const val MINUTA = "minuta"

    /** El detalle recibe el identificador de la receta como argumento. */
    const val DETALLE = "detalle/{recetaId}"
    const val ARG_RECETA_ID = "recetaId"

    fun detalleDe(recetaId: Int): String = "detalle/$recetaId"
}
