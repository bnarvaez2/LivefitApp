package com.example.livefitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Resultado extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference historial, recomend;

    public static final String EXTRA_TITULO = "com.example.android.LivefitApp.extra.TITULO";
    public static final String EXTRA_DESCRIPCION = "com.example.android.LivefitApp.extra.DESCRIPCION";
    public static final String EXTRA_IMG = "com.example.android.LivefitApp.extra.IMG";

    private TextView textViewDescripcion;
    private TextView textViewNombre;
    private ImageView imageView;
    private ImageButton imageButtonBack;
    private Button button;

    public static final ArrayList<Recomendacion> list = new ArrayList<>();

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

        recomend = FirebaseDatabase.getInstance().getReference();

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

        textViewDescripcion = findViewById(R.id.lblDscrpcnRcmndR);
        imageView = findViewById(R.id.imgRecomendacionR);
        button = findViewById(R.id.btnVerR);

        if(ctgr.equals("Bajo peso")){
            getRecomendacion("subirDePeso");
        }else{
            if(ctgr.equals("Normal")){
                getRecomendacion("saludable");
            }else{
                getRecomendacion("bajarDePeso");
            }
        }

    }

    public void getRecomendacion(String hijo){
        recomend.child("recomends").child(hijo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    list.clear();
                    Recomendacion ob;
                    for(DataSnapshot sd: dataSnapshot.getChildren()){
                        ob = new Recomendacion();
                        ob.setImage(sd.child("img").getValue().toString());
                        ob.setNombre(sd.child("nombre").getValue().toString());
                        ob.setDescripcion(sd.child("descripcion").getValue().toString() .replace("\\n", System.getProperty("line.separator")));
                        list.add(ob);
                    }
                }
                int num= (int) (Math.random()*list.size())-1;
                mostrarRecomendacion(num);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void mostrarRecomendacion(final int i){
        textViewDescripcion.setText(list.get(i).getNombre()+": "+list.get(i).getDescripcion());
        Picasso.with(this).load(list.get(i).getImage()).into(imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Resultado.this,verRecomendacion.class);
                intent.putExtra(EXTRA_TITULO,list.get(i).getNombre());
                intent.putExtra(EXTRA_DESCRIPCION,list.get(i).getDescripcion());
                intent.putExtra(EXTRA_IMG,list.get(i).getImage());
                startActivity(intent);
            }
        });
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
