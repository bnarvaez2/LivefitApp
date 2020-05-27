package com.example.livefitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText vPltura;
    private EditText vPeso;
    private TextView signOut;
    String user;

    FirebaseAuth mAuth;

    public static final String EXTRA_IMC = "com.example.android.LivefitApp.extra.IMC";
    public static final String EXTRA_ALTURA = "com.example.android.LivefitApp.extra.ALTURA";
    public static final String EXTRA_PESO = "com.example.android.LivefitApp.extra.PESO";
    public static final String EXTRA_USER = "com.example.android.LivefitApp.extra.USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        user = intent.getStringExtra(login.EXTRA_EMAIL);
        mAuth = FirebaseAuth.getInstance();

        Toast.makeText(this, "Email: "+user, Toast.LENGTH_SHORT).show();
        vPltura = findViewById(R.id.txtAltura);
        vPeso = findViewById(R.id.txtPeso);
        signOut = findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void calcularIMC(View view) {
        if (!(vPltura.getText().toString().isEmpty())) {
            if (!(vPeso.getText().toString().isEmpty())) {
                double altura = Double.parseDouble(vPltura.getText().toString());
                double peso = Double.parseDouble(vPeso.getText().toString());
                double IMC = peso / Math.pow(altura, 2);
                Intent intent = new Intent(this, Resultado.class);
                intent.putExtra(EXTRA_IMC, IMC + "");
                intent.putExtra(EXTRA_ALTURA, altura + "");
                intent.putExtra(EXTRA_PESO, peso + "");
                intent.putExtra(EXTRA_USER, user + "");
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Ingrese un peso.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Ingrese una altura.", Toast.LENGTH_SHORT).show();
        }
    }

    public void verHistorial(View view) {
        Intent intent = new Intent(this, HistorialView.class);
        intent.putExtra(EXTRA_USER, user + "");
        startActivity(intent);
    }
}
