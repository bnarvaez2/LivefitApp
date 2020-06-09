package com.example.livefitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class verRecomendacion extends AppCompatActivity {

    private TextView textViewTitulo;
    private TextView textViewDescripcion;
    private ImageView imageView;
    private ImageButton imageButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_recomendacion);

        Intent intent = getIntent();

        textViewTitulo = findViewById(R.id.lblTitulo);
        textViewDescripcion = findViewById(R.id.lblDescripcionRcmnd);
        imageView = findViewById(R.id.imgRcmnd);

        imageButtonBack = findViewById(R.id.btnBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (intent.getStringExtra(RecomendacionAdapter.EXTRA_TITULO).isEmpty()) {
            textViewTitulo.setText(intent.getStringExtra(Resultado.EXTRA_TITULO));
            textViewDescripcion.setText(intent.getStringExtra(Resultado.EXTRA_DESCRIPCION).replace("\\n", System.getProperty("line.separator")));
            Picasso.with(this).load(intent.getStringExtra(Resultado.EXTRA_IMG)).into(imageView);

        }else{
            textViewTitulo.setText(intent.getStringExtra(RecomendacionAdapter.EXTRA_TITULO));
            textViewDescripcion.setText(intent.getStringExtra(RecomendacionAdapter.EXTRA_DESCRIPCION).replace("\\n", System.getProperty("line.separator")));
            Picasso.with(this).load(intent.getStringExtra(RecomendacionAdapter.EXTRA_IMG)).into(imageView);
        }
    }
}
