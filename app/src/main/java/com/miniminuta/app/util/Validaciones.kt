package com.miniminuta.app.util

/**
 * Reglas de validación de los formularios.
 *
 * Están separadas de la interfaz para poder probarlas con tests unitarios
 * sin necesidad de levantar la UI.
 */
object Validaciones {

    const val LARGO_MINIMO_PASSWORD = 6

    /** Valida que el correo tenga un formato básico usuario@dominio.extension */
    fun esEmailValido(email: String): Boolean {
        val limpio = email.trim()
        if (limpio.isEmpty()) return false
        return Regex("^[\\w.+-]+@[\\w-]+\\.[A-Za-z]{2,}$").matches(limpio)
    }

    /** La contraseña debe tener al menos seis caracteres. */
    fun esPasswordValida(password: String): Boolean =
        password.length >= LARGO_MINIMO_PASSWORD

    /** El nombre debe tener al menos tres caracteres reales. */
    fun esNombreValido(nombre: String): Boolean =
        nombre.trim().length >= 3

    /** Las dos contraseñas ingresadas deben coincidir y no estar vacías. */
    fun coincidenPasswords(password: String, confirmacion: String): Boolean =
        password.isNotEmpty() && password == confirmacion

    /** Mensaje de error del correo, o null si el dato es correcto. */
    fun errorEmail(email: String): String? = when {
        email.isBlank() -> "Escribe tu correo electrónico"
        !email.contains("@") -> "El correo debe incluir el símbolo @"
        !esEmailValido(email) -> "Revisa el correo, por ejemplo: nombre@correo.com"
        else -> null
    }

    /** Mensaje de error de la contraseña, o null si el dato es correcto. */
    fun errorPassword(password: String): String? = when {
        password.isBlank() -> "Escribe tu contraseña"
        !esPasswordValida(password) ->
            "La contraseña necesita al menos $LARGO_MINIMO_PASSWORD caracteres"
        else -> null
    }

    /** Mensaje de error del nombre, o null si el dato es correcto. */
    fun errorNombre(nombre: String): String? = when {
        nombre.isBlank() -> "Escribe tu nombre"
        !esNombreValido(nombre) -> "El nombre necesita al menos 3 letras"
        else -> null
    }

    /** Mensaje de error de la confirmación de contraseña, o null si coincide. */
    fun errorConfirmacion(password: String, confirmacion: String): String? = when {
        confirmacion.isBlank() -> "Repite tu contraseña"
        !coincidenPasswords(password, confirmacion) -> "Las dos contraseñas no son iguales"
        else -> null
    }
}
