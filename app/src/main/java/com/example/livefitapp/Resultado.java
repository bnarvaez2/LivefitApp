package com.example.livefitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.Calendar;

public class Resultado extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference historial;
    TextView categoria;
    TextView categoriaM;
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

        altura = intent.getStringExtra(MainActivity.EXTRA_ALTURA);
        TextView textView = findViewById(R.id.hAltura);
        textView.setText(textView.getText().toString()+" "+format.format(Double.parseDouble(altura))+" metros");

        peso = intent.getStringExtra(MainActivity.EXTRA_PESO);
        textView = findViewById(R.id.hPeso);
        textView.setText(textView.getText().toString()+" "+format.format(Double.parseDouble(peso)) +" kg");

        textView = findViewById(R.id.hIMC);
        textView.setText( textView.getText().toString() +" "+ format.format(IMC));

        textView = findViewById(R.id.hCategoria);
        textView.setText(textView.getText().toString() + " "+ ctgr);

        email = intent.getStringExtra(MainActivity.EXTRA_USER);

        categoria.setBackgroundColor(getResources().getColor(R.color.colorAzulLight));
        categoriaM.setBackgroundColor(getResources().getColor(R.color.colorAzulLight));
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
