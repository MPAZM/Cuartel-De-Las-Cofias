# Engineering Guidelines

## Filosofia tecnica del proyecto

Este proyecto no busca mezclar tecnologias solo porque se puede. La decision tomada fue usar una base sencilla, gratuita y compatible con asistencia de IA:

- Android Studio como entorno principal de compilacion y ejecucion
- Kotlin + Jetpack Compose como stack principal
- VS Code + Codex como entorno fuerte de edicion cuando convenga

La regla es:

> minimizar malabares tecnologicos, maximizar continuidad y claridad.

## Entorno recomendado

### Herramienta principal

- Android Studio

Uso principal:

- crear y sincronizar proyecto
- compilar
- correr en emulador o dispositivo
- revisar logcat si hace falta

### Herramienta paralela

- VS Code con Codex

Uso principal:

- ediciones amplias
- refactors
- generacion de estructura
- documentacion
- soporte de backlog, pruebas y mantenimiento

## Dependencias de contexto

No existe dependencia critica de skills privados o especiales de esta computadora para continuar el proyecto.

Lo que si importa conservar:

- el codigo
- la documentacion del repo
- el backlog/sprint actualizado
- las decisiones de producto y legales
- el prompt de handoff

## Reglas de codigo

### Idioma

- todo texto visible al usuario final debe estar en espanol
- nombres tecnicos del codigo pueden seguir en ingles si eso mejora claridad
- documentacion interna puede ser espanol, ingles o mixta

### Arquitectura

- enfoque por features
- MVVM ligero
- repositorios
- Room para persistencia local
- modelos claros por capa cuando valga la pena

### Principios de implementacion

- cambios pequenos pero completos
- no dejar media funcionalidad si se puede cerrar punta a punta
- preferir reutilizar formularios y patrones existentes
- priorizar consistencia antes que sofisticacion

### Formularios

- los campos obligatorios llevan `*` rojo
- el resumen de errores debe listar exactamente que falta o que es invalido
- preferir catalogos cuando el dato operativo sea repetible
- no sobrecargar con campos obligatorios innecesarios

### Persistencia

- Room es la base actual
- durante prototipo se acepto `fallbackToDestructiveMigration()`
- antes de crecimiento serio se debera migrar a estrategia mas controlada

## Reglas de testing

### Principio general

Cada cambio importante debe dejar una defensa automatizada.

### Prioridad actual

1. unit tests de ViewModel
2. unit tests de reglas de negocio o repositorios fake
3. Compose UI tests solo cuando los flujos criticos lo ameriten

### Regla operativa

- si se toca un formulario, se agregan o actualizan tests de su ViewModel
- si se agrega una regla nueva, se agrega al menos un test de regression
- no cerrar un bloque importante sin correr build y pruebas relevantes

### Comandos base

```powershell
.\gradlew.bat testDebugUnitTest
.\gradlew.bat assembleDebug
```

## Reglas de backlog y sprints

- trabajar por bloques pequenos y verificables
- priorizar utilidad operativa real
- no abrir demasiados frentes en paralelo
- documentar estado actual despues de cambios relevantes

## Definicion de terminado para un bloque

Un bloque importante se considera terminado cuando:

- la funcionalidad existe de punta a punta
- la persistencia o efecto real funciona
- la navegacion y UX basica estan resueltas
- hay pruebas automatizadas razonables para la logica critica
- la app compila
- queda documentado el cambio si afecta backlog, alcance o estrategia

## Reglas de migracion y continuidad

- el repo debe contener el contexto suficiente para continuar en otra maquina
- el chat historico es respaldo, no fuente principal de verdad
- la documentacion del repo debe permitir que otro Codex retome sin depender de esta sesion

## Flujo recomendado de trabajo con IA

1. leer docs clave del repo
2. revisar estado actual y backlog
3. implementar un bloque concreto
4. agregar o ajustar pruebas
5. compilar y ejecutar pruebas
6. actualizar documentacion si cambia el estado del proyecto

## Deuda tecnica conocida

No bloquea continuidad, pero existe:

- warnings de deprecacion en Room y `menuAnchor()`
- falta editar enfermeras y guardias
- falta cierre formal de guardia
- falta robustecer cobros y alertas

## Regla de humildad del sistema

La app debe crecer desde uso real y evidencia, no desde sobrearquitectura ni entusiasmo de feature.
