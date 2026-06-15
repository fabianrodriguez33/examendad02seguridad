# Examen Unidad 2 – DAD  |  ms-reserva

## Estructura

| Módulo | Puerto |
|---|---|
| ms-admin-config-server | 8888 |
| ms-admin-registry-server (Eureka) | 8761 |
| auth-service | 8081 |
| ms-reserva | 8082 |
| ms-admin-api-gateway | 8080 |

## Orden de arranque (IntelliJ)

1. `docker-compose up -d`  → levanta postgres-auth y mysql-reserva
2. ms-admin-registry-server
3. ms-admin-config-server
4. auth-service
5. ms-reserva
6. ms-admin-api-gateway

## Endpoints principales (por gateway → puerto 8080)

| Método | Ruta | Auth |
|---|---|---|
| POST | /api/auth/register | No |
| POST | /api/auth/login | No |
| GET | /api/reservas | Bearer token |
| POST | /api/reservas | Bearer token |
| GET | /api/reservas/{id} | Bearer token |
| PUT | /api/reservas/{id} | ADMIN |
| DELETE | /api/reservas/{id} | ADMIN |

## JWT secret

Mismo secret en auth-service y ms-reserva:
`MiClaveJWTSuperSeguraParaExamenUnidad2DAD2026!!`
