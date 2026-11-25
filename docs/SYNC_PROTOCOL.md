# Protocolo de sincronizacion

## Estado

Propuesta pendiente de validacion.

## Objetivos

- Permitir modificaciones sin conexion.
- Compartir productos entre miembros de un hogar.
- Evitar la perdida de cambios locales.
- Evitar operaciones duplicadas.

## Propuesta

1. Guardar los cambios en SQLite.
2. Registrar las operaciones pendientes.
3. Enviar las operaciones al recuperar la conexion.
4. Procesar las operaciones en el servidor.
5. Devolver confirmaciones y un snapshot actualizado.
6. Aplicar el snapshot sin perder cambios pendientes.

## Reglas propuestas

- Cada operacion tiene un identificador unico.
- Las operaciones deben ser idempotentes.
- Enviar el valor de purchased, no una orden de toggle.
- No eliminar cambios pendientes sin confirmacion.
- No sobrescribir cambios locales durante importSnapshot.

## Conflictos

Pendiente de validacion.

Propuesta inicial: gana la ultima operacion
aceptada por el servidor.

## Pendiente

- Formato de operaciones.
- Versionado.
- Orden de procesamiento.
- Reintentos.
- Tratamiento de operaciones rechazadas.
- Eliminacion de productos.
- Pruebas de concurrencia.
