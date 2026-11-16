package com.smartbooking.app.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.smartbooking.app.R;

public class BaseActivity extends AppCompatActivity {
    protected void toolbar(String titulo, boolean atras) {
        Toolbar barra = findViewById(R.id.toolbar);
        setSupportActionBar(barra);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titulo);
            getSupportActionBar().setDisplayHomeAsUpEnabled(atras);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
