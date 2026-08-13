package com.miniminuta.app.data

/**
 * Fuente de datos de la minuta semanal.
 *
 * Las recetas se mantienen en un arreglo fijo en memoria, tal como pide el
 * requerimiento de la actividad: cinco recetas semanales con sus respectivas
 * recomendaciones nutricionales.
 */
object RecetasRepository {

    private val recetas: Array<Receta> = arrayOf(
        Receta(
            id = 1,
            dia = "Lunes",
            nombre = "Pollo al horno con quinoa",
            descripcion = "Pechuga de pollo dorada acompañada de quinoa y verduras salteadas.",
            tiempoMinutos = 45,
            porciones = 4,
            emoji = "🍗",
            ingredientes = listOf(
                "4 pechugas de pollo",
                "1 taza de quinoa",
                "1 zapallo italiano",
                "1 pimentón rojo",
                "2 cucharadas de aceite de oliva",
                "Sal, pimienta y orégano"
            ),
            preparacion = listOf(
                "Precalienta el horno a 200 grados.",
                "Sazona las pechugas con sal, pimienta y orégano.",
                "Hornea el pollo por 30 minutos hasta que quede dorado.",
                "Lava la quinoa y cocínala en dos tazas de agua por 15 minutos.",
                "Saltea las verduras en aceite de oliva y sirve todo junto."
            ),
            nutricion = InfoNutricional(
                calorias = 520,
                proteinas = 42,
                carbohidratos = 48,
                grasas = 14,
                recomendacion = "Alto aporte de proteína magra. Ideal para comenzar la semana " +
                    "con energía y mantener la masa muscular."
            )
        ),
        Receta(
            id = 2,
            dia = "Martes",
            nombre = "Ensalada de lentejas y palta",
            descripcion = "Plato frío y liviano, rico en fibra y grasas saludables.",
            tiempoMinutos = 30,
            porciones = 4,
            emoji = "🥗",
            ingredientes = listOf(
                "2 tazas de lentejas cocidas",
                "1 palta madura",
                "2 tomates",
                "1 cebolla morada",
                "Jugo de 1 limón",
                "Cilantro fresco"
            ),
            preparacion = listOf(
                "Enjuaga las lentejas cocidas y déjalas escurrir.",
                "Corta el tomate, la cebolla y la palta en cubos pequeños.",
                "Mezcla todo en un bol grande.",
                "Aliña con jugo de limón, aceite de oliva y sal.",
                "Agrega cilantro picado justo antes de servir."
            ),
            nutricion = InfoNutricional(
                calorias = 410,
                proteinas = 18,
                carbohidratos = 52,
                grasas = 15,
                recomendacion = "Excelente fuente de fibra y hierro vegetal. Acompaña con " +
                    "jugo de naranja para mejorar la absorción del hierro."
            )
        ),
        Receta(
            id = 3,
            dia = "Miércoles",
            nombre = "Salmón con puré de camote",
            descripcion = "Salmón a la plancha con puré suave de camote al horno.",
            tiempoMinutos = 40,
            porciones = 4,
            emoji = "🐟",
            ingredientes = listOf(
                "4 filetes de salmón",
                "3 camotes medianos",
                "1 cucharada de mantequilla",
                "1 diente de ajo",
                "Jugo de 1 limón",
                "Sal y pimienta"
            ),
            preparacion = listOf(
                "Cocina los camotes en agua hirviendo por 20 minutos.",
                "Sazona el salmón con sal, pimienta y limón.",
                "Cocina el salmón a la plancha 4 minutos por lado.",
                "Muele los camotes con mantequilla y ajo hasta formar un puré.",
                "Sirve el salmón sobre el puré y decora con limón."
            ),
            nutricion = InfoNutricional(
                calorias = 480,
                proteinas = 38,
                carbohidratos = 40,
                grasas = 18,
                recomendacion = "Rico en omega 3, que ayuda a la salud del corazón. " +
                    "Se recomienda consumir pescado al menos dos veces por semana."
            )
        ),
        Receta(
            id = 4,
            dia = "Jueves",
            nombre = "Charquicán de verduras",
            descripcion = "Guiso tradicional casero con zapallo, papas y choclo.",
            tiempoMinutos = 50,
            porciones = 5,
            emoji = "🍲",
            ingredientes = listOf(
                "500 gramos de zapallo",
                "4 papas medianas",
                "1 taza de choclo",
                "1 cebolla",
                "1 zanahoria",
                "Comino, sal y pimienta"
            ),
            preparacion = listOf(
                "Pela y corta el zapallo, las papas y la zanahoria en cubos.",
                "Sofríe la cebolla con comino hasta que quede transparente.",
                "Agrega las verduras y cubre con agua caliente.",
                "Cocina por 30 minutos hasta que todo esté blando.",
                "Muele levemente con un tenedor y agrega el choclo."
            ),
            nutricion = InfoNutricional(
                calorias = 390,
                proteinas = 12,
                carbohidratos = 68,
                grasas = 8,
                recomendacion = "Plato económico y alto en vitamina A gracias al zapallo. " +
                    "Acompaña con una porción de legumbres para sumar proteína."
            )
        ),
        Receta(
            id = 5,
            dia = "Viernes",
            nombre = "Tortilla de acelga y avena",
            descripcion = "Tortilla al horno liviana, perfecta para cerrar la semana.",
            tiempoMinutos = 35,
            porciones = 4,
            emoji = "🍳",
            ingredientes = listOf(
                "1 atado de acelga",
                "4 huevos",
                "media taza de avena",
                "1 cebolla",
                "50 gramos de queso rallado",
                "Sal y nuez moscada"
            ),
            preparacion = listOf(
                "Lava y pica la acelga en tiras finas.",
                "Sofríe la cebolla y agrega la acelga hasta que reduzca.",
                "Bate los huevos con la avena, el queso y las especias.",
                "Mezcla con las verduras y vierte en una fuente aceitada.",
                "Hornea a 180 grados por 25 minutos."
            ),
            nutricion = InfoNutricional(
                calorias = 350,
                proteinas = 22,
                carbohidratos = 30,
                grasas = 16,
                recomendacion = "Aporta calcio y fibra. La avena ayuda a mantener la " +
                    "sensación de saciedad por más tiempo."
            )
        )
    )

    /** Devuelve la minuta completa de la semana. */
    fun obtenerMinutaSemanal(): List<Receta> = recetas.toList()

    /** Busca una receta por su identificador. Devuelve null si no existe. */
    fun obtenerPorId(id: Int): Receta? = recetas.firstOrNull { it.id == id }
}
