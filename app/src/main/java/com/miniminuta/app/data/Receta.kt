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
 * Receta del catálogo.
 *
 * La receta no conoce el día en que se cocina: un mismo plato puede asignarse
 * a cualquier día de la semana. Esa relación la guarda [DiaMinuta].
 */
data class Receta(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val tiempoMinutos: Int,
    val porciones: Int,
    val emoji: String,
    val ingredientes: List<String>,
    val preparacion: List<String>,
    val nutricion: InfoNutricional
)

/**
 * Un día de la minuta semanal con la receta que la usuaria eligió cocinar.
 */
data class DiaMinuta(
    val dia: String,
    val receta: Receta
)
