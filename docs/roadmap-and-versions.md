# Roadmap And Versions

## Principio general

La evolucion del producto se planea por saltos pequenos y justificables. Cada version debe resolver un problema real antes de abrir un frente mas grande.

## Version 0 - Proof Of Concept Tecnico

### Objetivo

Demostrar que era posible construir una app Android funcional usando el stack elegido.

### Incluyo

- crear un proyecto Android nativo
- correr la app en emulador/dispositivo
- validar entorno Android Studio + Kotlin + Compose
- abrir el flujo de trabajo Android Studio para builds y VS Code/Codex para ediciones fuertes

### No incluyo

- definicion seria de producto
- estructura robusta de modulos
- persistencia real
- backlog formal

### Indicador de salida

La app corrio en dispositivo y se confirmo que el entorno de desarrollo si servia.

## Version 1 - Base Operativa Interna

### Objetivo

Crear una herramienta interna para una administradora de guardias.

### Incluye

- Inicio
- Pacientes
- Enfermeras
- Guardias
- Reporte de guardia
- Cobros y comisiones
- persistencia local con Room
- validaciones de formularios
- catalogos iniciales

### No incluye

- multiusuario real
- nube
- app para familiares
- app para flotilla de enfermeras
- mapas
- geolocalizacion en tiempo real
- pagos
- alertas push

### Indicador de salida

La administradora puede capturar y consultar operacion basica desde la app sin depender solo de notas externas.

## Version 1.1 - Operacion Editable Y Disciplina De Trabajo

### Objetivo

Volver la base actual util para uso diario real, no solo para captura inicial.

### Incluye

- edicion de pacientes
- edicion de enfermeras
- edicion de guardias
- pruebas automatizadas iniciales
- cierre formal de guardias
- mejor relacion guardia-reporte
- primeros filtros y busquedas basicos

### Estado actual

En progreso.

Ya completado dentro de este tramo:

- edicion de pacientes
- primera base de unit tests

Pendiente dentro de este tramo:

- edicion de enfermeras
- edicion de guardias
- cierre formal de guardia
- mejor visibilidad de reporte pendiente/capturado

### Indicador de salida

La coordinadora puede corregir datos, cerrar guardias y usar la app con mas seguridad operativa.

## Version 1.5 - Operacion Mas Madura

### Objetivo

Convertir la app en una herramienta mas confiable para jornadas de uso continuado.

### Podria incluir

- mejores filtros
- dashboard mas util
- estados de cobro mas claros
- alertas locales
- exportaciones basicas
- mejores empty states y UX

### No incluye todavia

- backend completo
- sincronizacion entre dispositivos
- sistema abierto para clientes

### Indicador de salida

La administradora puede usar la app de forma cotidiana con menos friccion y con recordatorios basicos.

## Version 2 - Operacion Compartida Con Enfermeras

### Objetivo

Extender el sistema a un grupo pequeno de enfermeras de confianza.

### Posible alcance

- cuenta o acceso simplificado para enfermeras
- confirmacion de turnos
- envio de reporte
- localizacion o presencia solo si se justifica
- alertas entre coordinadora y equipo

### Riesgos

- permisos
- complejidad operativa
- mayor tratamiento de datos
- necesidad de backend y sincronizacion

### Indicador de salida

La operacion ya no depende solo de una persona capturando todo manualmente.

## Version 3 - Experiencia Para Familias O Solicitantes

### Objetivo

Abrir parte del valor a quienes contratan el servicio.

### Posible alcance

- solicitud de servicios
- seguimiento de turnos
- acceso a reportes
- visibilidad parcial de perfiles

### Condicion para existir

No deberia empezarse hasta que:

- la operacion interna funcione bien
- la oferta de enfermeras confiables tenga proceso claro
- el manejo legal y de privacidad este mejor definido

## Criterios para decidir el salto de version

### Saltar de V1 a V1.1

Cuando la captura ya exista pero la app aun sea fragil para correcciones y uso diario.

### Saltar de V1.1 a V1.5

Cuando la base editable ya funcione y el siguiente cuello de botella sea productividad, visibilidad o seguimiento.

### Saltar de V1.5 a V2

Cuando haya suficiente uso real como para justificar multiusuario o colaboracion con varias enfermeras.

### Saltar de V2 a V3

Cuando tenga sentido abrir la experiencia al cliente/familiar y exista claridad legal, operativa y tecnica.

## Regla de roadmap

No abrir una version nueva por entusiasmo tecnico. Abrirla solo cuando la version actual ya este demostrando valor real o tenga un cuello de botella claro.
