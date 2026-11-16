package com.smartbooking.app.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.smartbooking.app.ui.LoginActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Llamadas {
    public interface Ok<T> {
        void listo(T body);
    }

    public static <T> void pedir(Activity activity, Call<T> call, View progress, Ok<T> ok) {
        if (progress != null) {
            progress.setVisibility(View.VISIBLE);
        }
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (activity.isFinishing()) {
                    return;
                }
                if (progress != null) {
                    progress.setVisibility(View.GONE);
                }
                if (response.isSuccessful()) {
                    ok.listo(response.body());
                } else {
                    mostrarError(activity, response);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (activity.isFinishing()) {
                    return;
                }
                if (progress != null) {
                    progress.setVisibility(View.GONE);
                }
                Toast.makeText(activity, "Sin conexion con el servidor", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void mostrarError(Activity activity, Response<?> response) {
        if (response.code() == 401 && !(activity instanceof LoginActivity)) {
            SessionManager.borrar(activity);
            activity.startActivity(new Intent(activity, LoginActivity.class));
            activity.finish();
            return;
        }
        Toast.makeText(activity, texto(response), Toast.LENGTH_LONG).show();
    }

    public static String texto(Response<?> response) {
        try {
            if (response.errorBody() == null) {
                return "Error " + response.code();
            }
            JSONObject obj = new JSONObject(response.errorBody().string());
            if (obj.has("detail")) {
                return valor(obj.get("detail"));
            }
            Iterator<String> keys = obj.keys();
            if (keys.hasNext()) {
                String key = keys.next();
                return valor(obj.get(key));
            }
        } catch (Exception ignored) {
        }
        return "Error " + response.code();
    }

    private static String valor(Object value) throws Exception {
        if (value instanceof JSONArray) {
            JSONArray arr = (JSONArray) value;
            if (arr.length() > 0) {
                return arr.getString(0);
            }
        }
        return String.valueOf(value);
    }
}
