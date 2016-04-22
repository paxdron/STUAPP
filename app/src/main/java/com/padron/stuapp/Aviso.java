package com.padron.stuapp;

/**
 * Reflejo de la tabla 'meta' en la base de datos
 */
public class Aviso {

    /*
    Atributos
     */
    private int idAvisos;
    private int numAviso;
    private String hora;
    private String fecha;
    private String contenido;
    private int tipo;

    public Aviso(int idAvisos, int numAviso, String hora, String fecha, String contenido, int tipo) {
        this.idAvisos = idAvisos;
        this.numAviso = numAviso;
        this.hora = hora;
        this.fecha = fecha;
        this.contenido = contenido;
        this.tipo = tipo;
    }

    public int getIdAvisos() {
        return idAvisos;
    }

    public int getNumAviso() {
        return numAviso;
    }

    public String getHora() {
        return hora;
    }

    public String getFecha() {
        return fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public int getTipo() {
        return tipo;
    }

}