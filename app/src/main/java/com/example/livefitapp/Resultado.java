package com.example.livefitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Resultado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_IMC);
        DecimalFormat format = new DecimalFormat("#.##");
        Double IMC = Double.parseDouble(message);
        TextView categoria;
        TextView categoriaM;
        String ctgr ="";
        if(IMC < 18.5){
            ctgr = "Bajo peso";
            categoria = findViewById(R.id.lblBajoPeso);
            categoriaM = findViewById(R.id.lblBajoPesoM);
        }else{
            if(IMC < 24.9){
                ctgr = "Normal";
                categoria = findViewById(R.id.lblNormal);
                categoriaM = findViewById(R.id.lblNormalM);
            }else{
                if(IMC < 29.9){
                    ctgr = "Sobrepeso";
                    categoria = findViewById(R.id.lblSobrePeso);
                    categoriaM = findViewById(R.id.lblSobrePesoM);
                }else{
                    ctgr = "Obeso";
                    categoria = findViewById(R.id.lblObeso);
                    categoriaM = findViewById(R.id.lblObesoM);
                }
            }
        }

        String altura= intent.getStringExtra(MainActivity.EXTRA_ALTURA);
        TextView textView = findViewById(R.id.lblAltura);
        textView.setText(textView.getText().toString()+" "+format.format(Double.parseDouble(altura))+" metros");

        String peso= intent.getStringExtra(MainActivity.EXTRA_PESO);
        textView = findViewById(R.id.lblPeso);
        textView.setText(textView.getText().toString()+" "+format.format(Double.parseDouble(peso)) +" kg");

        textView = findViewById(R.id.lblIMC);
        textView.setText( textView.getText().toString() +" "+ format.format(IMC));

        textView = findViewById(R.id.lblCategoria);
        textView.setText(textView.getText().toString() + " "+ ctgr);

        categoria.setBackgroundColor(getResources().getColor(R.color.colorAzulLight));
        categoriaM.setBackgroundColor(getResources().getColor(R.color.colorAzulLight));
    }
}
