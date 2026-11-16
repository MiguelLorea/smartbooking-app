package com.smartbooking.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartbooking.app.R;
import com.smartbooking.app.api.ApiClient;
import com.smartbooking.app.utils.Llamadas;
import com.smartbooking.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsuariosActivity extends BaseActivity {
    private UsuarioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!"admin".equals(SessionManager.getRol(this))) {
            finish();
            return;
        }
        setContentView(R.layout.activity_usuarios);
        toolbar("Usuarios", true);
        adapter = new UsuarioAdapter(SessionManager.getUserId(this), new UsuarioAdapter.Accion() {
            @Override
            public void cambiarRol(com.smartbooking.app.model.Usuario usuario) {
                Map<String, Object> datos = new HashMap<>();
                datos.put("rol", "admin".equals(usuario.rol) ? "usuario" : "admin");
                patch(usuario.id, datos);
            }

            @Override
            public void cambiarActivo(com.smartbooking.app.model.Usuario usuario) {
                Map<String, Object> datos = new HashMap<>();
                datos.put("is_active", !usuario.is_active);
                patch(usuario.id, datos);
            }
        });
        RecyclerView lista = findViewById(R.id.lista);
        lista.setLayoutManager(new LinearLayoutManager(this));
        lista.setAdapter(adapter);
        cargar();
    }

    private void patch(int id, Map<String, Object> datos) {
        Llamadas.pedir(this, ApiClient.get(this).actualizarUsuario(id, datos), findViewById(R.id.progress), body -> {
            Toast.makeText(this, "Usuario actualizado", Toast.LENGTH_SHORT).show();
            cargar();
        });
    }

    private void cargar() {
        TextView vacio = findViewById(R.id.vacio);
        Llamadas.pedir(this, ApiClient.get(this).usuarios(), findViewById(R.id.progress), usuarios -> {
            if (usuarios == null || usuarios.isEmpty()) {
                adapter.setDatos(new ArrayList<>());
                vacio.setVisibility(View.VISIBLE);
            } else {
                vacio.setVisibility(View.GONE);
                adapter.setDatos(usuarios);
            }
        });
    }
}
