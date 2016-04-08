package com.padron.stuapp;

/**
 * Created by antonio on 14/03/16.
 */
public class unidadEnServicio {
    public int idConductor;
    public int idRuta;
    public double latitud;
    public double longitud;

    public unidadEnServicio(double longitud, int idConductor, int idRuta, double latitud) {
        this.longitud = longitud;
        this.idConductor = idConductor;
        this.idRuta = idRuta;
        this.latitud = latitud;
    }

    public unidadEnServicio() {
    }
}
