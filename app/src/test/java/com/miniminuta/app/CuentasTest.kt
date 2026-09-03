package com.miniminuta.app

import com.miniminuta.app.data.Cuenta
import com.miniminuta.app.data.CuentaRegistrada
import com.miniminuta.app.data.CuentasRepository
import com.miniminuta.app.data.ResultadoRegistro
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Pruebas de las cuentas que usan las views de login, registro y recuperar.
 */
class CuentasTest {

    @Before
    fun dejarLasCuentasIniciales() {
        CuentasRepository.reiniciar()
    }

    @Test
    fun `ingresa con las credenciales de prueba`() {
        val cuenta = CuentasRepository.autenticar("maria@correo.com", CuentasRepository.PASSWORD_DEMO)

        assertNotNull(cuenta)
        assertEquals("María", cuenta?.nombre)
    }

    @Test
    fun `no ingresa con la contrasena equivocada`() {
        assertNull(CuentasRepository.autenticar("maria@correo.com", "otra clave"))
    }

    @Test
    fun `no ingresa con un correo que nadie registro`() {
        assertNull(CuentasRepository.autenticar("nadie@correo.com", CuentasRepository.PASSWORD_DEMO))
    }

    @Test
    fun `el correo no distingue mayusculas ni espacios`() {
        val cuenta = CuentasRepository.autenticar("  MARIA@Correo.com ", CuentasRepository.PASSWORD_DEMO)

        assertNotNull(cuenta)
    }

    @Test
    fun `la cuenta compara la clave sin exponerla`() {
        val cuenta = Cuenta(nombre = "Prueba", email = "prueba@correo.com", password = "secreta")

        assertTrue(cuenta.autentica("secreta"))
        assertFalse(cuenta.autentica("Secreta"))
    }

    @Test
    fun `el registro deja la cuenta lista para iniciar sesion`() {
        val resultado = CuentasRepository.registrar(
            nombre = "Raúl",
            email = "raul@correo.com",
            password = "hola1234",
            tipoDieta = "Vegetariana",
            restricciones = setOf("Sin gluten")
        )

        assertTrue(resultado is ResultadoRegistro.Creada)
        assertNotNull(CuentasRepository.autenticar("raul@correo.com", "hola1234"))
    }

    @Test
    fun `no se puede registrar dos veces el mismo correo`() {
        val repetido = CuentasRepository.registrar(
            nombre = "Otra María",
            email = "MARIA@correo.com",
            password = "hola1234",
            tipoDieta = "Vegetariana",
            restricciones = emptySet()
        )

        assertEquals(ResultadoRegistro.CorreoRepetido, repetido)
        assertEquals(3, CuentasRepository.correosRegistrados().size)
    }

    @Test
    fun `la cuenta registrada saluda distinto que la de prueba`() {
        val prueba = CuentasRepository.buscarPorEmail("ana@correo.com")
        val registrada = CuentaRegistrada(
            nombre = "Raúl",
            email = "raul@correo.com",
            password = "hola1234",
            tipoDieta = "Vegetariana",
            restricciones = setOf("Sin gluten", "Sin lactosa")
        )

        assertEquals("Hola Ana", prueba?.saludo())
        assertTrue(registrada.saludo().startsWith("Hola Raúl,"))
        assertTrue(registrada.saludo().contains("vegetariana con 2 restricciones"))
        assertEquals("Cuenta creada desde el registro", registrada.descripcion)
    }

    @Test
    fun `las restricciones no se repiten porque son un conjunto`() {
        val registrada = CuentaRegistrada(
            nombre = "Raúl",
            email = "raul@correo.com",
            password = "hola1234",
            tipoDieta = "Vegetariana",
            restricciones = setOf("Sin gluten", "Sin gluten", "Sin lactosa")
        )

        assertEquals(2, registrada.restricciones.size)
    }

    @Test
    fun `el mapa de credenciales trae una fila por cuenta de prueba`() {
        val credenciales = CuentasRepository.credencialesDePrueba()

        assertEquals(3, credenciales.size)
        assertEquals(CuentasRepository.PASSWORD_DEMO, credenciales["ana@correo.com"])
        assertNull(credenciales["nadie@correo.com"])
    }

    @Test
    fun `una cuenta registrada tratada como cuenta conserva su descripcion`() {
        val registrada = CuentaRegistrada(
            nombre = "Raúl",
            email = "raul@correo.com",
            password = "hola1234",
            tipoDieta = "Baja en calorías",
            restricciones = emptySet()
        )
        // Aunque la variable sea del tipo base, responden los métodos de la subclase.
        val comoCuenta: Cuenta = registrada

        assertTrue(comoCuenta.autentica("hola1234"))
        assertEquals("Cuenta creada desde el registro", comoCuenta.descripcion)
    }
}
