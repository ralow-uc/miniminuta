package com.miniminuta.app.data

/**
 * Información nutricional de una receta. Los valores están expresados por porción.
 */
data class InfoNutricional(
    val calorias: Int,
    val proteinas: Int,
    val carbohidratos: Int,
    val grasas: Int,
    val recomendacion: String
)

/**
 * Receta de un dia de la minuta semanal.
 */
data class Receta(
    val id: Int,
    val dia: String,
    val nombre: String,
    val descripcion: String,
    val tiempoMinutos: Int,
    val porciones: Int,
    val emoji: String,
    val ingredientes: List<String>,
    val preparacion: List<String>,
    val nutricion: InfoNutricional
)
