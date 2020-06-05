package com.example.livefitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.EventListener;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class recomendacion_view extends AppCompatActivity {

    private DatabaseReference database;
    private StorageReference mStorageRef;
    private ArrayList<Recomendacion> lstRcmnd = new ArrayList<>();
    private ListView lsItems;

    private RecomendacionAdapter adaptador;
    public Context context = this;
    private TextView usuarioActual;
    private ImageButton imageButtonBack;

    String user = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendacion_view);
        Intent intent = getIntent();
        user= intent.getStringExtra(MainActivity.EXTRA_USER);

        //usuarioActual = findViewById(R.id.lblUsuarioActual);
        //usuarioActual.setText(user);
        database = FirebaseDatabase.getInstance().getReference();

        imageButtonBack = findViewById(R.id.btnBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getRecomendaciones();
    }

    public void getRecomendaciones(){
        database.child("recomends").child("bajarDePeso").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    lstRcmnd.clear();
                    Recomendacion ob;
                    for(DataSnapshot sd: dataSnapshot.getChildren()){
                        ob = new Recomendacion();
                        ob.setImage(sd.child("img").getValue().toString());
                        ob.setNombre(sd.child("nombre").getValue().toString());
                        ob.setDescripcion(sd.child("descripcion").getValue().toString());
                        lstRcmnd.add(ob);
                    }
                }

                lsItems = findViewById(R.id.listViewRcmnd);
                adaptador = new RecomendacionAdapter(context,lstRcmnd);
                lsItems.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
