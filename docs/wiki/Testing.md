# Testing

## Plan de pruebas

Alcance: login, registro, CRUD de reservas, permisos de admin y las reglas RN01 a RN05. No probe la web Angular porque no hay frontend web, solo el admin de Django.

Tipos: funcional, negativa, seguridad, unitaria y una pasada de accesibilidad. No automatice la UI.

Entorno: API Django en local, app minSdk 21 / compileSdk 34, pruebas unitarias con Gradle en la PC. El emulador o el celular se usan en la defensa.

Herramientas: JUnit, Postman, Newman, Django `manage.py test`.

Entrada: la API levanta y migran las tablas. Salida: los tests automaticos en verde y la planilla completa. Riesgo: si el puerto 8000 esta ocupado hay que cambiar la URL.

## Piramide

- Unitaria JVM: 4 tests en `ValidadoresTest` (password, horario, fecha pasada). Son baratos y no necesitan red.
- API: la coleccion `tests/api/smartbooking.postman_collection.json` (auth, reservas, usuarios).
- UI instrumentada: no. El flujo de login y alta se prueba a mano. Armar Espresso me llevaba mas tiempo que correrlo las pocas veces de este final.
- E2E: no automatico. En la defensa instalo el APK y hago el recorrido.
- Accesibilidad: revision manual, ver Accesibilidad.

## Como correrlas

```bash
cd smartbooking-backend
./venv/bin/python manage.py test

cd smartbooking-app
./gradlew test
npx newman run tests/api/smartbooking.postman_collection.json
```

La API tiene que estar corriendo para Newman. Si no es el puerto 8000: `newman run ... --env-var baseUrl=http://127.0.0.1:8011`

## Planilla y matriz

- [test-cases.csv](https://github.com/MiguelLorea/smartbooking-app/blob/main/docs/test-cases.csv)
- [matriz.csv](https://github.com/MiguelLorea/smartbooking-app/blob/main/docs/matriz.csv)

El tablero esta en el proyecto de GitHub del repo. Tambien deje una copia en `docs/kanban.md`.
