package com.miniminuta.app

import com.miniminuta.app.data.RecetasRepository
import com.miniminuta.app.ui.MinutaViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Pruebas del cambio de receta por día.
 */
class MinutaViewModelTest {

    @Test
    fun `cambia la receta del dia elegido`() {
        val viewModel = MinutaViewModel()
        val recetaOriginal = viewModel.obtenerDia("Martes")!!.receta

        viewModel.cambiarReceta(dia = "Martes", recetaId = 9)

        val recetaNueva = viewModel.obtenerDia("Martes")!!.receta
        assertEquals(9, recetaNueva.id)
        assertNotEquals(recetaOriginal.id, recetaNueva.id)
    }

    @Test
    fun `cambiar un dia no altera los demas`() {
        val viewModel = MinutaViewModel()
        val lunesAntes = viewModel.obtenerDia("Lunes")!!.receta

        viewModel.cambiarReceta(dia = "Martes", recetaId = 9)

        assertEquals(lunesAntes.id, viewModel.obtenerDia("Lunes")!!.receta.id)
        assertEquals(5, viewModel.minuta.size)
    }

    @Test
    fun `una receta inexistente deja la minuta igual`() {
        val viewModel = MinutaViewModel()
        val antes = viewModel.minuta.map { it.receta.id }

        viewModel.cambiarReceta(dia = "Lunes", recetaId = 99)

        assertEquals(antes, viewModel.minuta.map { it.receta.id })
    }

    @Test
    fun `un dia inexistente deja la minuta igual`() {
        val viewModel = MinutaViewModel()
        val antes = viewModel.minuta.map { it.receta.id }

        viewModel.cambiarReceta(dia = "Domingo", recetaId = 9)

        assertEquals(antes, viewModel.minuta.map { it.receta.id })
        assertNull(viewModel.obtenerDia("Domingo"))
    }

    @Test
    fun `se puede repetir la misma receta en dos dias`() {
        val viewModel = MinutaViewModel()

        viewModel.cambiarReceta(dia = "Lunes", recetaId = 7)
        viewModel.cambiarReceta(dia = "Jueves", recetaId = 7)

        assertEquals(7, viewModel.obtenerDia("Lunes")!!.receta.id)
        assertEquals(7, viewModel.obtenerDia("Jueves")!!.receta.id)
    }

    @Test
    fun `restaurar deja la minuta como al inicio`() {
        val viewModel = MinutaViewModel()
        viewModel.cambiarReceta(dia = "Lunes", recetaId = 10)

        viewModel.restaurarMinuta()

        assertEquals(
            RecetasRepository.obtenerMinutaInicial().map { it.receta.id },
            viewModel.minuta.map { it.receta.id }
        )
    }
}
