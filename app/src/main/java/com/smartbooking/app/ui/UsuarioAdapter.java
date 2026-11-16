package com.smartbooking.app.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartbooking.app.R;
import com.smartbooking.app.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.Holder> {
    public interface Accion {
        void cambiarRol(Usuario usuario);
        void cambiarActivo(Usuario usuario);
    }

    private final List<Usuario> datos = new ArrayList<>();
    private final int miId;
    private final Accion accion;

    public UsuarioAdapter(int miId, Accion accion) {
        this.miId = miId;
        this.accion = accion;
    }

    public void setDatos(List<Usuario> nuevas) {
        datos.clear();
        datos.addAll(nuevas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new Holder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Usuario usuario = datos.get(position);
        holder.nombre.setText(usuario.username);
        String rol = "admin".equals(usuario.rol) ? "Administrador" : "Usuario";
        String estado = usuario.is_active ? "activo" : "inactivo";
        holder.detalle.setText(usuario.email + " · " + rol + " · " + estado);
        holder.btnRol.setText("admin".equals(usuario.rol) ? "Pasar a usuario" : "Pasar a admin");
        holder.btnActivo.setText(usuario.is_active ? "Desactivar" : "Activar");
        boolean soyYo = usuario.id == miId;
        holder.btnRol.setEnabled(!soyYo);
        holder.btnActivo.setEnabled(!soyYo);
        holder.btnRol.setOnClickListener(v -> accion.cambiarRol(usuario));
        holder.btnActivo.setOnClickListener(v -> accion.cambiarActivo(usuario));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView detalle;
        Button btnRol;
        Button btnActivo;

        Holder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombre);
            detalle = itemView.findViewById(R.id.txtDetalle);
            btnRol = itemView.findViewById(R.id.btnRol);
            btnActivo = itemView.findViewById(R.id.btnActivo);
        }
    }
}
