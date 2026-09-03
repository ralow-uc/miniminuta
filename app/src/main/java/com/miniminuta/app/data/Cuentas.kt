package com.miniminuta.app.data

/**
 * Cuenta de una persona que usa la aplicación.
 *
 * La clase está marcada como open para poder heredar de ella. La contraseña es
 * privada a propósito: nadie fuera de la clase puede leerla, la única forma de
 * usarla es pidiéndole a la cuenta que verifique una clave con [autentica].
 */
open class Cuenta(
    val nombre: String,
    val email: String,
    private val password: String
) {

    /** Cómo se describe la cuenta. Las subclases la reemplazan. */
    open val descripcion: String
        get() = "Cuenta de demostración"

    /** Compara la clave recibida con la guardada, sin exponer la contraseña. */
    fun autentica(clave: String): Boolean = clave == password

    /** Compara el correo ignorando mayúsculas y espacios sobrantes. */
    fun tieneEmail(otro: String): Boolean = email.equals(otro.trim(), ignoreCase = true)

    /** Saludo que ve la persona al entrar. Las subclases lo amplían. */
    open fun saludo(): String = "Hola $nombre"
}

/**
 * Cuenta creada desde la pantalla de registro.
 *
 * Hereda de [Cuenta] y agrega las preferencias que la persona eligió en el
 * formulario, de modo que el saludo de la minuta sea distinto al de una cuenta
 * de demostración.
 */
class CuentaRegistrada(
    nombre: String,
    email: String,
    password: String,
    val tipoDieta: String,
    val restricciones: Set<String>
) : Cuenta(nombre, email, password) {

    override val descripcion: String
        get() = "Cuenta creada desde el registro"

    /**
     * Amplía el saludo de la clase base con las preferencias del formulario.
     * Las restricciones vienen en un conjunto, así que nunca se repiten.
     */
    override fun saludo(): String {
        val detalle = when (restricciones.size) {
            0 -> tipoDieta.lowercase()
            1 -> "${tipoDieta.lowercase()} con 1 restricción"
            else -> "${tipoDieta.lowercase()} con ${restricciones.size} restricciones"
        }
        return "${super.saludo()}, tu minuta apunta a $detalle"
    }
}

/** Resultado de intentar crear una cuenta desde el formulario de registro. */
sealed interface ResultadoRegistro {
    data class Creada(val cuenta: CuentaRegistrada) : ResultadoRegistro
    data object CorreoRepetido : ResultadoRegistro
}

/**
 * Guarda las cuentas de la aplicación mientras esta se ejecuta.
 *
 * Las cuentas de prueba se declaran en un arreglo y se copian a una lista
 * mutable, porque el registro necesita agregar cuentas nuevas y un arreglo
 * tiene el largo fijo.
 */
object CuentasRepository {

    /** Todas las cuentas de prueba comparten la misma clave para simplificar la revisión. */
    const val PASSWORD_DEMO = "minuta123"

    private val cuentasDePrueba: Array<Cuenta> = arrayOf(
        Cuenta(nombre = "María", email = "maria@correo.com", password = PASSWORD_DEMO),
        Cuenta(nombre = "José", email = "jose@correo.com", password = PASSWORD_DEMO),
        Cuenta(nombre = "Ana", email = "ana@correo.com", password = PASSWORD_DEMO)
    )

    private val cuentas: MutableList<Cuenta> = cuentasDePrueba.toMutableList()

    /** Busca una cuenta por correo. Devuelve null si nadie lo tiene registrado. */
    fun buscarPorEmail(email: String): Cuenta? = cuentas.firstOrNull { it.tieneEmail(email) }

    /**
     * Devuelve la cuenta si el correo existe y la clave calza, o null si no.
     *
     * El operador ?. encadena las dos comprobaciones: si el correo no existe,
     * la expresión completa queda en null sin ejecutar takeIf.
     */
    fun autenticar(email: String, password: String): Cuenta? =
        buscarPorEmail(email)?.takeIf { it.autentica(password) }

    /** Conjunto de correos ya ocupados. Al ser un Set no admite repetidos. */
    fun correosRegistrados(): Set<String> = cuentas.map { it.email.lowercase() }.toSet()

    /**
     * Agrega la cuenta al listado, salvo que el correo ya esté ocupado.
     */
    fun registrar(
        nombre: String,
        email: String,
        password: String,
        tipoDieta: String,
        restricciones: Set<String>
    ): ResultadoRegistro {
        if (email.trim().lowercase() in correosRegistrados()) return ResultadoRegistro.CorreoRepetido
        val cuenta = CuentaRegistrada(
            nombre = nombre.trim(),
            email = email.trim(),
            password = password,
            tipoDieta = tipoDieta,
            restricciones = restricciones
        )
        cuentas.add(cuenta)
        return ResultadoRegistro.Creada(cuenta)
    }

    /**
     * Correo y contraseña de cada cuenta de prueba, para la tabla del login.
     *
     * associate arma un mapa donde la clave es el correo y el valor la clave de
     * demostración, que es la misma para todas.
     */
    fun credencialesDePrueba(): Map<String, String> =
        cuentasDePrueba.associate { it.email to PASSWORD_DEMO }

    /** Deja el repositorio con las cuentas iniciales. Lo usan las pruebas. */
    fun reiniciar() {
        cuentas.clear()
        cuentas.addAll(cuentasDePrueba)
    }
}
