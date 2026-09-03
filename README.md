# MiniMinuta

Aplicación móvil Android que entrega una minuta nutricional semanal de recetas.
La usuaria revisa las cinco recetas de la semana, entra al detalle de cada una
para ver ingredientes, preparación paso a paso y tabla nutricional, y puede
cambiar la receta de cualquier día por otra del catálogo.

La interfaz está pensada para personas con poca experiencia en el uso de
celulares: botones grandes, textos amplios, un objetivo por pantalla y mensajes
de error escritos en lenguaje simple.

## Tecnologías

- Kotlin
- Jetpack Compose con Material Design 3
- Navigation Compose
- Gradle con catálogo de versiones (`gradle/libs.versions.toml`)
- compileSdk 36, minSdk 24

## Cómo ejecutar el proyecto

1. Abrir la carpeta `MiniMinuta` desde Android Studio.
2. Esperar la sincronización de Gradle.
3. Elegir un emulador o un dispositivo físico con Android 7 o superior.
4. Presionar Run.

Desde la línea de comandos:

```bash
./gradlew assembleDebug        # genera el APK de depuración
./gradlew testDebugUnitTest    # ejecuta las pruebas unitarias
```

## Datos de prueba

La entrega no contempla servidor, por lo que las cuentas viven en el propio
proyecto. Hay tres cuentas de demostración y todas comparten la misma clave:

| Correo | Contraseña |
|---|---|
| `maria@correo.com` | `minuta123` |
| `jose@correo.com` | `minuta123` |
| `ana@correo.com` | `minuta123` |

Una cuenta creada desde el registro queda disponible de inmediato para iniciar
sesión, mientras la aplicación siga abierta.

## Pantallas

| Pantalla | Descripción |
|---|---|
| Login | Autentica el correo y la contraseña contra las cuentas guardadas, recuerda el correo en el dispositivo y enlaza al registro y a la recuperación. |
| Registro | Datos personales y preferencias alimentarias. Crea la cuenta y rechaza los correos ya ocupados. |
| Recuperar contraseña | Busca la cuenta por su correo y avisa si nadie lo tiene registrado. |
| Minuta | Grilla con las cinco recetas de la semana y un resumen que se recalcula al cambiar una receta. |
| Detalle de receta | Ingredientes, preparación, tabla nutricional y recomendación. |
| Elegir receta | Catálogo completo para reemplazar la receta de un día determinado. |

## Componentes de interfaz utilizados

Las tres vistas de acceso integran el catálogo completo de componentes que
pide la actividad:

| Componente | Login | Registro | Recuperar contraseña |
|---|---|---|---|
| Input | Correo y contraseña | Nombre, correo y contraseñas | Correo |
| Botones | Ingresar | Crear mi cuenta | Enviar instrucciones |
| Vínculos | Recuperar y crear cuenta | Volver al inicio | Volver al inicio |
| Textos | Título, bajada y ayudas | Títulos de sección y ayudas | Instrucciones y ayudas |
| Combo box | | Tipo de alimentación | |
| Radio buttons | | Personas del hogar | Medio de envío |
| Check list | Recordar mi correo | Restricciones y términos | |
| Grilla | Qué encontrarás en la aplicación | Restricciones alimentarias | Medio de envío |
| Tabla | Datos de prueba | Resumen del registro | Pasos del proceso |

Además, la vista Minuta y la de Elegir receta usan grillas (`LazyVerticalGrid`)
y la vista Detalle de receta usa una tabla para la información nutricional.

## Sintaxis de Kotlin aplicada

Estos son los elementos del lenguaje usados en el proyecto y dónde encontrarlos.

| Elemento de Kotlin | Dónde se usa |
|---|---|
| `enum class` | `DiaSemana`, `NivelCalorico` y `OrdenCatalogo` en `data/Receta.kt` |
| `data class` | `Receta`, `InfoNutricional` y `DiaMinuta` |
| `val` y `var` | `val` para el catálogo y las recetas; `var` para el estado de las pantallas |
| Funciones de extensión | `buscar`, `ordenarPor`, `nivelCalorico`, `seCocinaEn`, `promedioCalorias` |
| Funciones de una expresión | Casi todas las extensiones, con `=` en lugar de cuerpo |
| Funciones de orden superior | `filter`, `map`, `sortedBy`, `count` reciben lambdas |
| `when` con rangos | `nivelCalorico()` clasifica según `in 0..399` |
| `when` exhaustivo | `ordenarPor()` cubre los tres criterios sin `else` |
| Seguridad nula | `Receta?`, `DiaSemana?`, operadores `?.`, `?:` y `?.let` |
| Propiedad calculada | `InfoNutricional.totalMacros` con `get()` |
| Genéricos | `GrillaSeleccionUnica<T>` trabaja con enums y con cadenas |
| `companion object` | `DiaSemana.desdeNombre()` y las claves de `PreferenciasUsuario` |
| `arrayOf` | Las cinco recetas semanales y las cuentas de prueba |

### Programación orientada a objetos

El modelo de cuentas de `data/Cuentas.kt` es el que reúne los conceptos de
clases y objetos.

| Concepto | Dónde se usa |
|---|---|
| Clase con constructor parametrizado | `Cuenta(nombre, email, password)` |
| Herencia (`open` y `:`) | `CuentaRegistrada : Cuenta` |
| Sobrescritura (`override`) | `saludo()` y `descripcion` en `CuentaRegistrada` |
| Modificadores de visibilidad | `private val password`, `private val cuentas`, `private companion object` |
| Propiedades con `get()` | `Cuenta.inicial` y `Cuenta.descripcion` |
| Propiedad perezosa (`by lazy`) | `PreferenciasUsuario.preferencias` abre el archivo recién al usarlo |
| Interfaz | `sealed interface ResultadoRegistro` |
| `object` | `CuentasRepository` y `RecetasRepository` |

La contraseña es privada dentro de `Cuenta` y no tiene getter: la única manera
de comprobarla es pedirle a la cuenta que la valide con `autentica()`. El
saludo de la minuta cambia según el tipo de cuenta, porque `CuentaRegistrada`
sobrescribe `saludo()` y le suma las preferencias del formulario.

### Colecciones

El resumen semanal y el catálogo se resuelven completos con la biblioteca de
colecciones, sin bucles manuales:

```kotlin
catalogo.buscar(texto).ordenarPor(criterio)   // filtrar y ordenar encadenados
recetas.sumOf { it.nutricion.calorias }       // total de calorías
recetas.minByOrNull { it.tiempoMinutos }      // la más rápida, o null
recetas.count { it.nivelCalorico() == LIVIANA }
recetas.distinctBy { it.id }                  // cuántas no se repiten
DiaSemana.entries.mapIndexed { i, dia -> ... } // arma la minuta inicial
recetas.groupBy { it.nivelCalorico() }        // Map de nivel a recetas
cuentasDePrueba.associate { it.email to ... } // Map de correo a contraseña
cuentas.map { it.email.lowercase() }.toSet()  // Set de correos ocupados
```

Se usan los tres tipos que revisa la semana: listas (`List<Receta>`), conjuntos
(`Set<String>` para los correos y para las restricciones alimentarias, que no
admiten repetidos) y mapas (`Map<NivelCalorico, List<Receta>>` para el reparto
de la semana y `Map<String, String>` para la tabla de credenciales).

## Buscar y ordenar el catálogo

La pantalla para elegir receta permite buscar por nombre, descripción o
ingrediente, y ordenar por nombre, calorías o tiempo de preparación. Ambas
operaciones se apoyan en funciones de extensión sobre `List<Receta>`, de modo
que la pantalla solo mantiene el texto y el criterio elegido.

## Minuta editable

La minuta no es fija. Cada día tiene un botón para cambiar la receta, que abre
el catálogo completo con la receta actual marcada. Al guardar, la minuta y el
resumen de la semana se actualizan de inmediato.

El estado vive en `MinutaViewModel`, compartido por las pantallas de minuta,
detalle y selección. Así la elección se mantiene al navegar y sobrevive a los
giros de pantalla. No se guarda en disco: al cerrar la aplicación la minuta
vuelve a su estado inicial.

La misma receta puede repetirse en más de un día, porque no hay motivo para
impedirlo.

## Arreglo de las cinco recetas semanales

La minuta por omisión se declara como un arreglo de Kotlin en
`data/RecetasRepository.kt`, con las cinco recetas de lunes a viernes y la
recomendación nutricional de cada una dentro de `InfoNutricional`:

```kotlin
private val minutaInicial: Array<Receta> = arrayOf(...)

fun obtenerMinutaInicial(): List<DiaMinuta> =
    DiaSemana.entries.mapIndexed { indice, dia ->
        DiaMinuta(dia = dia, receta = minutaInicial[indice])
    }
```

El arreglo se recorre junto a los días del enum, de manera que cada día de la
semana queda emparejado con su receta por posición.

## Adaptabilidad y prestaciones del dispositivo

La grilla de recetas usa `WindowSizeClass` para mostrar una columna en
teléfonos en vertical y dos columnas en tablets o en orientación horizontal.
Los formularios se limitan a un ancho máximo y se centran en pantallas grandes,
y todas las pantallas admiten desplazamiento vertical.

La aplicación usa además dos prestaciones del teléfono:

- **Estado de la conexión.** Con `ConnectivityManager` y una suscripción a los
  cambios de red, el login y la minuta muestran un aviso cuando el dispositivo
  se queda sin internet y lo retiran apenas la conexión vuelve. La suscripción
  se cancela al salir de la pantalla.
- **Preferencias guardadas.** Al marcar "Recordar mi correo", el correo queda
  guardado con `SharedPreferences` y aparece escrito la próxima vez, incluso
  después de cerrar la aplicación por completo. Al desmarcar la casilla, el
  dato se borra.

## Estructura del proyecto

```
app/src/main/java/com/miniminuta/app/
├── MainActivity.kt          Actividad única, aplica el tema y calcula el tamaño de ventana
├── data/                    Modelo, enums, cuentas, catálogo y funciones de colección
├── navigation/              Rutas y grafo de navegación
├── ui/MinutaViewModel.kt    Estado de la minuta y cambio de receta por día
├── ui/components/           Componentes reutilizables de interfaz
├── ui/screens/              Las seis pantallas de la aplicación
├── ui/theme/                Colores, tipografía y tema
└── util/                    Validaciones, preferencias guardadas y estado de la conexión
```

## Pruebas

```bash
./gradlew testDebugUnitTest
```

Cubren las reglas de validación de los formularios, la consistencia del
catálogo de recetas, la búsqueda y el orden, el cambio de receta por día y el
modelo de cuentas: autenticación, registro, correos repetidos y el saludo
distinto de cada tipo de cuenta.
