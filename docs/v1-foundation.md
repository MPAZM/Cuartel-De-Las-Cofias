# El Cuartel de las Cofias - V1 Foundation

## Vision

El Cuartel de las Cofias V1 es una app Android para uso interno de una administradora de guardias de enfermeria. Su objetivo es organizar pacientes, enfermeras, turnos, reportes y cobros desde un solo lugar, reduciendo la dependencia de WhatsApp, notas sueltas y memoria operativa.

La V1 no busca ser un marketplace ni un expediente clinico electronico. Busca ser una herramienta de coordinacion operativa con captura minima de datos sensibles.

## Product Goal

Permitir que una administradora pueda:

- registrar casos y pacientes
- registrar enfermeras de confianza
- crear y asignar guardias
- llevar seguimiento del estado de cada guardia
- capturar un reporte final simple
- registrar tarifa, comision y pago neto

## Primary User

Administradora de guardias de enfermeria.

## Out Of Scope For V1

- app para familiares
- app para enfermeras
- chat interno
- geolocalizacion en tiempo real
- pagos integrados
- calificaciones publicas
- marketplace abierto
- expediente clinico completo
- notificaciones push

## Main Modules

1. Dashboard
2. Pacientes
3. Enfermeras
4. Guardias
5. Reportes
6. Cobros y comisiones

## Minimum Screens

1. Inicio / dashboard
2. Lista de guardias
3. Detalle de guardia
4. Lista de pacientes
5. Detalle de paciente
6. Lista de enfermeras
7. Detalle de enfermera
8. Captura de reporte de guardia
9. Resumen de cobros

## Primary Flow

1. La administradora registra un paciente/caso.
2. Registra o selecciona una enfermera.
3. Crea una guardia con fecha, horario, ubicacion y tarifa.
4. Asigna la guardia a una enfermera.
5. La guardia cambia de estado conforme avanza.
6. Al finalizar, se captura un reporte breve.
7. Se registra la comision y el pago neto correspondiente.

## Initial Entities

### Patient

- id
- fullName
- preferredName
- age
- careContext
- serviceLocationType
- serviceAddress
- emergencyContactName
- emergencyContactPhone
- active
- notes

### Nurse

- id
- fullName
- phone
- specialtyNotes
- baseZone
- active
- trustedLevelNotes
- notes

### Shift

- id
- patientId
- nurseId
- startDateTime
- endDateTime
- locationLabel
- serviceType
- rateAmount
- commissionAmount
- nurseNetAmount
- status
- adminNotes

### ShiftReport

- id
- shiftId
- generalStatus
- medicationNotes
- vitalSignsNotes
- relevantEvents
- hospitalStaffComments
- familyComments
- handoffSummary
- createdAt

### PaymentRecord

- id
- shiftId
- totalCharge
- commissionAmount
- nurseNetAmount
- paymentStatus
- paymentMethod
- paymentNotes

## Business Rules

- una guardia pertenece a un paciente
- una guardia puede tener una enfermera asignada
- una guardia debe tener hora de inicio y fin
- una guardia tiene tarifa total, comision y monto neto
- una guardia puede cerrarse sin reporte solo de manera excepcional
- un paciente puede tener muchas guardias
- una enfermera puede cubrir muchas guardias a lo largo del tiempo
- los reportes deben ser breves y operativos, no expediente clinico completo

## Legal And Privacy Principles

- capturar solo la informacion minima necesaria
- evitar datos clinicos extensos en V1
- no solicitar permisos sensibles innecesarios
- no capturar ubicacion en segundo plano
- considerar consentimiento expreso para datos sensibles en versiones funcionales posteriores
- disenar desde ahora para incluir aviso de privacidad y terminos de uso
- diferenciar notas operativas de informacion clinica formal

## Language Policy

- todo el contenido visible para usuarios finales debe estar en espanol
- esto incluye botones, etiquetas, mensajes, estados, formularios, validaciones y textos legales mostrados en la app
- el idioma base del producto es espanol
- el codigo fuente puede mantener nombres tecnicos en ingles cuando eso mejore la claridad y consistencia
- la documentacion interna puede escribirse en espanol, ingles o formato mixto segun sea mas util para desarrollo
- siempre que sea posible, los textos de interfaz deben centralizarse para facilitar consistencia y mantenimiento

## Technical Direction

### Platform

- Android nativo
- Kotlin
- Jetpack Compose

### Initial App Strategy

- una sola app Android
- persistencia local primero
- soporte para evolucionar a backend mas adelante

### Suggested Architecture

- enfoque por features con capas ligeras
- MVVM
- repositorios
- modelos separados por capa cuando valga la pena

## Suggested Package Structure

```text
com.cuarteldelascofias.app
├─ core
│  ├─ designsystem
│  ├─ navigation
│  ├─ utils
│  └─ common
├─ data
│  ├─ local
│  ├─ repository
│  └─ model
├─ domain
│  ├─ model
│  └─ usecase
├─ features
│  ├─ dashboard
│  ├─ patients
│  ├─ nurses
│  ├─ shifts
│  ├─ reports
│  └─ billing
└─ ui
   └─ theme
```

## Persistence Recommendation

Inicio:

- datos mock o seed local para construir pantallas rapido

Primera persistencia real:

- Room como base local

## Development Order

1. base de navegacion y tema
2. dashboard simple
3. modulo de pacientes
4. modulo de enfermeras
5. modulo de guardias
6. reportes
7. cobros y comisiones
8. persistencia con Room

## Exit Criteria For V1

La V1 estara lista cuando permita a una administradora:

- registrar pacientes y enfermeras
- crear y consultar guardias
- asignar guardias
- cerrar una guardia con reporte
- ver montos de cobro, comision y pago neto
- operar una semana real de trabajo sin depender de notas externas para esas tareas

## Current Status

Ya esta implementado:

- navegacion base entre Inicio, Pacientes, Enfermeras, Guardias, Cobros y Reporte
- persistencia local con Room para pacientes, enfermeras, guardias y reportes
- formularios reales con validaciones y resumen de errores
- catalogos base para tipo de ubicacion, disponibilidad, confianza y tipo de servicio
- alta de pacientes, enfermeras y guardias
- captura persistida de reporte de guardia
- edicion de pacientes

Pendiente inmediato:

- edicion de enfermeras
- edicion de guardias
- cierre formal de guardia
- relacion mas fuerte entre guardia y reporte
- filtros y busquedas
- mejora de cobros
- alertas locales

## Current Sprint - V1.1 Sprint 1

Objetivo:

- habilitar edicion de registros clave sin romper la base actual

Alcance del sprint:

1. editar pacientes
2. editar enfermeras
3. editar guardias
4. dejar base de pruebas automatizadas para validaciones y formularios

Estado actual del sprint:

- editar pacientes: completado
- base inicial de unit tests: completado
- editar enfermeras: pendiente
- editar guardias: pendiente

Definicion de terminado del sprint:

- se puede abrir un registro existente y editarlo
- los cambios persisten localmente
- las pantallas de detalle reflejan cambios guardados
- existen pruebas automatizadas para la logica critica del formulario intervenido
- el proyecto compila y las pruebas unitarias pasan

## Backlog V1.1

### Bloque 1 - Operacion editable

1. editar pacientes
2. editar enfermeras
3. editar guardias

### Bloque 2 - Flujo operativo real

4. terminar guardia de forma explicita
5. mostrar estado de reporte pendiente/capturado
6. abrir reporte naturalmente desde detalle de guardia

### Bloque 3 - Escalabilidad operativa

7. filtros por estado en guardias
8. busqueda en pacientes
9. busqueda en enfermeras
10. mejorar dashboard operativo

### Bloque 4 - Control administrativo

11. clarificar estados de cobro
12. mejorar resumen financiero

### Bloque 5 - Recordatorios

13. alertas locales para guardia por iniciar
14. alertas locales para guardia sin cerrar
15. alertas locales para reporte faltante
16. opcional: cobro pendiente

## Testing Strategy

Principios:

- cada cambio importante debe dejar al menos una prueba automatizada nueva o reforzar una existente
- la prioridad inicial es probar logica de negocio y validaciones, no apariencia visual
- la prueba manual en emulador sigue existiendo, pero deja de ser la unica defensa contra regresiones

Capas de prueba por etapa:

### Etapa actual

- unit tests de ViewModel
- unit tests de repositorios en memoria o fakes ligeros
- enfoque en validaciones, guardado y edicion

### Siguientes sprints

- tests de flujos de guardias y reportes
- tests de reglas de cobro y estados
- pruebas de alertas locales donde la logica sea desacoplable

### Etapa posterior

- Compose UI tests para flujos criticos
- pruebas instrumentadas solo en los recorridos mas sensibles

Regla operativa para futuros sprints:

- si se modifica un formulario, se agregan o ajustan tests de su ViewModel
- si se agrega una regla nueva de negocio, se agrega al menos un test de regression
- no se cierra un bloque importante sin compilar y ejecutar pruebas automatizadas relevantes
