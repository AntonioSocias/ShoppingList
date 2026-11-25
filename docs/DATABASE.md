# Diseno de la base de datos

## Motor

PostgreSQL 17.

## Gestion del esquema

SQLAlchemy y migraciones Alembic.

## Entidades propuestas

- users
- households
- household_members
- products
- processed_operations

## Modelo actual conocido de producto

- id
- name
- purchased

## Campos adicionales propuestos

- household_id
- revision

## Pendiente

- Tipos de datos.
- Claves primarias.
- Claves foraneas.
- Restricciones.
- Indices.
- Relaciones.
- Migraciones.
- Transacciones de sincronizacion.

No crear tablas manualmente sin registrar
la correspondiente migracion.
