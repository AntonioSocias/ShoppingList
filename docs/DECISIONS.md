# Registro de decisiones tecnicas

## Confirmadas

- Android desarrollado en Java.
- SQLite como almacenamiento local.
- Python para el backend.
- PostgreSQL como base de datos remota.
- Docker Compose para desarrollo local.

## Implementadas en el entorno inicial

- FastAPI.
- SQLAlchemy.
- Python 3.12.
- PostgreSQL 17.
- Endpoints de comprobacion.
- Conexion entre API y PostgreSQL.

## Propuestas pendientes de validacion

- API REST versionada.
- Autenticacion JWT.
- Migraciones Alembic.
- Sincronizacion mediante operaciones y snapshots.
- Idempotencia mediante identificadores de operacion.
- Resolucion de conflictos mediante orden del servidor.
- WorkManager para sincronizacion Android.

## Nuevas decisiones

Registrar fecha, decision, motivo y componentes afectados.

### 2026-11-25 — Identidad de los agentes (aprobada por OWNER)

- BACKEND: agente responsable del servidor Python y arquitectura general.
- FRONT: agente responsable de Android.
- OWNER: propietario y responsable de validar decisiones.
- SHARED: tareas o decisiones conjuntas.
- Motivo: simplificar referencias y mantener identidades independientes de la herramienta utilizada.
- Componentes afectados: coordinación y documentación compartida; sin cambios funcionales.
