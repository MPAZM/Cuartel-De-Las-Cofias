# Product Decisions And Legal Guidelines

## Advertencia importante

Este documento no sustituye asesoria legal profesional. Resume decisiones de producto y principios prudentes para una app que toca una zona sensible: coordinacion de cuidado y datos personales. Antes de un lanzamiento real al mercado se debe revisar cumplimiento legal y regulatorio con asesoria especializada en la jurisdiccion aplicable.

## Naturaleza del producto en esta etapa

La app actual es:

- una herramienta interna de coordinacion operativa
- enfocada en una administradora de guardias
- orientada a registro minimo, seguimiento y control basico

La app actual no es:

- expediente clinico electronico
- sistema hospitalario
- sistema oficial de triage
- plataforma medica regulada
- canal de diagnostico
- sustituto de protocolos clinicos institucionales

## Decision central de producto

La V1 debe capturar solo lo minimo necesario para operar.

Esto afecta:

- campos del formulario
- permisos del dispositivo
- reportes
- identidad del usuario
- backlog

## Principios de minimizacion de datos

### Capturar lo minimo necesario

En esta fase solo se justifica capturar datos como:

- nombre del paciente
- contexto general de cuidado
- tipo y referencia de ubicacion
- contacto principal
- datos operativos de guardia
- notas breves y operativas

### Evitar sobrecaptura

No conviene introducir en V1, salvo necesidad muy clara y revision posterior:

- historial medico completo
- expediente detallado
- imagenes o documentos sensibles
- diagnosticos amplios
- resultados clinicos estructurados complejos
- biometria

### Separar lo operativo de lo clinico

Las notas de V1 deben entenderse como notas operativas breves de coordinacion, no como un expediente medico formal.

## Principios de interfaz y tono

La app debe sentirse:

- clara
- profesional
- sobria
- cercana
- no alarmista

No debe prometer:

- seguridad clinica garantizada
- supervision medica
- cumplimiento regulatorio que no se haya validado
- trazabilidad forense o medico-legal que todavia no existe

## Decisiones sobre formularios

### Obligatoriedad responsable

Se acordo que:

- los campos obligatorios deben marcarse con `*` rojo
- no se deben exigir demasiados campos si no son necesarios
- pero si un campo es critico para operar, si debe exigirse

### Validaciones claras

Las validaciones deben:

- decir exactamente que falta
- listar campos invalidos o vacios
- ayudar a corregir rapido
- no castigar al usuario con mensajes vagos

### Catalogos antes que texto libre donde tenga sentido

Se prioriza convertir ciertos campos en catalogos cuando:

- el dato suele caer en pocas opciones reales
- el texto libre genera inconsistencias
- el dato luego servira para filtros, reportes o alertas

Ejemplos ya adoptados:

- tipo de ubicacion
- disponibilidad de enfermera
- nivel de confianza
- tipo de servicio

## Lineamientos para reportes

El reporte de guardia en esta fase debe ser:

- breve
- operativo
- entendible
- consistente

No debe escalar todavia a un expediente clinico completo.

Se deben privilegiar:

- estado general
- medicamentos y cuidados
- signos vitales en formato breve
- eventos relevantes
- comentarios importantes
- entrega de turno

## Privacidad y seguridad por fase

### En la fase actual

- persistencia local en el dispositivo
- sin nube
- sin multiusuario
- sin sincronizacion

Esto reduce ciertas complejidades, pero no elimina la sensibilidad del dato.

### Medidas prudentes futuras

Cuando el producto crezca, deberan evaluarse:

- autenticacion
- control de acceso por rol
- cifrado o proteccion adicional de datos
- respaldo seguro
- politicas de retencion y borrado
- terminos de uso
- aviso de privacidad

## Permisos del dispositivo

Solo se deben pedir permisos cuando exista una necesidad real ya aprobada en el roadmap.

En particular:

- no pedir ubicacion en segundo plano en V1
- no pedir permisos invasivos solo “por si luego sirven”
- cualquier permiso sensible debe justificarse por valor real y revision de privacidad

## Alertas y notificaciones

Las alertas locales si tienen sentido en una fase posterior, pero deben ser:

- claras
- no intrusivas
- relacionadas con operacion real

Ejemplos aceptables:

- guardia por iniciar
- guardia no cerrada
- reporte faltante
- cobro pendiente

No deben usarse todavia para:

- comunicacion pseudo-medica
- alarmas clinicas
- promesas de monitoreo continuo

## Riesgos que se deben seguir vigilando

- crecimiento desordenado del modelo de datos
- meter demasiada informacion medica sin gobernanza
- funciones que impliquen responsabilidad clinica no validada
- mezclar rol administrativo con rol clinico sin claridad
- asumir cumplimiento legal sin asesoria formal

## Reglas de decision para futuras features

Antes de agregar una feature nueva, responder:

1. Resuelve un problema operativo real de la coordinadora hoy?
2. Aumenta de forma innecesaria la captura de datos sensibles?
3. Introduce una expectativa clinica o regulatoria que no podemos sostener?
4. Requiere permisos o infraestructura que todavia no justificamos?
5. Puede implementarse de forma escalonada y prudente?

Si las respuestas no son satisfactorias, la feature no deberia entrar todavia.

## Resumen ejecutivo

La politica del proyecto hasta ahora es:

- construir una herramienta interna y prudente
- minimizar datos
- no inflar el alcance medico
- preparar desde ahora una base mas responsable para versiones futuras
