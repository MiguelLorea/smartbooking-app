package com.smartbooking.app.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.smartbooking.app.R;
import com.smartbooking.app.api.ApiClient;
import com.smartbooking.app.model.Reserva;
import com.smartbooking.app.utils.Llamadas;
import com.smartbooking.app.utils.Validadores;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservaFormActivity extends BaseActivity {
    private int reservaId = -1;
    private TextInputLayout tilFecha;
    private TextInputLayout tilInicio;
    private TextInputLayout tilFin;
    private TextInputLayout tilMotivo;
    private TextInputEditText txtFecha;
    private TextInputEditText txtInicio;
    private TextInputEditText txtFin;
    private TextInputEditText txtMotivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_form);
        reservaId = getIntent().getIntExtra("reserva_id", -1);
        toolbar(reservaId > 0 ? "Editar reserva" : "Nueva reserva", true);

        tilFecha = findViewById(R.id.tilFecha);
        tilInicio = findViewById(R.id.tilInicio);
        tilFin = findViewById(R.id.tilFin);
        tilMotivo = findViewById(R.id.tilMotivo);
        txtFecha = findViewById(R.id.txtFecha);
        txtInicio = findViewById(R.id.txtInicio);
        txtFin = findViewById(R.id.txtFin);
        txtMotivo = findViewById(R.id.txtMotivo);

        txtFecha.setOnClickListener(v -> elegirFecha());
        txtInicio.setOnClickListener(v -> elegirHora(txtInicio));
        txtFin.setOnClickListener(v -> elegirHora(txtFin));
        findViewById(R.id.btnGuardar).setOnClickListener(v -> guardar());

        if (reservaId > 0) {
            Llamadas.pedir(this, ApiClient.get(this).detalleReserva(reservaId), findViewById(R.id.progress), reserva -> {
                txtFecha.setText(reserva.fecha);
                txtInicio.setText(corta(reserva.hora_inicio));
                txtFin.setText(corta(reserva.hora_fin));
                txtMotivo.setText(reserva.motivo);
            });
        }
    }

    private void elegirFecha() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) -> {
            txtFecha.setText(String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, day));
            tilFecha.setError(null);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void elegirHora(TextInputEditText destino) {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(this, (view, hour, minute) -> {
            destino.setText(String.format(Locale.US, "%02d:%02d", hour, minute));
            tilInicio.setError(null);
            tilFin.setError(null);
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    private void guardar() {
        tilFecha.setError(null);
        tilInicio.setError(null);
        tilFin.setError(null);
        String fecha = valor(txtFecha);
        String inicio = valor(txtInicio);
        String fin = valor(txtFin);
        if (fecha.isEmpty()) {
            tilFecha.setError("Elegi una fecha");
            return;
        }
        String errorHorario = Validadores.horario(inicio, fin);
        if (errorHorario != null) {
            tilFin.setError(errorHorario);
            return;
        }
        if (Validadores.esPasada(fecha, inicio)) {
            tilFecha.setError("No se puede reservar en el pasado");
            return;
        }

        Reserva reserva = new Reserva();
        reserva.fecha = fecha;
        reserva.hora_inicio = inicio;
        reserva.hora_fin = fin;
        reserva.motivo = valor(txtMotivo);

        Call<Reserva> call;
        if (reservaId > 0) {
            call = ApiClient.get(this).editarReserva(reservaId, reserva);
        } else {
            call = ApiClient.get(this).crearReserva(reserva);
        }
        findViewById(R.id.progress).setVisibility(android.view.View.VISIBLE);
        call.enqueue(new Callback<Reserva>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                findViewById(R.id.progress).setVisibility(android.view.View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(ReservaFormActivity.this, "Reserva guardada", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (response.code() == 401) {
                    Llamadas.mostrarError(ReservaFormActivity.this, response);
                } else {
                    String msg = Llamadas.texto(response);
                    if (msg.toLowerCase().contains("franja") || msg.toLowerCase().contains("inicio")) {
                        tilInicio.setError(msg);
                    } else if (msg.toLowerCase().contains("fin")) {
                        tilFin.setError(msg);
                    } else {
                        tilFecha.setError(msg);
                    }
                }
            }

            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {
                findViewById(R.id.progress).setVisibility(android.view.View.GONE);
                Toast.makeText(ReservaFormActivity.this, "Sin conexion con el servidor", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String valor(TextInputEditText input) {
        if (input.getText() == null) {
            return "";
        }
        return input.getText().toString().trim();
    }

    private String corta(String hora) {
        if (hora != null && hora.length() >= 5) {
            return hora.substring(0, 5);
        }
        return hora == null ? "" : hora;
    }
}
