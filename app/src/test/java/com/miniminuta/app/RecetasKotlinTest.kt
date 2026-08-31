package com.miniminuta.app

import com.miniminuta.app.data.DiaSemana
import com.miniminuta.app.data.NivelCalorico
import com.miniminuta.app.data.OrdenCatalogo
import com.miniminuta.app.data.RecetasRepository
import com.miniminuta.app.data.buscar
import com.miniminuta.app.data.nivelCalorico
import com.miniminuta.app.data.ordenarPor
import com.miniminuta.app.data.promedioCalorias
import com.miniminuta.app.data.promedioMinutos
import com.miniminuta.app.data.seCocinaEn
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pruebas de las funciones y colecciones de Kotlin que trabajan sobre el
 * catálogo: búsqueda, orden, clasificación y promedios.
 */
class RecetasKotlinTest {

    private val catalogo = RecetasRepository.obtenerCatalogo()

    @Test
    fun `el enum de dias tiene los cinco dias habiles`() {
        assertEquals(5, DiaSemana.entries.size)
        assertEquals("Miércoles", DiaSemana.MIERCOLES.etiqueta)
    }

    @Test
    fun `busca un dia por su nombre y devuelve null si no existe`() {
        assertEquals(DiaSemana.LUNES, DiaSemana.desdeNombre("LUNES"))
        assertNull(DiaSemana.desdeNombre("DOMINGO"))
        assertNull(DiaSemana.desdeNombre(null))
    }

    @Test
    fun `la busqueda vacia devuelve el catalogo completo`() {
        assertEquals(catalogo.size, catalogo.buscar("").size)
        assertEquals(catalogo.size, catalogo.buscar("   ").size)
    }

    @Test
    fun `la busqueda encuentra por nombre sin distinguir mayusculas`() {
        val encontradas = catalogo.buscar("SALMÓN")

        assertEquals(1, encontradas.size)
        assertTrue(encontradas.first().nombre.contains("Salmón"))
    }

    @Test
    fun `la busqueda tambien mira los ingredientes`() {
        val conQuinoa = catalogo.buscar("quinoa")

        assertTrue(conQuinoa.isNotEmpty())
        assertTrue(conQuinoa.all { receta ->
            receta.ingredientes.any { it.contains("quinoa", ignoreCase = true) } ||
                receta.nombre.contains("quinoa", ignoreCase = true)
        })
    }

    @Test
    fun `una busqueda sin coincidencias devuelve la lista vacia`() {
        assertTrue(catalogo.buscar("pizza napolitana").isEmpty())
    }

    @Test
    fun `ordenar por nombre deja la lista alfabetica`() {
        val ordenadas = catalogo.ordenarPor(OrdenCatalogo.NOMBRE).map { it.nombre }

        assertEquals(ordenadas.sorted(), ordenadas)
    }

    @Test
    fun `ordenar por calorias deja primero la mas liviana`() {
        val ordenadas = catalogo.ordenarPor(OrdenCatalogo.CALORIAS)

        assertEquals(catalogo.minOf { it.nutricion.calorias }, ordenadas.first().nutricion.calorias)
    }

    @Test
    fun `ordenar por tiempo deja primero la mas rapida`() {
        val ordenadas = catalogo.ordenarPor(OrdenCatalogo.TIEMPO)

        assertEquals(catalogo.minOf { it.tiempoMinutos }, ordenadas.first().tiempoMinutos)
    }

    @Test
    fun `ordenar no pierde ni agrega recetas`() {
        OrdenCatalogo.entries.forEach { criterio ->
            assertEquals(catalogo.size, catalogo.ordenarPor(criterio).size)
        }
    }

    @Test
    fun `el nivel calorico clasifica por rangos`() {
        catalogo.forEach { receta ->
            val esperado = when (receta.nutricion.calorias) {
                in 0..399 -> NivelCalorico.LIVIANA
                in 400..499 -> NivelCalorico.MODERADA
                else -> NivelCalorico.ALTA
            }
            assertEquals(esperado, receta.nivelCalorico())
        }
    }

    @Test
    fun `se cocina en compara el tiempo disponible`() {
        val rapida = catalogo.minByOrNull { it.tiempoMinutos }!!

        assertTrue(rapida.seCocinaEn(rapida.tiempoMinutos))
        assertFalse(rapida.seCocinaEn(rapida.tiempoMinutos - 1))
    }

    @Test
    fun `los promedios de una lista vacia son cero`() {
        assertEquals(0, emptyList<com.miniminuta.app.data.Receta>().promedioCalorias())
        assertEquals(0, emptyList<com.miniminuta.app.data.Receta>().promedioMinutos())
    }

    @Test
    fun `el promedio de calorias coincide con la suma dividida`() {
        val esperado = catalogo.sumOf { it.nutricion.calorias } / catalogo.size

        assertEquals(esperado, catalogo.promedioCalorias())
    }

    @Test
    fun `el total de macros suma los tres nutrientes`() {
        val receta = catalogo.first()
        val nutricion = receta.nutricion

        assertEquals(
            nutricion.proteinas + nutricion.carbohidratos + nutricion.grasas,
            nutricion.totalMacros
        )
    }
}
