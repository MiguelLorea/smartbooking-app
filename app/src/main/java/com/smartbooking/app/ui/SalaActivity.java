package com.smartbooking.app.ui;

import android.os.Bundle;

import com.smartbooking.app.R;

public class SalaActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala);
        toolbar("La sala", true);
    }
}
