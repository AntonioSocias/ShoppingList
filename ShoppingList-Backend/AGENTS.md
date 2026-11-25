# ShoppingList - Agent Instructions

## Objetivo

Desarrollar una aplicacion Android para compartir listas de
la compra entre miembros de un hogar, con soporte offline.

## Responsabilidades

- ChatGPT: arquitectura, backend, PostgreSQL y sincronizacion.
- Codex: desarrollo y mantenimiento de Android.
- Propietario: validacion de decisiones y cambios.

## Reglas

- Consultar la documentacion de docs/ antes de programar.
- No modificar unilateralmente el contrato de la API.
- Mantener compatibilidad entre Android y backend.
- No eliminar funcionalidades existentes sin autorizacion.
- No almacenar credenciales en el repositorio.
- Mantener identificadores estables para los productos.
- Garantizar el funcionamiento offline.
- No dar por implementadas funcionalidades sin verificar.

## Documentacion

- docs/ARCHITECTURE.md
- docs/API_CONTRACT.md
- docs/SYNC_PROTOCOL.md
- docs/DATABASE.md
- docs/DECISIONS.md
- docs/PROJECT_STATUS.md
- docs/ANDROID_STATUS.md

## Al finalizar una tarea

- Identificar los archivos modificados.
- Registrar las decisiones tecnicas.
- Documentar cambios que afecten a otros componentes.
- Indicar las pruebas realizadas.
- Actualizar docs/PROJECT_STATUS.md.
- Registrar problemas y bloqueos pendientes.
