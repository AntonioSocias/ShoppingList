from fastapi import FastAPI, HTTPException
from sqlalchemy import text
from sqlalchemy.exc import SQLAlchemyError

from app.database import engine


app = FastAPI(
    title="ShoppingList API",
    description="Backend para listas de la compra compartidas",
    version="0.1.0"
)


@app.get("/")
def root():
    return {
        "app": "ShoppingList",
        "version": "0.1.0",
        "status": "running"
    }


@app.get("/health")
def health():
    try:
        with engine.connect() as connection:
            connection.execute(text("SELECT 1"))

        return {
            "api": "online",
            "database": "connected"
        }

    except SQLAlchemyError:
        raise HTTPException(
            status_code=503,
            detail="Database unavailable"
        )
