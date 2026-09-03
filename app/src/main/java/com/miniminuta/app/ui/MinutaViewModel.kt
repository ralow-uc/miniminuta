package com.miniminuta.app.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.miniminuta.app.data.Cuenta
import com.miniminuta.app.data.DiaMinuta
import com.miniminuta.app.data.DiaSemana
import com.miniminuta.app.data.NivelCalorico
import com.miniminuta.app.data.Receta
import com.miniminuta.app.data.RecetasRepository
import com.miniminuta.app.data.nivelCalorico
import com.miniminuta.app.data.promedioCalorias
import com.miniminuta.app.data.promedioMinutos

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

    /** Cuenta que tiene la sesión abierta, o null si nadie ingresó todavía. */
    var cuenta by mutableStateOf<Cuenta?>(null)
        private set

    /** Guarda la cuenta que acaba de autenticarse en el login. */
    fun iniciarSesion(cuenta: Cuenta) {
        this.cuenta = cuenta
    }

    /**
     * Cierra la sesión y devuelve la minuta a su estado inicial, para que la
     * siguiente persona no herede los cambios de la anterior.
     */
    fun cerrarSesion() {
        cuenta = null
        restaurarMinuta()
    }

    /**
     * Saludo que encabeza la minuta. Cada tipo de cuenta responde distinto,
     * porque saludo() está sobrescrito en la cuenta registrada.
     */
    fun saludo(): String = cuenta?.saludo() ?: "Hola"

    /** Tipo de cuenta con la sesión abierta, que cada subclase describe a su manera. */
    fun descripcionCuenta(): String = cuenta?.descripcion.orEmpty()

    /**
     * Reemplaza la receta de un día por otra del catálogo.
     *
     * Si el día o la receta no existen, la minuta queda igual.
     */
    fun cambiarReceta(dia: DiaSemana, recetaId: Int) {
        val nueva = RecetasRepository.obtenerPorId(recetaId) ?: return
        minuta = minuta.map { diaMinuta ->
            if (diaMinuta.dia == dia) diaMinuta.copy(receta = nueva) else diaMinuta
        }
    }

    /** Devuelve el día pedido, o null si no forma parte de la minuta. */
    fun obtenerDia(dia: DiaSemana?): DiaMinuta? =
        dia?.let { buscado -> minuta.firstOrNull { it.dia == buscado } }

    /** Recetas de la semana, sin el día que las acompaña. */
    private val recetasDeLaSemana: List<Receta>
        get() = minuta.map { it.receta }

    /** Promedio de calorías de la semana. */
    fun promedioCaloriasSemana(): Int = recetasDeLaSemana.promedioCalorias()

    /** Promedio de minutos de preparación de la semana. */
    fun promedioMinutosSemana(): Int = recetasDeLaSemana.promedioMinutos()

    /** Receta más rápida de la semana, o null si la minuta está vacía. */
    fun recetaMasRapida(): Receta? = recetasDeLaSemana.minByOrNull { it.tiempoMinutos }

    /** Receta con más calorías de la semana, o null si la minuta está vacía. */
    fun recetaMasCalorica(): Receta? = recetasDeLaSemana.maxByOrNull { it.nutricion.calorias }

    /**
     * Agrupa las recetas de la semana por carga calórica.
     *
     * groupBy devuelve un mapa donde la clave es el nivel y el valor la lista
     * de recetas que caen en él.
     */
    fun recetasPorNivel(): Map<NivelCalorico, List<Receta>> =
        recetasDeLaSemana.groupBy { it.nivelCalorico() }

    /** Cuántas recetas distintas hay en la semana. */
    fun recetasDistintas(): Int = recetasDeLaSemana.distinctBy { it.id }.size

    /** Vuelve a dejar la minuta como venía por omisión. */
    fun restaurarMinuta() {
        minuta = RecetasRepository.obtenerMinutaInicial()
    }
}
