# Session History 2026-05-31 Reconstructed

## Purpose

Este archivo conserva un historial amplio de la conversacion que dio forma al proyecto. No es la fuente principal de verdad; la fuente operativa es el codigo y la documentacion estructurada del repo.

Este archivo es una reconstruccion manual amplia a partir de la sesion actual. Se hizo para preservar contexto narrativo, decisiones y secuencia de pensamiento.

## High-Level Timeline

### 1. Definicion de entorno y stack

- se discutio usar VS Code con Codex, sin descartar Visual Studio Community
- se busco evitar mezclar tecnologias sin necesidad
- se concluyo que el camino mas limpio era Android Studio + Kotlin + Compose
- se confirmo que VS Code podia seguir usandose como entorno de edicion fuerte
- se discutio Gemini gratis vs Codex de paga y diferencias practicas
- se aterrizo el flujo: Android Studio para compilar y correr, VS Code/Codex para cambios grandes

### 2. Preparacion de entorno Android

- se guio la instalacion de Android Studio
- se resolvio duda sobre instalar SDK en `D:` en lugar de `C:`
- se explico `Gradle project sync in progress`
- se ayudo a correr la primera app
- se presento la duda del AVD y se resolvieron problemas con emulador mediante cold boot

### 3. Nacimiento del producto real

El usuario explico la experiencia real de hospitalizacion de su papa y la necesidad de contratar enfermeras de guarda. A partir de esa experiencia se describio:

- descubrimiento manual del servicio
- uso de calcomanias o anuncios en hospitales
- contratacion informal
- cargas familiares en guardias nocturnas
- ayuda concreta de una cuidadora contratada

Tambien se compartio lo aprendido a traves de una amiga enfermera:

- existencia de administradoras o coordinadoras
- armado manual de turnos
- grupos de WhatsApp como semillero de personal
- ausencia de catalogos, protocolos y estandarizacion
- reportes inconsistentes
- cobro y comision manual

### 4. Cambio de direccion estrategica

- inicialmente se penso en una app mas grande con clientes, catalogos y mapas
- luego se considero demasiado ambicioso
- se redefinio el usuario principal de V1 como administradora/coordinadora
- se acordo crecer por etapas realistas

### 5. Definiciones de producto previas al codigo serio

- se acordo definir alcance antes de lanzar mucho codigo
- se incorporaron desde temprano consideraciones legales y de privacidad
- se hablo de hacer cuestionario con amiga enfermera, pero se pospuso para no bloquear
- se construyo un roadmap por versiones

### 6. Identidad de V1

- se discutio el nombre `El Cuartel de las Cofias`
- se menciono que `Red de Cofias` podia ser una evolucion futura
- se cerro para V1:
  - nombre: `El Cuartel de las Cofias`
  - slogan: `Cuidado con corazon`
- se acepto reciclar logo e identidad ya existentes para esta etapa

### 7. Estructura de carpetas y proyecto

- se definio una estructura amplia dentro de `D:\Android`
- se creo una carpeta de proyectos
- se creo el proyecto Android definitivo en:
  - `D:\Android\Proyectos\CuartelDeLasCofias\apps\android`

### 8. Reglas de idioma y documentacion

- se acordo que todo texto visible al usuario final debe estar en espanol
- se acepto que codigo y documentacion tecnica pudieran usar ingles o formato mixto cuando eso ayudara al desarrollo

### 9. Construccion de la V1

Se fue construyendo progresivamente:

- base de navegacion
- tabs principales
- pantallas placeholder
- modulos de Pacientes, Enfermeras, Guardias, Cobros y Reporte
- persistencia local con Room
- validaciones y resumen rojo de errores
- catalogos iniciales

### 10. UX y validaciones

El usuario pidio explicitamente:

- `*` rojo para campos obligatorios
- mensajes detallados con lista de campos faltantes o invalidos
- no pedir demasiados obligatorios si no era necesario
- validar inputs antes de guardar

Estas decisiones se volvieron lineamientos permanentes del proyecto.

### 11. Problemas de emulador y arranque

- hubo una fase donde la app parecia no mostrar nada
- se reviso codigo
- al final se confirmo que el problema principal era del emulador/AVD
- el cold boot resolvio el problema

### 12. Flujos funcionales implementados

Se implementaron:

- alta de pacientes
- alta de enfermeras
- alta de guardias
- captura editable y persistida de reporte de guardia
- observacion reactiva en cobros
- detalle y navegacion basica

### 13. Normalizacion de catalogos

Se redujo texto libre en:

- tipo de ubicacion de paciente
- disponibilidad de enfermera
- nivel de confianza
- tipo de servicio de guardia

### 14. Validaciones de reporte

Se reforzo el reporte con:

- campos obligatorios razonables
- validaciones de longitud minima
- deteccion de entradas anomales o demasiado pobres

### 15. Vision de V1.1

Se definio que la siguiente etapa debia enfocarse en volver la app realmente util para operacion diaria:

- editar pacientes
- editar enfermeras
- editar guardias
- cierre formal de guardia
- relacion mas fuerte entre guardia y reporte
- filtros y busquedas
- luego alertas locales

### 16. Inicio de V1.1

Se implemento primero:

- edicion de pacientes

Y despues:

- base inicial de unit tests para `PatientFormViewModel`

### 17. Decision de testing

El usuario pidio formalizar pruebas automatizadas para no depender solo de prueba manual. Se acordo:

- introducir unit tests por ViewModel
- integrar testing a la definicion de terminado de futuros sprints
- usar testing incremental por bloques

### 18. Necesidad de migracion

Finalmente se detecto la necesidad de mover el proyecto a otra computadora con:

- otro usuario
- otra cuenta de Codex
- otro entorno

Por eso se planeo:

- repositorio Git
- documentacion de handoff
- archivo historico
- prompt de transferencia para otro chat

## Decisions That Must Not Be Lost

- la V1 es interna para coordinadora
- no es marketplace ni expediente clinico
- todo texto visible al usuario va en espanol
- Android Studio es el entorno principal de build
- VS Code/Codex sirve como apoyo fuerte de edicion
- testing ya forma parte del proceso
- el siguiente bloque tecnico es editar enfermeras y luego guardias

## Archive Notes

Si en el futuro se requiere una transcripcion mas literal del chat original de la plataforma, se debe exportar desde la interfaz o copiarla manualmente. Este archivo busca preservar el contenido util y la secuencia de decisiones con suficiente fidelidad operativa.
