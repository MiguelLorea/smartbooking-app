package com.smartbooking.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.smartbooking.app.R;
import com.smartbooking.app.api.ApiClient;
import com.smartbooking.app.model.LoginRequest;
import com.smartbooking.app.utils.Llamadas;
import com.smartbooking.app.utils.SessionManager;

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputLayout tilUser = findViewById(R.id.tilUser);
        TextInputLayout tilPass = findViewById(R.id.tilPass);
        TextInputEditText txtUser = findViewById(R.id.txtUser);
        TextInputEditText txtPass = findViewById(R.id.txtPass);
        Button btnEntrar = findViewById(R.id.btnEntrar);
        Button btnRegistro = findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(v ->
                startActivity(new Intent(this, RegistroActivity.class)));

        btnEntrar.setOnClickListener(v -> {
            tilUser.setError(null);
            tilPass.setError(null);
            String user = texto(txtUser);
            String pass = texto(txtPass);
            if (user.isEmpty()) {
                tilUser.setError("Completa el usuario");
                return;
            }
            if (pass.isEmpty()) {
                tilPass.setError("Completa la contraseña");
                return;
            }
            Llamadas.pedir(this, ApiClient.get(this).login(new LoginRequest(user, pass)), findViewById(R.id.progress), token -> {
                SessionManager.guardar(this, token);
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            });
        });
    }

    private String texto(TextInputEditText input) {
        if (input.getText() == null) {
            return "";
        }
        return input.getText().toString().trim();
    }
}
