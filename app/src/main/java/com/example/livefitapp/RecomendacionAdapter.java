package com.example.livefitapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.widget.Toast.*;

public class RecomendacionAdapter extends BaseAdapter {

    public static final String EXTRA_TITULO = "com.example.android.LivefitApp.extra.TITULO";
    public static final String EXTRA_DESCRIPCION = "com.example.android.LivefitApp.extra.DESCRIPCION";
    public static final String EXTRA_IMG = "com.example.android.LivefitApp.extra.IMG";
    public String img;

    private Context context;
    ArrayList<Recomendacion> listaRecomendaciones;

    public RecomendacionAdapter(Context context, ArrayList<Recomendacion> listaRecomendaciones) {
        this.context = context;
        this.listaRecomendaciones = listaRecomendaciones;
    }

    @Override
    public int getCount() {
        return listaRecomendaciones.size();
    }

    @Override
    public Object getItem(int position) {
        return listaRecomendaciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Recomendacion ob = (Recomendacion) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.recomendacion, null);
        final TextView textViewNombre = convertView.findViewById(R.id.lblNom_Rcmdc);
        final TextView textViewDescripcion = convertView.findViewById(R.id.lblDscrp_Rcmnd);
        ImageView image = convertView.findViewById(R.id.image);
        Button button = convertView.findViewById(R.id.btnVerRecomendacion);

        textViewNombre.setText(ob.getNombre());
        textViewDescripcion.setText(ob.getDescripcion());
        Picasso.with(context).load(ob.getImage()).into(image);
        //img = ob.getImage();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,verRecomendacion.class);
                intent.putExtra(EXTRA_TITULO,textViewNombre.getText().toString());
                intent.putExtra(EXTRA_DESCRIPCION,textViewDescripcion.getText().toString());
                intent.putExtra(EXTRA_IMG,ob.getImage());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
