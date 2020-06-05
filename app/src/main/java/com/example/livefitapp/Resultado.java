package com.example.livefitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.Calendar;

public class Resultado extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference historial;

    private ImageButton imageButtonBack;

    String ctgr ="";
    Double IMC;
    String altura;
    String peso;
    String email;
    DecimalFormat format = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        historial = database.getInstance().getReference();

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_IMC);
        IMC = Double.parseDouble(message);

        if(IMC < 18.5){
            ctgr = "Bajo peso";
        }else{
            if(IMC < 24.9){
                ctgr = "Normal";
            }else{
                if(IMC < 29.9){
                    ctgr = "Sobrepeso";
                }else{
                    ctgr = "Obeso";
                }
            }
        }

        altura = intent.getStringExtra(MainActivity.EXTRA_ALTURA);
        TextView textView = findViewById(R.id.hAltura);
        textView.setText(format.format(Double.parseDouble(altura)));

        peso = intent.getStringExtra(MainActivity.EXTRA_PESO);
        textView = findViewById(R.id.hPeso);
        textView.setText(format.format(Double.parseDouble(peso)));

        textView = findViewById(R.id.resultado);
        textView.setText(format.format(IMC));

        textView = findViewById(R.id.hCategoria);
        textView.setText(ctgr);

        imageButtonBack = findViewById(R.id.btnBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        email = intent.getStringExtra(MainActivity.EXTRA_USER);
        registrarHistorial(email);
    }



    public void registrarHistorial(String x){
        String meses[]={"Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"};
        String id = historial.push().getKey();
        Calendar c = Calendar.getInstance();
        String dia = Integer.toString(c.get(Calendar.DATE));
        String mes = meses[c.get(Calendar.MONTH)];
        String  annio = Integer.toString(c.get(Calendar.YEAR));
        Historial registro = new Historial(annio, dia,mes,altura,peso,format.format(IMC)+"",ctgr);
        historial.child("register").child(email.replace('.','|')).child(id).setValue(registro);
    }

}
