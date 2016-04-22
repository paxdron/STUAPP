package com.padron.stuapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 16/04/2016.
 */
public class Ruta {
    private String nombre;

    public static final List<Ruta> RUTAS_BUAP = new ArrayList<Ruta>();

    public Ruta(String nombre,String imagen) {
        this.nombre = nombre;
        this.imagen=imagen;
    }

    public String imagen;

    public String getImagen() {
        return imagen;
    }

    static {
        RUTAS_BUAP.add(new Ruta("Amalucan","http://i1104.photobucket.com/albums/h330/Fernando_Rodriguez_Mendieta/CUAUTLANCINGO_zpsa4vesqi9.jpg"));
        RUTAS_BUAP.add(new Ruta("CAPU","http://i1104.photobucket.com/albums/h330/Fernando_Rodriguez_Mendieta/capu_zpsomjccyq0.jpg"));
        RUTAS_BUAP.add(new Ruta("Cuautlancingo","http://i1104.photobucket.com/albums/h330/Fernando_Rodriguez_Mendieta/cuautlancingo_zpsxcwubqng.jpg"));
        RUTAS_BUAP.add(new Ruta("Los Héroes","http://i1104.photobucket.com/albums/h330/Fernando_Rodriguez_Mendieta/heroes_zps3pyxmmxg.jpg"));
        RUTAS_BUAP.add(new Ruta("Maravillas","http://i1104.photobucket.com/albums/h330/Fernando_Rodriguez_Mendieta/maravillas_zpsdxwbguxh.jpg"));
        RUTAS_BUAP.add(new Ruta("San Ramón","http://i1104.photobucket.com/albums/h330/Fernando_Rodriguez_Mendieta/sanramon_zpsfy0qzx7v.jpg"));
    }
    public String getNombre() {
        return nombre;
    }
}
