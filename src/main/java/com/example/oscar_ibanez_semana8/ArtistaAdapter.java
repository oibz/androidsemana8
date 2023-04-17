package com.example.oscar_ibanez_semana8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ArtistaAdapter extends BaseAdapter {

    private Context context;
    private List<Artista> artistas;

    public ArtistaAdapter(Context context, List<Artista> artistas) {
        this.context = context;
        this.artistas = artistas;
    }

    @Override
    public int getCount() {
        return artistas.size();
    }

    @Override
    public Object getItem(int position) {
        return artistas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // No es necesario implementar esto si no vas a utilizar IDs para los elementos
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflar el layout personalizado para cada elemento de la lista
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.items_artista, parent, false);

        // Obtener los views del layout
        TextView nombreArtista = convertView.findViewById(R.id.nombre_artista);
        TextView primeraCancion = convertView.findViewById(R.id.primera_cancion);

        // Obtener el objeto Artista correspondiente a la posición actual
        Artista artista = artistas.get(position);

        // Establecer los valores de los views con los datos del objeto Artista
        nombreArtista.setText(artista.getNombre());
        primeraCancion.setText(artista.getCanciones().get(0));

        return convertView;
    }
}