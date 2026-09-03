package com.miniminuta.app

import com.miniminuta.app.data.Cuenta
import com.miniminuta.app.data.DiaSemana
import com.miniminuta.app.data.RecetasRepository
import com.miniminuta.app.ui.MinutaViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pruebas del cambio de receta por día.
 */
class MinutaViewModelTest {

    @Test
    fun `cambia la receta del dia elegido`() {
        val viewModel = MinutaViewModel()
        val recetaOriginal = viewModel.obtenerDia(DiaSemana.MARTES)!!.receta

        viewModel.cambiarReceta(dia = DiaSemana.MARTES, recetaId = 9)

        val recetaNueva = viewModel.obtenerDia(DiaSemana.MARTES)!!.receta
        assertEquals(9, recetaNueva.id)
        assertNotEquals(recetaOriginal.id, recetaNueva.id)
    }

    @Test
    fun `cambiar un dia no altera los demas`() {
        val viewModel = MinutaViewModel()
        val lunesAntes = viewModel.obtenerDia(DiaSemana.LUNES)!!.receta

        viewModel.cambiarReceta(dia = DiaSemana.MARTES, recetaId = 9)

        assertEquals(lunesAntes.id, viewModel.obtenerDia(DiaSemana.LUNES)!!.receta.id)
        assertEquals(5, viewModel.minuta.size)
    }

    @Test
    fun `una receta inexistente deja la minuta igual`() {
        val viewModel = MinutaViewModel()
        val antes = viewModel.minuta.map { it.receta.id }

        viewModel.cambiarReceta(dia = DiaSemana.LUNES, recetaId = 99)

        assertEquals(antes, viewModel.minuta.map { it.receta.id })
    }

    @Test
    fun `pedir un dia que no existe no devuelve receta`() {
        val viewModel = MinutaViewModel()

        assertNull(viewModel.obtenerDia(null))
        assertNull(DiaSemana.desdeNombre("DOMINGO"))
        assertNull(viewModel.obtenerDia(DiaSemana.desdeNombre("DOMINGO")))
    }

    @Test
    fun `las consultas de coleccion resumen la semana`() {
        val viewModel = MinutaViewModel()

        assertEquals(5, viewModel.recetasDistintas())
        assertTrue(viewModel.promedioCaloriasSemana() > 0)
        assertTrue(viewModel.promedioMinutosSemana() > 0)
        assertNotNull(viewModel.recetaMasRapida())
        assertNotNull(viewModel.recetaMasCalorica())
    }

    @Test
    fun `la receta mas rapida es la de menos minutos`() {
        val viewModel = MinutaViewModel()
        val minimo = viewModel.minuta.minOf { it.receta.tiempoMinutos }

        assertEquals(minimo, viewModel.recetaMasRapida()?.tiempoMinutos)
    }

    @Test
    fun `repetir una receta reduce las recetas distintas`() {
        val viewModel = MinutaViewModel()

        viewModel.cambiarReceta(dia = DiaSemana.LUNES, recetaId = 7)
        viewModel.cambiarReceta(dia = DiaSemana.MARTES, recetaId = 7)

        assertEquals(4, viewModel.recetasDistintas())
    }

    @Test
    fun `se puede repetir la misma receta en dos dias`() {
        val viewModel = MinutaViewModel()

        viewModel.cambiarReceta(dia = DiaSemana.LUNES, recetaId = 7)
        viewModel.cambiarReceta(dia = DiaSemana.JUEVES, recetaId = 7)

        assertEquals(7, viewModel.obtenerDia(DiaSemana.LUNES)!!.receta.id)
        assertEquals(7, viewModel.obtenerDia(DiaSemana.JUEVES)!!.receta.id)
    }

    @Test
    fun `la sesion guarda la cuenta y el saludo cambia con ella`() {
        val viewModel = MinutaViewModel()
        assertEquals("Hola", viewModel.saludo())

        viewModel.iniciarSesion(Cuenta(nombre = "Ana", email = "ana@correo.com", password = "clave"))

        assertEquals("Hola Ana", viewModel.saludo())
    }

    @Test
    fun `cerrar sesion olvida la cuenta y restaura la minuta`() {
        val viewModel = MinutaViewModel()
        viewModel.iniciarSesion(Cuenta(nombre = "Ana", email = "ana@correo.com", password = "clave"))
        viewModel.cambiarReceta(dia = DiaSemana.LUNES, recetaId = 9)

        viewModel.cerrarSesion()

        assertNull(viewModel.cuenta)
        assertEquals(
            RecetasRepository.obtenerMinutaInicial().map { it.receta.id },
            viewModel.minuta.map { it.receta.id }
        )
    }

    @Test
    fun `el mapa por nivel calorico reparte las cinco recetas`() {
        val viewModel = MinutaViewModel()

        val porNivel = viewModel.recetasPorNivel()

        assertEquals(5, porNivel.values.sumOf { it.size })
        assertEquals(
            viewModel.minuta.map { it.receta.id }.toSet(),
            porNivel.values.flatten().map { it.id }.toSet()
        )
    }

    @Test
    fun `restaurar deja la minuta como al inicio`() {
        val viewModel = MinutaViewModel()
        viewModel.cambiarReceta(dia = DiaSemana.LUNES, recetaId = 10)

        viewModel.restaurarMinuta()

        assertEquals(
            RecetasRepository.obtenerMinutaInicial().map { it.receta.id },
            viewModel.minuta.map { it.receta.id }
        )
    }
}
