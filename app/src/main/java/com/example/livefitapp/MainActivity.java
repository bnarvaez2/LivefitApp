package com.example.livefitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import android.view.View;

import java.math.MathContext;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private EditText vPltura;
    private EditText vPeso;
    public static final String EXTRA_IMC ="com.example.android.LivefitApp.extra.IMC";
    public static final String EXTRA_ALTURA ="com.example.android.LivefitApp.extra.ALTURA";
    public static final String EXTRA_PESO ="com.example.android.LivefitApp.extra.PESO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vPltura = findViewById(R.id.txtAltura);
        vPeso = findViewById(R.id.txtPeso);
    }

    public void calcularIMC(View view){
        if(!(vPltura.getText().toString().isEmpty())){
            if(!(vPeso.getText().toString().isEmpty())){
                double altura = Double.parseDouble(vPltura.getText().toString());
                double peso = Double.parseDouble(vPeso.getText().toString());
                double IMC = peso/Math.pow(altura,2);
                Intent intent = new Intent(this, Resultado.class);
                intent.putExtra(EXTRA_IMC, IMC+"");
                intent.putExtra(EXTRA_ALTURA, altura+"");
                intent.putExtra(EXTRA_PESO, peso+"");
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), "Ingrese un peso.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Ingrese una altura.", Toast.LENGTH_SHORT).show();
        }
    }
}
