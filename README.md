# MiniMinuta

Aplicación móvil Android que entrega una minuta nutricional semanal de recetas.
El usuario revisa las cinco recetas de la semana, entra al detalle de cada una y
consulta sus ingredientes, la preparación paso a paso y la tabla nutricional.

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
| Minuta | Grilla con las cinco recetas de la semana y un resumen de la semana. |
| Detalle de receta | Ingredientes, preparación, tabla nutricional y recomendación. |

## Componentes de interfaz utilizados

| Componente | Ubicación |
|---|---|
| Campos de entrada | Login, Registro, Recuperar contraseña |
| Botones | Todas las pantallas (`Button`, `OutlinedButton`, `TextButton`) |
| Combo box | Registro, selector de tipo de alimentación |
| Radio buttons | Registro, cantidad de personas del hogar. Recuperar, medio de envío |
| Check list | Registro, restricciones alimentarias y aceptación de términos |
| Vínculos | Login y Registro |
| Textos | Títulos, descripciones y mensajes de ayuda |
| Grilla | Minuta, `LazyVerticalGrid` con las recetas |
| Tabla | Detalle de receta, información nutricional por porción |

## Adaptabilidad

La grilla de recetas usa `WindowSizeClass` para mostrar una columna en
teléfonos en vertical y dos columnas en tablets o en orientación horizontal.
Los formularios se limitan a un ancho máximo y se centran en pantallas grandes,
y todas las pantallas admiten desplazamiento vertical.

## Estructura del proyecto

```
app/src/main/java/com/miniminuta/app/
├── MainActivity.kt          Actividad única, aplica el tema y calcula el tamaño de ventana
├── data/                    Modelo de receta y arreglo con la minuta semanal
├── navigation/              Rutas y grafo de navegación
├── ui/components/           Componentes reutilizables de interfaz
├── ui/screens/              Las cinco pantallas de la aplicación
├── ui/theme/                Colores, tipografía y tema
└── util/                    Reglas de validación de formularios
```

## Pruebas

```bash
./gradlew testDebugUnitTest
```

Cubren las reglas de validación de los formularios y la consistencia del
arreglo de recetas.
