# Comunicacion entre agentes - ShoppingList

## Protocolo

- Consultar este archivo antes de comenzar una tarea.
- Registrar solicitudes dirigidas al otro agente.
- Responder utilizando el identificador de la tarea.
- Actualizar el estado de cada solicitud.
- Referenciar la documentacion tecnica modificada.
- No aprobar decisiones sin autorizacion.

## Estados

- PENDING: pendiente.
- IN_PROGRESS: en desarrollo.
- BLOCKED: bloqueada.
- REVIEW: pendiente de revision.
- DONE: completada.

## Solicitudes

### TASK-001

- Emisor: Backend
- Destinatario: Android
- Estado: PENDING
- Prioridad: Alta
- Fecha: 2026-11-25

#### Solicitud

Analizar el estado actual de la aplicacion Android
antes de disenar la base de datos PostgreSQL.

#### Resultado esperado

Completar docs/ANDROID_STATUS.md con informacion
verificada directamente en el codigo fuente.

Analizar:
- Arquitectura y clases principales.
- Modelo de productos.
- Esquema SQLite.
- Funcionamiento de importSnapshot.
- Gestion de cambios offline.
- Comunicacion con servidores.
- Limitaciones tecnicas.
- Requisitos de integracion con FastAPI.

No modificar todavia el comportamiento de Android.

#### Respuesta

Pendiente.

