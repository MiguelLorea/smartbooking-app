package com.smartbooking.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.smartbooking.app.R;

public class ContactoActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        toolbar("Contacto", true);
        findViewById(R.id.btnMail).setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:hola@smartbooking.local"))));
        findViewById(R.id.btnTel).setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+541143210000"))));
        findViewById(R.id.btnMapa).setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Av.+Corrientes+1234,+CABA"))));
    }
}
