# El Cuartel de las Cofias

App Android nativa para coordinar guardias de enfermeria desde una sola herramienta interna.

## Estado actual

El proyecto ya paso la etapa de proof of concept tecnico. Hoy existe una V1 operativa interna con:

- navegacion base funcional
- modulos de Inicio, Pacientes, Enfermeras, Guardias, Reporte y Cobros
- persistencia local con Room
- formularios reales con validaciones
- catalogos iniciales para reducir texto libre
- alta de pacientes, enfermeras y guardias
- captura persistida de reporte
- edicion de pacientes
- primera base de unit tests

El siguiente paso planeado es continuar con:

1. edicion de enfermeras
2. edicion de guardias
3. cierre formal de guardia
4. relacion mas fuerte entre guardia y reporte
5. filtros, busquedas y alertas locales en fases posteriores

## Stack

- Android nativo
- Kotlin
- Jetpack Compose
- Room
- MVVM ligero
- Gradle
- Android Studio para compilar y correr
- VS Code con Codex como apoyo fuerte de edicion

## Como correr el proyecto

1. Instala Android Studio y Android SDK.
2. Clona este repositorio.
3. Abre `apps/android` en Android Studio.
4. Espera a que termine `Gradle Sync`.
5. Configura un emulador o dispositivo.
6. Corre la app.

Comandos utiles desde `apps/android`:

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat testDebugUnitTest
```

## Estructura del repositorio

```text
CuartelDeLasCofias
|- apps
|  \- android
|- assets
|- docs
|  |- archive
|  |- backlog-and-sprint-status.md
|  |- codex-handoff-prompt.md
|  |- engineering-guidelines.md
|  |- handoff-current-state.md
|  |- migration-and-environment.md
|  |- product-decisions-and-legal-guidelines.md
|  |- project-history-and-vision.md
|  |- roadmap-and-versions.md
|  \- v1-foundation.md
\- tools
```

## Documentos clave

- [docs/project-history-and-vision.md](docs/project-history-and-vision.md)
- [docs/roadmap-and-versions.md](docs/roadmap-and-versions.md)
- [docs/product-decisions-and-legal-guidelines.md](docs/product-decisions-and-legal-guidelines.md)
- [docs/engineering-guidelines.md](docs/engineering-guidelines.md)
- [docs/backlog-and-sprint-status.md](docs/backlog-and-sprint-status.md)
- [docs/handoff-current-state.md](docs/handoff-current-state.md)
- [docs/migration-and-environment.md](docs/migration-and-environment.md)
- [docs/codex-handoff-prompt.md](docs/codex-handoff-prompt.md)

## Fuente de verdad

La fuente operativa de verdad no es el chat original, sino:

1. el codigo
2. la documentacion viva dentro de `docs/`
3. el backlog y estado actual documentados

El archivo historico del chat existe como respaldo secundario, no como documento principal de trabajo.
