package com.smartbooking.app.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.smartbooking.app.R;
import com.smartbooking.app.api.ApiClient;
import com.smartbooking.app.model.Reserva;
import com.smartbooking.app.utils.Llamadas;

public class ReservaDetalleActivity extends BaseActivity {
    private int reservaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_detalle);
        toolbar("Detalle", true);
        reservaId = getIntent().getIntExtra("reserva_id", -1);
        if (reservaId < 0) {
            finish();
            return;
        }
        findViewById(R.id.btnEditar).setOnClickListener(v -> {
            Intent intent = new Intent(this, ReservaFormActivity.class);
            intent.putExtra("reserva_id", reservaId);
            startActivity(intent);
        });
        findViewById(R.id.btnCancelar).setOnClickListener(v -> confirmar());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (reservaId > 0) {
            cargar();
        }
    }

    private void cargar() {
        Llamadas.pedir(this, ApiClient.get(this).detalleReserva(reservaId), findViewById(R.id.progress), this::mostrar);
    }

    private void mostrar(Reserva reserva) {
        TextView fecha = findViewById(R.id.txtFecha);
        TextView horario = findViewById(R.id.txtHorario);
        TextView motivo = findViewById(R.id.txtMotivo);
        TextView usuario = findViewById(R.id.txtUsuario);
        fecha.setText(reserva.fecha);
        String inicio = reserva.hora_inicio == null ? "" : reserva.hora_inicio;
        String fin = reserva.hora_fin == null ? "" : reserva.hora_fin;
        horario.setText(inicio + " a " + fin);
        motivo.setText(reserva.motivo == null || reserva.motivo.isEmpty() ? "Sin motivo" : reserva.motivo);
        usuario.setText(reserva.usuario_nombre == null ? "" : reserva.usuario_nombre);
    }

    private void confirmar() {
        new AlertDialog.Builder(this)
                .setTitle("Cancelar reserva")
                .setMessage("Seguro que queres cancelar esta reserva?")
                .setPositiveButton("Si, cancelar", (dialog, which) -> borrar())
                .setNegativeButton("Volver", null)
                .show();
    }

    private void borrar() {
        Llamadas.pedir(this, ApiClient.get(this).borrarReserva(reservaId), findViewById(R.id.progress), body -> {
            Toast.makeText(this, "Reserva cancelada", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
