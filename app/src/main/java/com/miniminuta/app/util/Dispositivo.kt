package com.miniminuta.app.util

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Guarda en el teléfono el correo de la última persona que ingresó.
 *
 * Es una clase con constructor parametrizado: recibe el contexto y con él abre
 * el archivo de preferencias del sistema. La propiedad que expone es mutable y
 * define get y set propios, de manera que las pantallas lean y escriban el dato
 * como si fuera una variable cualquiera.
 */
class PreferenciasUsuario(contexto: Context) {

    private val contextoApp = contexto.applicationContext

    /**
     * Abrir el archivo de preferencias toca el disco, así que la propiedad es
     * perezosa: recién se inicializa la primera vez que alguien lee o escribe
     * el correo, y no cada vez que se construye la clase.
     */
    private val preferencias: SharedPreferences by lazy {
        contextoApp.getSharedPreferences(ARCHIVO, Context.MODE_PRIVATE)
    }

    var correoRecordado: String
        get() = preferencias.getString(CLAVE_CORREO, "") ?: ""
        set(valor) {
            preferencias.edit().putString(CLAVE_CORREO, valor).apply()
        }

    /** Borra el correo guardado cuando la persona desmarca la casilla. */
    fun olvidarCorreo() {
        preferencias.edit().remove(CLAVE_CORREO).apply()
    }

    private companion object {
        const val ARCHIVO = "miniminuta_preferencias"
        const val CLAVE_CORREO = "correo_recordado"
    }
}

/**
 * Indica si el dispositivo tiene una conexión a internet activa y validada.
 *
 * La aplicación funciona sin conexión porque las recetas viajan dentro del
 * proyecto, pero el aviso le permite a la persona saber en qué estado está su
 * equipo antes de sincronizar.
 */
fun hayConexion(contexto: Context): Boolean {
    val gestor = contexto.getSystemService(ConnectivityManager::class.java) ?: return false
    val capacidades = gestor.getNetworkCapabilities(gestor.activeNetwork) ?: return false
    return capacidades.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
        capacidades.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}
