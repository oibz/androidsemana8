package com.example.oscar_ibanez_semana8;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ArtistaAdapter extends ArrayAdapter<Artista> {
    private LayoutInflater inflater;

    public ArtistaAdapter(Context context, List<Artista> artistas) {
        super(context, 0, artistas);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.items_artista, parent, false);
        }

        Artista artista = getItem(position);
        TextView nombreArtista = convertView.findViewById(R.id.nombre_artista);
        TextView cancionesArtista = convertView.findViewById(R.id.canciones_artista);

        nombreArtista.setText(artista.getNombre());
        cancionesArtista.setText(TextUtils.join(", ", artista.getCanciones()));

        return convertView;
    }
}