# SmartBooking

App de Android para reservar la sala de un coworking. Consume la API de Django que esta en el otro repo: https://github.com/MiguelLorea/smartbooking-backend

No arme la web en Angular. Para ver que la reserva quedo guardada uso el admin de Django (`/admin/`).

## Tecnologias

- App: Java, Android Studio, minSdk 21, Retrofit, Gson, OkHttp
- Token en el telefono: EncryptedSharedPreferences
- API: Django REST Framework, SimpleJWT
- Base: SQLite (el script de MySQL esta en el backend, `docs/schema.sql`)
- Pruebas: JUnit y Postman/Newman

## Como correrla

1. Instalar JDK 17 y Android SDK (compileSdk 34).
2. Copiar `local.properties.example` a `local.properties` y poner la ruta del SDK.
3. La URL de la API sale de `API_BASE_URL` en ese archivo. En el emulador es `http://10.0.2.2:8000/`.
4. Levantar el backend (ver el README de ese repo) y despues abrir el proyecto en Android Studio, o:

```bash
./gradlew assembleDebug
./gradlew test
```

El cleartext solo esta permitido hacia `10.0.2.2`, `localhost` y `127.0.0.1` (`network_security_config.xml`). `security-crypto` pide minSdk 23; lo force a 21 con `tools:overrideLibrary` porque el keystore existe desde API 18 y la consigna pide minSdk 21.

## Estructura

- `ui` activities y adapters
- `api` Retrofit, el interceptor del token
- `model` Usuario, Reserva
- `utils` sesion, validaciones, llamadas
- `tests/api` coleccion de Postman
- `docs` wiki, plan de pruebas, DER

## Endpoints que usa la app

- POST `/api/auth/register/` y `/api/auth/login/`
- POST `/api/auth/logout/` con el refresh
- GET y PUT `/api/perfil/`
- GET POST `/api/reservas/`
- GET PUT DELETE `/api/reservas/{id}/`
- GET y PATCH `/api/usuarios/` (solo admin)

## APK

Release: https://github.com/MiguelLorea/smartbooking-app/releases/tag/v1.0

## Usuarios de prueba

| Usuario | Clave | Rol |
|---|---|---|
| admin | Admin123! | administrador |
| usuario | Usuario123! | usuario |

Son usuarios de demo, no cuentas reales.
