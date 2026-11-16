# Diseño

Una sala. Dos tablas.

Usuario 1 — N Reserva. El usuario tiene rol (`admin` o `usuario`) y `is_active`. La reserva tiene fecha, hora de inicio, hora de fin y motivo.

DER: [der.svg](https://github.com/MiguelLorea/smartbooking-app/blob/main/docs/der.svg)

El script MySQL esta en el backend: `docs/schema.sql`. Para correr el proyecto uso SQLite porque no hace falta un servidor aparte. Si se cambia `DB_ENGINE=mysql` en el `.env`, Django usa MySQL.

## Clases de la app (lo importante)

- `LoginActivity`, `RegistroActivity`, `HomeActivity`
- `ReservasActivity`, `ReservaFormActivity`, `ReservaDetalleActivity`
- `PerfilActivity`, `UsuariosActivity`, `SalaActivity`, `ContactoActivity`
- `ApiClient` + `AuthInterceptor`
- `SessionManager` guarda el token

Las pantallas son un toolbar azul, un formulario o una lista, y botones grandes. No hay un Figma aparte: el layout es el que esta en `res/layout`.
