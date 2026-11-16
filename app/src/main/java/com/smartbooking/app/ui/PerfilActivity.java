package com.smartbooking.app.ui;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.smartbooking.app.R;
import com.smartbooking.app.api.ApiClient;
import com.smartbooking.app.utils.Llamadas;
import com.smartbooking.app.utils.SessionManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends BaseActivity {
    private TextInputEditText txtNombre;
    private TextInputEditText txtApellido;
    private TextInputEditText txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        toolbar("Mi perfil", true);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtEmail = findViewById(R.id.txtEmail);
        TextInputLayout tilEmail = findViewById(R.id.tilEmail);

        Llamadas.pedir(this, ApiClient.get(this).perfil(), findViewById(R.id.progress), usuario -> {
            txtNombre.setText(usuario.first_name);
            txtApellido.setText(usuario.last_name);
            txtEmail.setText(usuario.email);
        });

        findViewById(R.id.btnGuardar).setOnClickListener(v -> {
            String email = valor(txtEmail);
            if (!email.contains("@")) {
                tilEmail.setError("Email invalido");
                return;
            }
            tilEmail.setError(null);
            Map<String, String> datos = new HashMap<>();
            datos.put("first_name", valor(txtNombre));
            datos.put("last_name", valor(txtApellido));
            datos.put("email", email);
            Llamadas.pedir(this, ApiClient.get(this).editarPerfil(datos), findViewById(R.id.progress), body ->
                    android.widget.Toast.makeText(this, "Perfil actualizado", android.widget.Toast.LENGTH_SHORT).show());
        });

        findViewById(R.id.btnSalir).setOnClickListener(v -> {
            Map<String, String> body = new HashMap<>();
            String refresh = SessionManager.getRefresh(this);
            body.put("refresh", refresh == null ? "" : refresh);
            ApiClient.get(this).logout(body).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    irAlLogin();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    irAlLogin();
                }
            });
        });
    }

    private void irAlLogin() {
        SessionManager.borrar(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private String valor(TextInputEditText input) {
        if (input.getText() == null) {
            return "";
        }
        return input.getText().toString().trim();
    }
}
