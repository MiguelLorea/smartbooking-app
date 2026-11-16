package com.smartbooking.app.ui;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.smartbooking.app.R;
import com.smartbooking.app.api.ApiClient;
import com.smartbooking.app.model.RegistroRequest;
import com.smartbooking.app.utils.Llamadas;
import com.smartbooking.app.utils.Validadores;

public class RegistroActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        toolbar("Crear cuenta", true);

        TextInputLayout tilUser = findViewById(R.id.tilUser);
        TextInputLayout tilEmail = findViewById(R.id.tilEmail);
        TextInputLayout tilNombre = findViewById(R.id.tilNombre);
        TextInputLayout tilApellido = findViewById(R.id.tilApellido);
        TextInputLayout tilPass = findViewById(R.id.tilPass);
        TextInputLayout tilPass2 = findViewById(R.id.tilPass2);
        TextInputEditText txtUser = findViewById(R.id.txtUser);
        TextInputEditText txtEmail = findViewById(R.id.txtEmail);
        TextInputEditText txtNombre = findViewById(R.id.txtNombre);
        TextInputEditText txtApellido = findViewById(R.id.txtApellido);
        TextInputEditText txtPass = findViewById(R.id.txtPass);
        TextInputEditText txtPass2 = findViewById(R.id.txtPass2);
        Button btnCrear = findViewById(R.id.btnCrear);

        btnCrear.setOnClickListener(v -> {
            tilUser.setError(null);
            tilEmail.setError(null);
            tilPass.setError(null);
            tilPass2.setError(null);

            String pass = valor(txtPass);
            String errorPass = Validadores.password(pass);
            if (valor(txtUser).isEmpty()) {
                tilUser.setError("Completa el usuario");
                return;
            }
            if (!valor(txtEmail).contains("@")) {
                tilEmail.setError("Email invalido");
                return;
            }
            if (errorPass != null) {
                tilPass.setError(errorPass);
                return;
            }
            if (!pass.equals(valor(txtPass2))) {
                tilPass2.setError("Las contraseñas no coinciden");
                return;
            }

            RegistroRequest datos = new RegistroRequest();
            datos.username = valor(txtUser);
            datos.email = valor(txtEmail);
            datos.first_name = valor(txtNombre);
            datos.last_name = valor(txtApellido);
            datos.password = pass;

            Llamadas.pedir(this, ApiClient.get(this).registro(datos), findViewById(R.id.progress), body -> {
                android.widget.Toast.makeText(this, "Cuenta creada, ahora entra", android.widget.Toast.LENGTH_LONG).show();
                finish();
            });
        });
    }

    private String valor(TextInputEditText input) {
        if (input.getText() == null) {
            return "";
        }
        return input.getText().toString().trim();
    }
}
