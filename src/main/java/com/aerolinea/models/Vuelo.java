package com.aerolinea.models;

import java.util.ArrayList;
import java.util.List;

public class Vuelo {
    private String codigo;
    private Aeropuerto origen;
    private Aeropuerto destino;
    private int capacidad;
    private EstadoVuelo estado;
    private List<Pasajero> pasajeros;

    public Vuelo(String codigo, Aeropuerto origen, Aeropuerto destino, int capacidad) {
        this.codigo = codigo;
        this.origen = origen;
        this.destino = destino;
        this.capacidad = capacidad;
        this.estado = EstadoVuelo.PROGRAMADO;
        this.pasajeros = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public Aeropuerto getOrigen() {
        return origen;
    }

    public Aeropuerto getDestino() {
        return destino;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public EstadoVuelo getEstado() {
        return estado;
    }

    public void setEstado(EstadoVuelo estado) {
        this.estado = estado;
    }

    public List<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public int getPasajerosCount() {
        return pasajeros.size();
    }

    public boolean agregarPasajero(Pasajero pasajero) {
        if (pasajeros.size() >= capacidad) {
            return false;
        }
        for (Pasajero p : pasajeros) {
            if (p.getDocumento().equals(pasajero.getDocumento())) {
                return false;
            }
        }
        pasajeros.add(pasajero);
        return true;
    }

    public double getPorcentajeOcupacion() {
        if (capacidad == 0) return 0;
        return (pasajeros.size() * 100.0) / capacidad;
    }
}