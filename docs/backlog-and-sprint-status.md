# Backlog And Sprint Status

## Estado actual resumido

Proyecto en fase `V1.1 Sprint 1`.

Objetivo del sprint:

- volver editable la base operativa
- introducir testing automatizado como parte del flujo normal de desarrollo

## Sprint 1 - V1.1

### Objetivo

Permitir corregir registros ya capturados sin tener que recrearlos, y empezar a blindar regresiones con pruebas.

### Alcance planeado

1. editar pacientes
2. editar enfermeras
3. editar guardias
4. base inicial de unit tests

### Estado

- editar pacientes: completado
- tests iniciales de pacientes: completado
- editar enfermeras: pendiente
- tests de enfermeras: pendiente
- editar guardias: pendiente
- tests de guardias: pendiente

### Criterios de terminado del sprint

- se puede editar paciente
- se puede editar enfermera
- se puede editar guardia
- existen tests de ViewModel para los formularios intervenidos
- `assembleDebug` pasa
- `testDebugUnitTest` pasa

## Backlog priorizado

### Prioridad inmediata

1. editar enfermeras
2. agregar unit tests de `NurseFormViewModel`
3. editar guardias
4. agregar unit tests de `ShiftFormViewModel`

### Siguiente bloque

5. terminar guardia de forma explicita
6. mostrar estado de reporte pendiente/capturado
7. CTA claros `Capturar reporte` o `Ver reporte`
8. tests del flujo de reporte y reglas de cierre

### Despues

9. filtros por estado en guardias
10. busqueda por paciente
11. busqueda por enfermera
12. dashboard mas util
13. estados mas claros de cobro

### Posterior

14. alertas locales de guardia por iniciar
15. alertas locales de guardia sin cerrar
16. alertas locales de reporte faltante
17. opcional: alertas de cobro pendiente

## Mapa de sprints sugerido

## Sprint 1

- editar pacientes
- editar enfermeras
- editar guardias
- tests iniciales de formularios

## Sprint 2

- cierre formal de guardia
- estado de reporte en detalle de guardia
- flujo de captura y consulta de reporte
- tests de reglas de guardia y reporte

## Sprint 3

- filtros y busquedas
- mejora del dashboard
- primeros estados de cobro
- tests de logica de filtrado y estados

## Sprint 4

- alertas locales
- pruebas de logica desacoplada de recordatorios
- UX final de esta etapa

## Politica de testing por sprint

Cada sprint debe incluir:

- cambios funcionales
- pruebas automatizadas nuevas o reforzadas
- verificacion de build

No se considera sprint cerrado si solo hay funcionalidad manual sin cobertura minima.

## Regla de priorizacion

Primero aquello que vuelve la app mas usable hoy.

Eso significa priorizar:

- edicion
- cierre de guardias
- claridad operativa

Por encima de:

- features llamativas
- complejidad de plataforma
- cosas que exigen backend sin necesidad inmediata
