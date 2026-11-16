# Ciberseguridad

Use JWT (SimpleJWT) y no sesion de Django porque la app es Android: no guarda cookie, manda el access en el header. El access dura 30 minutos. El refresh dura 1 dia, se rota y al hacer logout se mete en la blacklist. No uso OAuth porque aca no hay login con Google ni otro proveedor.

## Politicas

| Politica | Que previene | Donde esta | Control | Quien |
|---|---|---|---|---|
| Contraseña de mas de 8, con letras, numeros y un caracter especial | fuerza bruta y claves obvias | `reservas/validators.py` y `Validadores.java` | test AUT-API-02 y AUT-UNIT-01 | Dev Backend |
| JWT con access corto, refresh y logout que lo invalida | robo de sesion si alguien copia un token viejo | `settings.py` SIMPLE_JWT, `LogoutView` | probar logout y volver a usar el refresh | Dev Backend |
| Rol y duenio | IDOR, un usuario tocando reservas ajenas o el ABM de usuarios | `permissions.py`, `get_queryset` | AUT-API-05 y el 403 de `/api/usuarios/` | Dev Backend |
| Secretos en `.env`, no en git | que se filtre la SECRET_KEY | `.env` en `.gitignore`, `settings.py` lee la variable | mirar el repo antes de cada merge | Dev Backend |
| HTTPS o, en local, cleartext solo al host de desarrollo | escuchar el trafico en la red | `network_security_config.xml` | la app no abre http hacia otros hosts | Dev Movil |
| Token en EncryptedSharedPreferences, la clave no se guarda | leer el token si alguien abre el almacenamiento | `SessionManager.java` | no hay password en SharedPreferences | Dev Movil |
| Logs de login fallido, 403 y cambios de admin, sin clave ni token | no enterarse de un intento de acceso | `views.py`, `excepciones.py`, `logs/seguridad.log` | el test `test_log_sin_password` | Dev Backend |

## Requerimiento, riesgo, politica

| Requerimiento | Riesgo | Politica | Evidencia |
|---|---|---|---|
| Iniciar sesion | fuerza bruta, robo de clave | password fuerte, JWT, log de fallos | `validators.py`, `LoginView`, AUT-API-02 |
| Ver mis reservas (RN04) | IDOR | queryset filtrado y `EsDuenioOAdmin` | `views.py`, AUT-API-05 |
| Gestionar usuarios (RN05) | escalada de privilegios | `EsAdmin` | `permissions.py`, test 403 |
| Cerrar sesion | reutilizar el refresh | blacklist | `LogoutView` |

Los 3 casos de seguridad estan en la planilla: TC02, TC03 y TC08.

El backend no esta desplegado en HTTPS. Para la defensa corre en la PC. Por eso el cleartext queda limitado a localhost y al `10.0.2.2` del emulador.
