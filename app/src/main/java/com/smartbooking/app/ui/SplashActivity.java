package com.smartbooking.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.smartbooking.app.R;
import com.smartbooking.app.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Class<?> destino = SessionManager.haySesion(this) ? HomeActivity.class : LoginActivity.class;
            startActivity(new Intent(this, destino));
            finish();
        }, 800);
    }
}
