# Migration And Environment

## Objetivo

Permitir mover este proyecto a otra computadora, otro usuario y otra cuenta de Codex sin perder continuidad.

## Dependencias reales de esta maquina

### Dependencias fuertes

- Android Studio instalado
- Android SDK instalado
- JDK compatible
- VS Code si se quiere conservar el flujo con Codex

### Dependencias debiles o no criticas

- configuraciones locales de editor
- rutas especificas del SDK de esta maquina
- estado del chat actual

## Lo que no debe viajar tal cual

### `local.properties`

Este archivo es local a cada maquina porque apunta al SDK instalado ahi.

No debe versionarse.

Cada nueva computadora debe regenerarlo o dejar que Android Studio lo cree.

## Lo que si debe viajar

- todo el codigo del repo
- carpeta `docs/`
- assets relevantes
- historial de backlog y estado
- prompt de handoff

## Skills de Codex

Para este proyecto no se dependio de skills exoticos o privados que sean indispensables en la nueva computadora.

Lo esencial fue:

- capacidad de leer/editar el repo
- compilar con Gradle/Android Studio
- seguir documentacion y backlog

## Entorno recomendado en la nueva computadora

1. instalar Android Studio
2. instalar SDK y platform tools
3. instalar VS Code si se quiere ese flujo
4. clonar el repo
5. abrir `apps/android` en Android Studio
6. dejar que Gradle sincronice
7. correr tests y build

## Validacion de migracion

Desde `apps/android` ejecutar:

```powershell
.\gradlew.bat testDebugUnitTest
.\gradlew.bat assembleDebug
```

Luego abrir el emulador y correr la app.

## Recomendacion de Git

La estrategia ideal es:

1. inicializar repo local
2. hacer commit base limpio
3. crear repo privado en GitHub
4. agregar remote
5. push
6. clonar en la otra computadora

## Lo que probablemente faltara hacer manualmente

- configurar credenciales Git/GitHub en la nueva maquina
- aceptar licencias de SDK si aplica
- reinstalar plugins de VS Code o Android Studio
- volver a configurar emuladores

## Que revisar primero en la nueva sesion de Codex

Orden sugerido de lectura:

1. `README.md`
2. `docs/handoff-current-state.md`
3. `docs/backlog-and-sprint-status.md`
4. `docs/engineering-guidelines.md`
5. `docs/project-history-and-vision.md`
6. `docs/codex-handoff-prompt.md`

## Recomendacion de continuidad

No confiar en memoria del chat anterior.

Confiar en:

- repo
- docs
- build
- tests

Ese es el paquete real de migracion.
