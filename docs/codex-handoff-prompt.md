# Codex Handoff Prompt

Copia y pega el siguiente prompt en la nueva sesion de Codex.

---

Quiero que tomes este repositorio como continuidad de un proyecto Android ya avanzado, no como un proyecto nuevo.

Tu tarea inicial no es programar todavia. Primero debes hacer un knowledge transfer riguroso del repositorio y del estado actual del producto.

Contexto clave:

- Este proyecto se llama `El Cuartel de las Cofias`.
- Es una app Android nativa para coordinacion interna de guardias de enfermeria.
- El usuario principal de la V1 es una administradora/coordinadora de guardias.
- No es un marketplace abierto ni un expediente clinico.
- Todo texto visible al usuario debe estar en espanol.
- El stack es Kotlin + Jetpack Compose + Room.
- Android Studio es el entorno principal de compilacion y ejecucion.
- VS Code + Codex se usa como apoyo fuerte de edicion.
- Ya existe persistencia local, formularios reales, validaciones, catalogos iniciales y edicion de pacientes.
- Ya existe una primera base de unit tests.
- El siguiente paso planeado es editar enfermeras y luego editar guardias, ambos con pruebas.

Quiero que leas este repositorio y, en este orden, revises:

1. `README.md`
2. `docs/handoff-current-state.md`
3. `docs/backlog-and-sprint-status.md`
4. `docs/engineering-guidelines.md`
5. `docs/project-history-and-vision.md`
6. `docs/roadmap-and-versions.md`
7. `docs/product-decisions-and-legal-guidelines.md`
8. `docs/migration-and-environment.md`
9. `docs/v1-foundation.md`

Despues revisa el codigo Android en `apps/android`.

Necesito que me respondas con este formato exacto:

## Knowledge Transfer Summary

### 1. Product Understanding
- explica en tus palabras que problema resuelve esta app
- quien es el usuario principal
- que si es y que no es el producto en esta etapa

### 2. Current Functional State
- resume que modulos ya existen
- que funcionalidades ya estan implementadas
- que se puede hacer hoy dentro de la app

### 3. Current Engineering State
- arquitectura y stack
- persistencia
- validaciones
- testing actual
- warnings o deudas conocidas

### 4. Exact Current Phase
- di explicitamente en que version/sprint estamos
- di que esta terminado
- di que esta pendiente inmediato

### 5. Recommended Next Step
- indica cual deberia ser el siguiente bloque tecnico exacto
- explica por que
- menciona que pruebas deberian acompanar ese cambio

### 6. Risks Or Open Questions
- lista huecos reales de informacion si los detectas
- separa claramente dudas criticas contra dudas opcionales

### 7. Handoff Confidence
- di que tan confiado estas de poder continuar sin perder contexto
- menciona si necesitas algo adicional o si el repo ya trae suficiente contexto

Reglas:

- no inventes alcance nuevo
- no propongas backend o nube si no esta justificado
- no cambies el enfoque de V1 interna
- considera siempre que estamos en una zona sensible por tratarse de cuidado y datos personales
- si detectas que algo del repo contradice el plan documentado, senalalo

Muy importante:

Al final agrega una seccion final llamada:

## Gaps To Review With Previous Codex

Y ahi lista cualquier hueco o duda que quieras que yo lleve de regreso al Codex anterior para confirmar si falta contexto.

No programes nada todavia. Solo analiza y devuelve el resumen.

---
