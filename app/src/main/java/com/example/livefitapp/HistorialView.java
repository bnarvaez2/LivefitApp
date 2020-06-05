package com.example.livefitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistorialView extends AppCompatActivity {

    private DatabaseReference database;
    private ArrayList<Historial> listaRegistro = new ArrayList<>();

    private ListView lsItems;
    private HistoriaAdapter adaptador;
    public Context context = this;
    private TextView usuarioActual;
    private ImageButton imageButtonBack;
    String user = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_view);
        Intent intent = getIntent();
        user= intent.getStringExtra(MainActivity.EXTRA_USER);

        usuarioActual = findViewById(R.id.lblUsuarioActual);
        usuarioActual.setText(user);

        imageButtonBack = findViewById(R.id.btnBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        database = FirebaseDatabase.getInstance().getReference();
        getRegistros();
    }

    private void getRegistros(){
        database.child("register").child(user.replace('.','|')).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    listaRegistro.clear();
                    Historial registro;
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        registro = new Historial();
                        if(ds.child("dia").exists()){
                            registro.setDia(ds.child("dia").getValue().toString());
                            registro.setMes(ds.child("mes").getValue().toString());
                            registro.setAño(ds.child("año").getValue().toString());
                            registro.setAltura("Altura: "+ ds.child("altura").getValue().toString() + " metros");
                            registro.setPeso("Peso: "+ds.child("peso").getValue().toString() + " kg");
                            registro.setImc("IMC: "+ ds.child("imc").getValue().toString());
                            registro.setCategoria(ds.child("categoria").getValue().toString());
                            listaRegistro.add(registro);
                        }
                    }
                }


                lsItems = findViewById(R.id.listView);
                adaptador = new HistoriaAdapter(context,listaRegistro);
                lsItems.setAdapter(adaptador);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
