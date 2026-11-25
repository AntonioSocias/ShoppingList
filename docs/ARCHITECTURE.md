# Arquitectura de ShoppingList

## Android

- Java.
- SQLite.
- Android Studio.
- Arquitectura offline-first.

## Backend

- Python 3.12.
- FastAPI.
- PostgreSQL 17.
- SQLAlchemy.
- Alembic.
- Docker Compose.

## Comunicacion

- API REST.
- Formato JSON.
- HTTP durante desarrollo local.
- HTTPS obligatorio en produccion.

## Principios

SQLite almacena el estado local de la aplicacion.
PostgreSQL almacena el estado compartido del hogar.

La aplicacion debe poder funcionar sin conexion.

## Entorno de desarrollo

- API: puerto 8000.
- PostgreSQL: puerto 5432.
- Swagger: http://localhost:8000/docs
- Health: http://localhost:8000/health

## Arquitectura del servidor

Propuesta: monolito modular.
