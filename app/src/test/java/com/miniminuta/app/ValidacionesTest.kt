package com.miniminuta.app

import com.miniminuta.app.util.Validaciones
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pruebas de las reglas de validación de los formularios.
 */
class ValidacionesTest {

    @Test
    fun `acepta un correo con formato correcto`() {
        assertTrue(Validaciones.esEmailValido("maria@correo.com"))
        assertTrue(Validaciones.esEmailValido("maria.perez@mi-correo.cl"))
    }

    @Test
    fun `rechaza correos incompletos`() {
        assertFalse(Validaciones.esEmailValido(""))
        assertFalse(Validaciones.esEmailValido("maria"))
        assertFalse(Validaciones.esEmailValido("maria@"))
        assertFalse(Validaciones.esEmailValido("maria@correo"))
        assertFalse(Validaciones.esEmailValido("@correo.com"))
    }

    @Test
    fun `la contrasena exige el largo minimo`() {
        assertFalse(Validaciones.esPasswordValida("123"))
        assertFalse(Validaciones.esPasswordValida("12345"))
        assertTrue(Validaciones.esPasswordValida("123456"))
    }

    @Test
    fun `el nombre necesita al menos tres letras`() {
        assertFalse(Validaciones.esNombreValido("  "))
        assertFalse(Validaciones.esNombreValido("An"))
        assertTrue(Validaciones.esNombreValido("Ana"))
    }

    @Test
    fun `las contrasenas deben coincidir y no estar vacias`() {
        assertTrue(Validaciones.coincidenPasswords("minuta123", "minuta123"))
        assertFalse(Validaciones.coincidenPasswords("minuta123", "minuta124"))
        assertFalse(Validaciones.coincidenPasswords("", ""))
    }

    @Test
    fun `los mensajes de error orientan al usuario`() {
        assertEquals("Escribe tu correo electrónico", Validaciones.errorEmail(""))
        assertEquals("El correo debe incluir el símbolo @", Validaciones.errorEmail("maria"))
        assertNull(Validaciones.errorEmail("maria@correo.com"))
        assertNull(Validaciones.errorPassword("minuta123"))
        assertNull(Validaciones.errorConfirmacion("minuta123", "minuta123"))
        assertEquals(
            "Las dos contraseñas no son iguales",
            Validaciones.errorConfirmacion("minuta123", "otra")
        )
    }
}
