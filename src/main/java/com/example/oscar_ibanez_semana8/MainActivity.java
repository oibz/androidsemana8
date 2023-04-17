package com.example.oscar_ibanez_semana8;

import static android.app.Service.START_STICKY;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int INTERVALO_NOTIFICACIONES = 20000; // 20 segundos
    private List<Artista> artistas;
    private int posicionActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Lista de artistas
        artistas = new ArrayList<>();
        artistas.add(new Artista("Bad Bunny", Arrays.asList("Callaíta")));
        artistas.add(new Artista("Metallica", Arrays.asList("Nothing Else Matters")));
        artistas.add(new Artista("Dillom", Arrays.asList("220")));
        artistas.add(new Artista("Los Miserables", Arrays.asList("El Crack")));
        artistas.add(new Artista("Blink 182", Arrays.asList("Dumpweed")));
        posicionActual = 0;

        ListView listView = findViewById(R.id.lista_artistas);
        ArtistaAdapter adapter = new ArtistaAdapter(this, artistas);
        listView.setAdapter(adapter);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Artista artistaActual = artistas.get(posicionActual);
                String nombreArtista = artistaActual.getNombre();
                String nombreCancion = artistaActual.getCanciones().get(0);

                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle(nombreArtista)
                                .setContentText(nombreCancion);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, builder.build());

                posicionActual = (posicionActual + 1) % artistas.size();

                handler.postDelayed(this, INTERVALO_NOTIFICACIONES);
            }
        }, INTERVALO_NOTIFICACIONES);

        return START_STICKY;
    }

    @Nullable

    public IBinder onBind(Intent intent) {
        return null;
    }

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
}