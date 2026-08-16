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

La entrega no contempla servidor, por lo que el acceso usa credenciales de
demostración:

- Correo: `maria@correo.com`
- Contraseña: `minuta123`

## Pantallas

| Pantalla | Descripción |
|---|---|
| Login | Ingreso con correo y contraseña, opción de recordar el correo y vínculos a registro y recuperación. |
| Registro | Datos personales y preferencias alimentarias del hogar. |
| Recuperar contraseña | Envío simulado de instrucciones al correo del usuario. |
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

## Adaptabilidad

La grilla de recetas usa `WindowSizeClass` para mostrar una columna en
teléfonos en vertical y dos columnas en tablets o en orientación horizontal.
Los formularios se limitan a un ancho máximo y se centran en pantallas grandes,
y todas las pantallas admiten desplazamiento vertical.

## Estructura del proyecto

```
app/src/main/java/com/miniminuta/app/
├── MainActivity.kt          Actividad única, aplica el tema y calcula el tamaño de ventana
├── data/                    Modelo de receta, catálogo y minuta semanal inicial
├── navigation/              Rutas y grafo de navegación
├── ui/MinutaViewModel.kt    Estado de la minuta y cambio de receta por día
├── ui/components/           Componentes reutilizables de interfaz
├── ui/screens/              Las seis pantallas de la aplicación
├── ui/theme/                Colores, tipografía y tema
└── util/                    Reglas de validación de formularios
```

## Pruebas

```bash
./gradlew testDebugUnitTest
```

Cubren las reglas de validación de los formularios, la consistencia del
catálogo de recetas y el cambio de receta por día.
