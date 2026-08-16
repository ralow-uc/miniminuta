package com.miniminuta.app.data

/**
 * Fuente de datos de las recetas.
 *
 * Mantiene dos cosas distintas:
 *
 * - El catálogo completo de recetas disponibles, entre las que la usuaria puede
 *   elegir para cualquier día.
 * - La minuta inicial: el arreglo de cinco recetas semanales con sus
 *   recomendaciones nutricionales, una por cada día de lunes a viernes, que es
 *   lo que la usuaria ve la primera vez que entra.
 */
object RecetasRepository {

    /** Días que componen la minuta semanal. */
    val DIAS = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes")

    private val catalogo: Array<Receta> = arrayOf(
        Receta(
            id = 1,
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
        ),
        Receta(
            id = 6,
            nombre = "Cazuela de vacuno",
            descripcion = "Caldo caliente con carne, zapallo y papa entera.",
            tiempoMinutos = 60,
            porciones = 5,
            emoji = "🥘",
            ingredientes = listOf(
                "500 gramos de asado de tira",
                "4 papas",
                "1 trozo de zapallo",
                "2 choclos partidos",
                "1 zanahoria",
                "Arroz, sal y orégano"
            ),
            preparacion = listOf(
                "Cuece la carne en abundante agua con sal por 30 minutos.",
                "Agrega el zapallo, la zanahoria y el choclo.",
                "Suma las papas enteras y cocina 20 minutos más.",
                "Añade un puñado de arroz y espera 10 minutos.",
                "Sirve bien caliente con orégano encima."
            ),
            nutricion = InfoNutricional(
                calorias = 540,
                proteinas = 35,
                carbohidratos = 55,
                grasas = 18,
                recomendacion = "Plato completo en un solo plato. Si buscas bajar las " +
                    "calorías, retira la grasa visible de la carne antes de cocinar."
            )
        ),
        Receta(
            id = 7,
            nombre = "Porotos granados",
            descripcion = "Guiso de porotos con zapallo y choclo, clásico de temporada.",
            tiempoMinutos = 55,
            porciones = 5,
            emoji = "🫘",
            ingredientes = listOf(
                "3 tazas de porotos granados",
                "1 trozo de zapallo",
                "2 choclos rallados",
                "1 cebolla",
                "1 diente de ajo",
                "Albahaca fresca"
            ),
            preparacion = listOf(
                "Cuece los porotos en agua con sal por 30 minutos.",
                "Sofríe la cebolla y el ajo hasta dorar.",
                "Agrega el zapallo en cubos y el sofrito a los porotos.",
                "Suma el choclo rallado y revuelve para espesar.",
                "Termina con albahaca picada."
            ),
            nutricion = InfoNutricional(
                calorias = 430,
                proteinas = 20,
                carbohidratos = 70,
                grasas = 7,
                recomendacion = "Las legumbres con choclo forman una proteína completa. " +
                    "Es una alternativa económica a la carne."
            )
        ),
        Receta(
            id = 8,
            nombre = "Merluza al vapor con arroz integral",
            descripcion = "Pescado suave al vapor con arroz integral y ensalada.",
            tiempoMinutos = 35,
            porciones = 4,
            emoji = "🍚",
            ingredientes = listOf(
                "4 filetes de merluza",
                "1 taza de arroz integral",
                "1 limón",
                "1 zanahoria",
                "Perejil fresco",
                "Sal y pimienta"
            ),
            preparacion = listOf(
                "Cocina el arroz integral en dos tazas de agua por 35 minutos.",
                "Sazona la merluza con limón, sal y pimienta.",
                "Cocina el pescado al vapor por 10 minutos.",
                "Ralla la zanahoria y mézclala con el arroz.",
                "Sirve el pescado sobre el arroz con perejil."
            ),
            nutricion = InfoNutricional(
                calorias = 380,
                proteinas = 34,
                carbohidratos = 45,
                grasas = 6,
                recomendacion = "Muy baja en grasas y fácil de digerir. Buena opción " +
                    "para la cena o para quienes cuidan el colesterol."
            )
        ),
        Receta(
            id = 9,
            nombre = "Wok de verduras con tofu",
            descripcion = "Salteado rápido de verduras crujientes con tofu dorado.",
            tiempoMinutos = 25,
            porciones = 4,
            emoji = "🥦",
            ingredientes = listOf(
                "300 gramos de tofu firme",
                "1 brócoli",
                "1 pimentón",
                "1 zanahoria",
                "2 cucharadas de salsa de soya",
                "1 cucharadita de jengibre"
            ),
            preparacion = listOf(
                "Corta el tofu en cubos y dóralo en una sartén caliente.",
                "Retira el tofu y saltea las verduras por 5 minutos.",
                "Agrega el jengibre y la salsa de soya.",
                "Devuelve el tofu a la sartén y revuelve.",
                "Sirve de inmediato para que las verduras queden crujientes."
            ),
            nutricion = InfoNutricional(
                calorias = 320,
                proteinas = 24,
                carbohidratos = 28,
                grasas = 13,
                recomendacion = "Opción vegetariana rica en proteína y calcio. " +
                    "El brócoli aporta vitamina C que mejora la absorción del hierro."
            )
        ),
        Receta(
            id = 10,
            nombre = "Guiso de garbanzos con espinaca",
            descripcion = "Guiso reconfortante de garbanzos con espinaca y tomate.",
            tiempoMinutos = 40,
            porciones = 4,
            emoji = "🍜",
            ingredientes = listOf(
                "3 tazas de garbanzos cocidos",
                "1 atado de espinaca",
                "3 tomates",
                "1 cebolla",
                "2 dientes de ajo",
                "Pimentón en polvo y comino"
            ),
            preparacion = listOf(
                "Sofríe la cebolla y el ajo hasta que estén blandos.",
                "Agrega el tomate picado y cocina 10 minutos.",
                "Suma los garbanzos y las especias.",
                "Incorpora la espinaca y cocina 5 minutos más.",
                "Deja reposar antes de servir para que tome sabor."
            ),
            nutricion = InfoNutricional(
                calorias = 400,
                proteinas = 19,
                carbohidratos = 58,
                grasas = 10,
                recomendacion = "Muy alto en fibra y hierro vegetal. Rinde bien y se " +
                    "puede guardar para el día siguiente."
            )
        )
    )

    /**
     * Arreglo con las cinco recetas semanales que trae la minuta por omisión,
     * una para cada día de lunes a viernes.
     */
    private val minutaInicial: Array<Receta> = arrayOf(
        catalogo[0],
        catalogo[1],
        catalogo[2],
        catalogo[3],
        catalogo[4]
    )

    /** Catálogo completo de recetas disponibles para elegir. */
    fun obtenerCatalogo(): List<Receta> = catalogo.toList()

    /** Minuta semanal por omisión, con una receta asignada a cada día. */
    fun obtenerMinutaInicial(): List<DiaMinuta> =
        DIAS.mapIndexed { indice, dia -> DiaMinuta(dia = dia, receta = minutaInicial[indice]) }

    /** Busca una receta del catálogo por su identificador. Devuelve null si no existe. */
    fun obtenerPorId(id: Int): Receta? = catalogo.firstOrNull { it.id == id }
}
