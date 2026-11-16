package com.smartbooking.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartbooking.app.R;
import com.smartbooking.app.api.ApiClient;
import com.smartbooking.app.utils.Llamadas;

import java.util.ArrayList;

public class ReservasActivity extends BaseActivity {
    private ReservaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);
        toolbar("Reservas", true);

        adapter = new ReservaAdapter(reserva -> {
            Intent intent = new Intent(this, ReservaDetalleActivity.class);
            intent.putExtra("reserva_id", reserva.id);
            startActivity(intent);
        });
        RecyclerView lista = findViewById(R.id.lista);
        lista.setLayoutManager(new LinearLayoutManager(this));
        lista.setAdapter(adapter);

        findViewById(R.id.btnNueva).setOnClickListener(v ->
                startActivity(new Intent(this, ReservaFormActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargar();
    }

    private void cargar() {
        TextView vacio = findViewById(R.id.vacio);
        Llamadas.pedir(this, ApiClient.get(this).reservas(), findViewById(R.id.progress), reservas -> {
            if (reservas == null || reservas.isEmpty()) {
                adapter.setDatos(new ArrayList<>());
                vacio.setVisibility(View.VISIBLE);
            } else {
                vacio.setVisibility(View.GONE);
                adapter.setDatos(reservas);
            }
        });
    }
}
