package com.miniminuta.app.navigation

import com.miniminuta.app.data.DiaSemana

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

    /**
     * El día viaja como el nombre del enum, que no lleva tildes ni espacios y
     * por lo tanto no necesita escaparse en la ruta.
     */
    fun detalleDe(dia: DiaSemana): String = "detalle/${dia.name}"

    fun seleccionDe(dia: DiaSemana): String = "seleccion/${dia.name}"
}
