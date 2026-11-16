# Defensa (10 minutos)

1. Admin entra, abre reservas, crea o edita una, y en Usuarios desactiva a alguien que no sea el (despues lo vuelve a activar).
2. Sale y entra como `usuario`. Crea una reserva, la ve en la lista, abre el detalle.
3. Con Postman, con el token del usuario, pido la reserva que creo el admin. Tiene que dar 404. Si quiero el 403, pido `/api/usuarios/`.
4. En el admin de Django muestro la reserva que acabo de crear en la app.
5. Si preguntan codigo:
   - El token sale de `LoginView` y la app lo manda en `AuthInterceptor`.
   - Se guarda en `SessionManager` (EncryptedSharedPreferences). La clave no se guarda.
   - RN01 esta en `ReservaSerializer.validate`: misma fecha y franjas que se pisan.
   - `EsAdmin` y `EsDuenioOAdmin` estan en `permissions.py`. El listado filtra en `get_queryset`.
   - El JWT tiene header, payload y firma. Cuando vence el access, la API responde 401 y la app vuelve al login. El refresh pide otro access. El logout lo pone en la blacklist.
   - La SECRET_KEY esta en `.env`, no en el repo.
   - En el log no va la clave ni el token. El test `test_log_sin_password` lo revisa.
   - Un test se sigue asi: AUT-API-01 esta en la coleccion, en la planilla es TC01, y la historia es US01.
