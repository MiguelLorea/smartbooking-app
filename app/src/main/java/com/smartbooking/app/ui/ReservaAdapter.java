package com.smartbooking.app.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartbooking.app.R;
import com.smartbooking.app.model.Reserva;

import java.util.ArrayList;
import java.util.List;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.Holder> {
    public interface Click {
        void abrir(Reserva reserva);
    }

    private final List<Reserva> datos = new ArrayList<>();
    private final Click click;

    public ReservaAdapter(Click click) {
        this.click = click;
    }

    public void setDatos(List<Reserva> nuevas) {
        datos.clear();
        datos.addAll(nuevas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);
        return new Holder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Reserva reserva = datos.get(position);
        holder.fecha.setText(reserva.fecha);
        holder.horario.setText(corta(reserva.hora_inicio) + " a " + corta(reserva.hora_fin));
        String motivo = reserva.motivo == null || reserva.motivo.isEmpty() ? "Sin motivo" : reserva.motivo;
        if (reserva.usuario_nombre != null && !reserva.usuario_nombre.isEmpty()) {
            motivo = motivo + " · " + reserva.usuario_nombre;
        }
        holder.motivo.setText(motivo);
        holder.itemView.setOnClickListener(v -> click.abrir(reserva));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    private String corta(String hora) {
        if (hora != null && hora.length() >= 5) {
            return hora.substring(0, 5);
        }
        return hora;
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView fecha;
        TextView horario;
        TextView motivo;

        Holder(View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.txtFecha);
            horario = itemView.findViewById(R.id.txtHorario);
            motivo = itemView.findViewById(R.id.txtMotivo);
        }
    }
}
