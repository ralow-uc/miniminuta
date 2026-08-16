package com.miniminuta.app

import com.miniminuta.app.data.RecetasRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pruebas del catálogo de recetas y de la minuta semanal inicial.
 */
class RecetasRepositoryTest {

    @Test
    fun `la minuta inicial tiene cinco recetas, una por dia`() {
        val minuta = RecetasRepository.obtenerMinutaInicial()
        assertEquals(5, minuta.size)
        assertEquals(RecetasRepository.DIAS, minuta.map { it.dia })
    }

    @Test
    fun `el catalogo ofrece mas recetas que la minuta`() {
        val catalogo = RecetasRepository.obtenerCatalogo()
        assertTrue(catalogo.size > RecetasRepository.obtenerMinutaInicial().size)
    }

    @Test
    fun `cada receta trae su recomendacion nutricional`() {
        RecetasRepository.obtenerCatalogo().forEach { receta ->
            assertTrue(receta.nombre.isNotBlank())
            assertTrue(receta.ingredientes.isNotEmpty())
            assertTrue(receta.preparacion.isNotEmpty())
            assertTrue(receta.nutricion.recomendacion.isNotBlank())
            assertTrue(receta.nutricion.calorias > 0)
        }
    }

    @Test
    fun `los identificadores del catalogo no se repiten`() {
        val ids = RecetasRepository.obtenerCatalogo().map { it.id }
        assertEquals(ids.size, ids.distinct().size)
    }

    @Test
    fun `busca una receta por su identificador`() {
        assertNotNull(RecetasRepository.obtenerPorId(1))
        assertEquals("Pollo al horno con quinoa", RecetasRepository.obtenerPorId(1)?.nombre)
        assertNull(RecetasRepository.obtenerPorId(99))
    }
}
