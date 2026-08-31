package com.miniminuta.app.data

/**
 * Días que componen la minuta semanal.
 *
 * Es un enum y no una cadena de texto para que las búsquedas y comparaciones
 * trabajen sobre valores controlados: el compilador impide escribir un día que
 * no exista y desaparecen los errores por tildes o mayúsculas.
 */
enum class DiaSemana(val etiqueta: String) {
    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miércoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes");

    companion object {
        /** Busca un día por su nombre. Devuelve null si el texto no corresponde. */
        fun desdeNombre(nombre: String?): DiaSemana? =
            entries.firstOrNull { it.name == nombre }
    }
}

/**
 * Qué tan cargada es una receta según sus calorías por porción.
 *
 * La clasificación se resuelve con una expresión when sobre rangos.
 */
enum class NivelCalorico(val etiqueta: String) {
    LIVIANA("Liviana"),
    MODERADA("Moderada"),
    ALTA("Alta")
}

/**
 * Información nutricional de una receta. Los valores están expresados por porción.
 */
data class InfoNutricional(
    val calorias: Int,
    val proteinas: Int,
    val carbohidratos: Int,
    val grasas: Int,
    val recomendacion: String
) {
    /** Suma de los macronutrientes en gramos. */
    val totalMacros: Int
        get() = proteinas + carbohidratos + grasas
}

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
    val dia: DiaSemana,
    val receta: Receta
)

/**
 * Criterios con que se puede ordenar el catálogo de recetas.
 */
enum class OrdenCatalogo(val etiqueta: String) {
    NOMBRE("Nombre"),
    CALORIAS("Menos calorías"),
    TIEMPO("Más rápida")
}

/** Clasifica la receta por sus calorías usando when sobre rangos. */
fun Receta.nivelCalorico(): NivelCalorico = when (nutricion.calorias) {
    in 0..399 -> NivelCalorico.LIVIANA
    in 400..499 -> NivelCalorico.MODERADA
    else -> NivelCalorico.ALTA
}

/** Indica si la receta se prepara en el tiempo disponible. */
fun Receta.seCocinaEn(minutos: Int): Boolean = tiempoMinutos <= minutos

/**
 * Filtra el catálogo por nombre, descripción o ingredientes.
 *
 * Si el texto viene vacío devuelve la lista completa, de modo que la pantalla
 * no tenga que decidir nada.
 */
fun List<Receta>.buscar(texto: String): List<Receta> {
    val consulta = texto.trim()
    if (consulta.isEmpty()) return this
    return filter { receta ->
        receta.nombre.contains(consulta, ignoreCase = true) ||
            receta.descripcion.contains(consulta, ignoreCase = true) ||
            receta.ingredientes.any { it.contains(consulta, ignoreCase = true) }
    }
}

/** Ordena el catálogo según el criterio elegido. */
fun List<Receta>.ordenarPor(criterio: OrdenCatalogo): List<Receta> = when (criterio) {
    OrdenCatalogo.NOMBRE -> sortedBy { it.nombre }
    OrdenCatalogo.CALORIAS -> sortedBy { it.nutricion.calorias }
    OrdenCatalogo.TIEMPO -> sortedBy { it.tiempoMinutos }
}

/** Promedio de calorías de la lista. Devuelve cero si está vacía. */
fun List<Receta>.promedioCalorias(): Int =
    if (isEmpty()) 0 else sumOf { it.nutricion.calorias } / size

/** Promedio de minutos de preparación. Devuelve cero si la lista está vacía. */
fun List<Receta>.promedioMinutos(): Int =
    if (isEmpty()) 0 else sumOf { it.tiempoMinutos } / size
