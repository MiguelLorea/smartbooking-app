package com.smartbooking.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.smartbooking.app.R;
import com.smartbooking.app.api.ApiClient;
import com.smartbooking.app.utils.SessionManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar("SmartBooking", false);

        if (!"admin".equals(SessionManager.getRol(this))) {
            findViewById(R.id.btnUsuarios).setVisibility(View.GONE);
        }

        findViewById(R.id.btnReservas).setOnClickListener(v ->
                startActivity(new Intent(this, ReservasActivity.class)));
        findViewById(R.id.btnNueva).setOnClickListener(v ->
                startActivity(new Intent(this, ReservaFormActivity.class)));
        findViewById(R.id.btnPerfil).setOnClickListener(v ->
                startActivity(new Intent(this, PerfilActivity.class)));
        findViewById(R.id.btnUsuarios).setOnClickListener(v ->
                startActivity(new Intent(this, UsuariosActivity.class)));
        findViewById(R.id.btnSala).setOnClickListener(v ->
                startActivity(new Intent(this, SalaActivity.class)));
        findViewById(R.id.btnContacto).setOnClickListener(v ->
                startActivity(new Intent(this, ContactoActivity.class)));
        findViewById(R.id.btnSalir).setOnClickListener(v -> salir());
    }

    private void salir() {
        Map<String, String> body = new HashMap<>();
        String refresh = SessionManager.getRefresh(this);
        body.put("refresh", refresh == null ? "" : refresh);
        findViewById(R.id.btnSalir).setEnabled(false);
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
    }

    private void irAlLogin() {
        SessionManager.borrar(this);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
