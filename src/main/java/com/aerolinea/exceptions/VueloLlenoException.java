package com.aerolinea.exceptions;

public class VueloLlenoException extends Exception {
    public VueloLlenoException(String mensaje) {
        super(mensaje);
    }
}