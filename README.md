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

Además, la vista Minuta usa una grilla (`LazyVerticalGrid`) para las recetas y
la vista Detalle de receta usa una tabla para la información nutricional.

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
