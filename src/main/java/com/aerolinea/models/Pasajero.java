package com.aerolinea.models;

public class Pasajero extends Persona {
    private int edad;

    public Pasajero(String documento, String nombre, int edad) {
        super(documento, nombre);
        this.edad = edad;
    }

    public int getEdad() {
        return edad;
    }

    @Override
    public String obtenerDescripcion() {
        return "Pasajero: " + getNombre();
    }
}