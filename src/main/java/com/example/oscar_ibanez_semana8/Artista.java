package com.example.oscar_ibanez_semana8;

import java.util.List;

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