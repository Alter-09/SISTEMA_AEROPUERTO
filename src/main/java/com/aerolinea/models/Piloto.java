package com.aerolinea.models;

public class Piloto extends Persona {
    private String licencia;
    private int horasVuelo;

    public Piloto(String documento, String nombre, String licencia, int horasVuelo) {
        super(documento, nombre);
        this.licencia = licencia;
        this.horasVuelo = horasVuelo;
    }

    public String getLicencia() {
        return licencia;
    }

    public int getHorasVuelo() {
        return horasVuelo;
    }

    @Override
    public String obtenerDescripcion() {
        return "Piloto: " + getNombre() + "- " + horasVuelo + " horas de vuelo";
    }
}