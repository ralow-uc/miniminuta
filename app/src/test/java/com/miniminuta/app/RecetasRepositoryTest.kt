package com.miniminuta.app

import com.miniminuta.app.data.RecetasRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pruebas del arreglo de recetas que alimenta la minuta semanal.
 */
class RecetasRepositoryTest {

    @Test
    fun `la minuta tiene cinco recetas`() {
        assertEquals(5, RecetasRepository.obtenerMinutaSemanal().size)
    }

    @Test
    fun `cada receta trae su recomendacion nutricional`() {
        RecetasRepository.obtenerMinutaSemanal().forEach { receta ->
            assertTrue(receta.nombre.isNotBlank())
            assertTrue(receta.ingredientes.isNotEmpty())
            assertTrue(receta.preparacion.isNotEmpty())
            assertTrue(receta.nutricion.recomendacion.isNotBlank())
            assertTrue(receta.nutricion.calorias > 0)
        }
    }

    @Test
    fun `los identificadores no se repiten`() {
        val ids = RecetasRepository.obtenerMinutaSemanal().map { it.id }
        assertEquals(ids.size, ids.distinct().size)
    }

    @Test
    fun `busca una receta por su identificador`() {
        assertNotNull(RecetasRepository.obtenerPorId(1))
        assertEquals("Lunes", RecetasRepository.obtenerPorId(1)?.dia)
        assertNull(RecetasRepository.obtenerPorId(99))
    }
}
