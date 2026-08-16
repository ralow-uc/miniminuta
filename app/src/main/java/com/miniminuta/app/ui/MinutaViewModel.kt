package com.miniminuta.app.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.miniminuta.app.data.DiaMinuta
import com.miniminuta.app.data.Receta
import com.miniminuta.app.data.RecetasRepository

/**
 * Estado de la minuta semanal.
 *
 * Vive fuera de las pantallas para que la receta elegida en un día siga
 * vigente al navegar entre la minuta, el detalle y el selector, y para que
 * sobreviva a los giros de pantalla.
 */
class MinutaViewModel : ViewModel() {

    /** Minuta actual: un día con su receta asignada. */
    var minuta by mutableStateOf(RecetasRepository.obtenerMinutaInicial())
        private set

    /** Catálogo completo entre el que se puede elegir. */
    val catalogo: List<Receta> = RecetasRepository.obtenerCatalogo()

    /**
     * Reemplaza la receta de un día por otra del catálogo.
     *
     * Si el día o la receta no existen, la minuta queda igual.
     */
    fun cambiarReceta(dia: String, recetaId: Int) {
        val nueva = RecetasRepository.obtenerPorId(recetaId) ?: return
        minuta = minuta.map { diaMinuta ->
            if (diaMinuta.dia == dia) diaMinuta.copy(receta = nueva) else diaMinuta
        }
    }

    /** Devuelve el día pedido, o null si no forma parte de la minuta. */
    fun obtenerDia(dia: String): DiaMinuta? = minuta.firstOrNull { it.dia == dia }

    /** Vuelve a dejar la minuta como venía por omisión. */
    fun restaurarMinuta() {
        minuta = RecetasRepository.obtenerMinutaInicial()
    }
}
