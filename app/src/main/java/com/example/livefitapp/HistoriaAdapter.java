package com.example.livefitapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoriaAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Historial>listaRegistro;

    public HistoriaAdapter(Context context, ArrayList<Historial> listaRegistro) {
        this.context = context;
        this.listaRegistro = listaRegistro;
    }

    @Override
    public int getCount() {
        return listaRegistro.size();
    }

    @Override
    public Object getItem(int position) {
        return listaRegistro.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Historial registro = (Historial) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.historial, null);
        TextView textViewDia = convertView.findViewById(R.id.hDia);
        TextView textViewMes = convertView.findViewById(R.id.hMes);
        TextView textViewAnio = convertView.findViewById(R.id.hAnio);
        TextView textViewPeso = convertView.findViewById(R.id.hAltura);
        TextView textViewAltura = convertView.findViewById(R.id.hPeso);
        TextView textViewIMC = convertView.findViewById(R.id.hIMC);
        TextView textViewCategoria = convertView.findViewById(R.id.hCategoria);

        textViewDia.setText(registro.getDia());
        textViewMes.setText(registro.getMes());
        textViewAnio.setText(registro.getAño());
        textViewAltura.setText(registro.getAltura());
        textViewPeso.setText(registro.getPeso());
        textViewIMC.setText(registro.getImc());
        textViewCategoria.setText(registro.getCategoria());

        return convertView;
    }
}
