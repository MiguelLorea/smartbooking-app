package com.smartbooking.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.smartbooking.app.model.TokenResponse;

public class SessionManager {
    private static SharedPreferences prefs;

    private static SharedPreferences prefs(Context context) {
        if (prefs == null) {
            try {
                String alias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
                prefs = EncryptedSharedPreferences.create(
                        "sesion",
                        alias,
                        context.getApplicationContext(),
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );
            } catch (Exception e) {
                throw new RuntimeException("No pude abrir la sesion", e);
            }
        }
        return prefs;
    }

    public static void guardar(Context context, TokenResponse token) {
        prefs(context).edit()
                .putString("access", token.access)
                .putString("refresh", token.refresh)
                .putString("rol", token.rol)
                .putString("username", token.username)
                .putInt("id", token.id)
                .apply();
    }

    public static String getAccess(Context context) {
        return prefs(context).getString("access", null);
    }

    public static String getRefresh(Context context) {
        return prefs(context).getString("refresh", null);
    }

    public static String getRol(Context context) {
        return prefs(context).getString("rol", "");
    }

    public static int getUserId(Context context) {
        return prefs(context).getInt("id", -1);
    }

    public static boolean haySesion(Context context) {
        String token = getAccess(context);
        return token != null && !token.isEmpty();
    }

    public static void borrar(Context context) {
        prefs(context).edit().clear().apply();
    }
}
