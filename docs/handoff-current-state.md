# Handoff Current State

## Que es este documento

Este documento es la foto mas util del proyecto para que otra persona o instancia de Codex pueda retomar el trabajo sin depender del chat original.

## Estado del codigo hoy

La app Android ya existe y compila.

Ruta del proyecto Android dentro del repo:

- `apps/android`

## Funcionalidades actuales

### Navegacion

- Inicio
- Guardias
- Pacientes
- Enfermeras
- Cobros
- Reporte de guardia

### Persistencia

- Room ya esta conectado para pacientes, enfermeras, guardias y reportes

### Formularios reales

Ya existen pantallas con captura real para:

- paciente
- enfermera
- guardia
- reporte de guardia

### Validaciones

Ya existen:

- resumen rojo de errores
- asterisco rojo para obligatorios
- validaciones por campo
- algunos catalogos para reducir texto libre

### Catalogos ya normalizados

- tipo de ubicacion de paciente
- disponibilidad de enfermera
- nivel de confianza de enfermera
- tipo de servicio de guardia

### Edicion ya implementada

- pacientes

## Testing actual

Ya existe base inicial de unit tests.

Cobertura introducida:

- `PatientFormViewModelTest`

Los tests actuales cubren:

- obligatorios faltantes
- catalogo invalido
- alta valida
- edicion valida sin perder identidad del registro

## Build y pruebas verificadas

Verificado recientemente:

```powershell
.\gradlew.bat testDebugUnitTest
.\gradlew.bat assembleDebug
```

Ambos pasaron.

## Proximo paso recomendado

Continuar con:

1. edicion de enfermeras
2. tests de `NurseFormViewModel`
3. edicion de guardias
4. tests de `ShiftFormViewModel`

## Decisiones importantes que no deben perderse

- la app es interna para una administradora, no marketplace abierto
- todo texto visible al usuario debe ir en espanol
- se debe evitar sobrecaptura de datos sensibles
- la prioridad es utilidad operativa real
- testing ya es parte del proceso, no extra opcional
- Android Studio es el entorno principal para compilar; VS Code/Codex se usa para ediciones fuertes

## Riesgos o deuda conocida

- todavia no existe edicion para enfermeras ni guardias
- todavia no existe cierre formal de guardia
- faltan filtros y alertas
- hay warnings de deprecacion no bloqueantes

## Skills o dependencias especiales

No hay dependencia critica de skills locales especiales de Codex para continuar este proyecto.

La continuidad depende mucho mas de:

- codigo
- documentacion
- backlog
- prompt de handoff

que de herramientas ocultas de esta maquina.

## Como validar que el nuevo entorno esta bien

1. abrir `apps/android` en Android Studio
2. dejar que Gradle sincronice
3. correr `testDebugUnitTest`
4. correr `assembleDebug`
5. abrir la app
6. confirmar navegacion y captura base

Si eso funciona, el proyecto ya quedo transferido correctamente a nivel tecnico.
