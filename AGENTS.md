# ShoppingList - Agent Instructions

## Objetivo del proyecto

Aplicacion Android para compartir listas de la compra
entre miembros de un hogar, con funcionamiento offline
y sincronizacion mediante un backend centralizado.

## Responsabilidades

- ChatGPT: arquitectura, backend Python y PostgreSQL.
- Codex: desarrollo y mantenimiento de Android.
- Propietario: validacion de decisiones tecnicas.

## Estructura del repositorio

- ShoppingList-Android/: aplicacion Android.
- ShoppingList-Backend/: servidor Python.
- docs/: documentacion tecnica compartida.

## Stack tecnologico

Android:
- Java.
- SQLite.
- Android Studio.

Backend:
- Python 3.12.
- FastAPI.
- PostgreSQL 17.
- SQLAlchemy.
- Alembic.
- Docker Compose.

## Reglas de desarrollo

- Leer la documentacion de docs/ antes de implementar.
- No modificar unilateralmente el contrato de la API.
- Mantener la compatibilidad entre Android y backend.
- No eliminar funcionalidades sin autorizacion.
- No subir credenciales ni archivos .env.
- Mantener identificadores estables para los productos.
- Garantizar el funcionamiento offline.
- No perder operaciones locales pendientes.
- Diferenciar propuestas de decisiones confirmadas.
- No modificar el componente del otro agente sin autorizacion.

## Documentacion compartida

- docs/ARCHITECTURE.md
- docs/API_CONTRACT.md
- docs/SYNC_PROTOCOL.md
- docs/DATABASE.md
- docs/DECISIONS.md
- docs/PROJECT_STATUS.md
- docs/ANDROID_STATUS.md

## Al finalizar cada tarea

- Identificar los archivos modificados.
- Registrar las decisiones tecnicas relevantes.
- Documentar cambios que afecten al otro componente.
- Indicar las pruebas realizadas y sus resultados.
- Actualizar docs/PROJECT_STATUS.md.
- Registrar problemas y bloqueos pendientes.
- No realizar commits ni pushes sin autorizacion.

## Comunicacion entre agentes

Consultar docs/AGENT_HANDOFF.md antes de iniciar
cada tarea y registrar los resultados al finalizar.

Actualizar tambien PROJECT_STATUS.md y los
documentos tecnicos afectados.

