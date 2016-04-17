package com.padron.stuapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 16/04/2016.
 */
public class Ruta {
    private String nombre;

    public static final List<Ruta> RUTAS_BUAP = new ArrayList<Ruta>();

    public Ruta(String nombre) {
        this.nombre = nombre;
    }

    static {
        RUTAS_BUAP.add(new Ruta("Amalucan"));
        RUTAS_BUAP.add(new Ruta("CAPU"));
        RUTAS_BUAP.add(new Ruta("Cuautlancingo"));
        RUTAS_BUAP.add(new Ruta("Los Héroes"));
        RUTAS_BUAP.add(new Ruta("Maravillas"));
        RUTAS_BUAP.add(new Ruta("San Ramón"));
    }
    public String getNombre() {
        return nombre;
    }
}
