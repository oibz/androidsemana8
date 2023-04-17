package com.example.oscar_ibanez_semana8;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotificationService extends Service {

    private static final int INTERVALO_NOTIFICACIONES = 20000; // 20 segundos
    private List<Artista> artistas;
    private int posicionActual;

    public class Artista {
        private String nombre;
        private List<String> canciones;

        public Artista(String nombre, List<String> canciones) {
            this.nombre = nombre;
            this.canciones = canciones;
        }

        public String getNombre() {
            return nombre;
        }

        public List<String> getCanciones() {
            return canciones;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Lista de artistas
        artistas = new ArrayList<>();
        artistas.add(new Artista("Bad Bunny", Arrays.asList("Callaíta")));
        artistas.add(new Artista("Metallica", Arrays.asList("Nothing Else Matters")));
        artistas.add(new Artista("Dillom", Arrays.asList("220")));
        artistas.add(new Artista("Los Miserables", Arrays.asList("El Crack")));
        artistas.add(new Artista("Blink 182", Arrays.asList("Dumpweed")));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Crear el canal de notificación
            NotificationChannel channel = new NotificationChannel("default", "Canal de notificaciones", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
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
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}