# Accesibilidad

Mire el flujo login → inicio → nueva reserva, que es el camino critico.

Orden de lectura, de arriba hacia abajo:

1. Logo "Logo de SmartBooking" y el nombre de la app.
2. Campo "Usuario o email".
3. Campo "Contraseña".
4. Boton "Entrar".
5. Boton "Crear cuenta".
6. En el inicio, los botones del menu (Mis reservas, Nueva reserva, Mi perfil, La sala, Contacto, Cerrar sesion). El de Usuarios solo si es admin.
7. En el formulario: Fecha, Hora de inicio, Hora de fin, Motivo, Guardar.

Los campos usan el hint del `TextInputLayout`, asi TalkBack dice el nombre del campo. Los botones tienen texto visible y miden 48dp de alto.

No cumple del todo: la imagen de la sala (`SalaActivity`, `imgSala`) no tiene `contentDescription`. TalkBack la salta o dice "imagen" sin contexto. Lo deje como bug de accesibilidad (WCAG 2.2, criterio 1.1.1).

El resto del flujo se puede recorrer sin quedar trabado en un control sin nombre.
