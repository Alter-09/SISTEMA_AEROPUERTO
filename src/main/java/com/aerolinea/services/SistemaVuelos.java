package com.aerolinea.services;

import com.aerolinea.exceptions.VueloLlenoException;
import com.aerolinea.models.*;
import com.aerolinea.repositories.Repositorio;

import java.util.*;

public class SistemaVuelos {
    private Repositorio<Aeropuerto> aeropuertos = new Repositorio<>();
    private Repositorio<Pasajero> pasajeros = new Repositorio<>();
    private Repositorio<Vuelo> vuelos = new Repositorio<>();
    private List<Persona> personas = new ArrayList<>();
    private Set<String> ciudadesDestino = new HashSet<>();
    private Map<String, Integer> vuelosPorDestino = new HashMap<>();

    public void cargarDatosIniciales() {
        registrarAeropuerto("BOG", "El Dorado", "Bogotá", "Colombia");
        registrarAeropuerto("MDE", "José María Córdova", "Medellín", "Colombia");
        registrarAeropuerto("CLO", "Alfonso Bonilla Aragón", "Cali", "Colombia");
        registrarAeropuerto("CTG", "Rafael Núñez", "Cartagena", "Colombia");
        registrarAeropuerto("PSO", "Antonio Nariño", "Pasto", "Colombia");

        registrarVuelo("AV101", "BOG", "MDE", 180);
        registrarVuelo("AV202", "BOG", "CTG", 150);
        registrarVuelo("AV303", "MDE", "CLO", 120);
        registrarVuelo("AV404", "CLO", "BOG", 160);
        registrarVuelo("AV505", "BOG", "PSO", 100);

        registrarPasajero("1001", "Laura Gómez", 25);
        registrarPasajero("1002", "Andrés Martínez", 32);
        registrarPasajero("1003", "Carolina Díaz", 28);
        registrarPasajero("1004", "Daniel Torres", 41);

        personas.add(new Piloto("P001", "Carlos Rodríguez", "LIC-001", 2500));
        personas.add(new Piloto("P002", "Ana López", "LIC-002", 1800));
    }

    public void registrarAeropuerto(String codigo, String nombre, String ciudad, String pais) {
        if (aeropuertos.existePorCodigo(codigo, Aeropuerto::getCodigo)) {
            System.out.println("Error: Ya existe un aeropuerto con el código " + codigo);
            return;
        }
        Aeropuerto aeropuerto = new Aeropuerto(codigo, nombre, ciudad, pais);
        aeropuertos.agregar(aeropuerto);
        System.out.println("Aeropuerto registrado correctamente.");
    }

    public void registrarPasajero(String documento, String nombre, int edad) {
        if (pasajeros.existePorCodigo(documento, Pasajero::getDocumento)) {
            System.out.println("Error: Ya existe un pasajero con el documento " + documento);
            return;
        }
        if (edad <= 0) {
            System.out.println("Error: La edad debe ser mayor a 0.");
            return;
        }
        Pasajero pasajero = new Pasajero(documento, nombre, edad);
        pasajeros.agregar(pasajero);
        personas.add(pasajero);
        System.out.println("Pasajero registrado correctamente.");
    }

    public void registrarVuelo(String codigo, String origenCodigo, String destinoCodigo, int capacidad) {
        if (vuelos.existePorCodigo(codigo, Vuelo::getCodigo)) {
            System.out.println("Error: Ya existe un vuelo con el código " + codigo);
            return;
        }
        Aeropuerto origen = aeropuertos.buscarPorCodigo(origenCodigo, Aeropuerto::getCodigo);
        Aeropuerto destino = aeropuertos.buscarPorCodigo(destinoCodigo, Aeropuerto::getCodigo);

        if (origen == null) {
            System.out.println("Error: El aeropuerto de origen " + origenCodigo + " no existe.");
            return;
        }
        if (destino == null) {
            System.out.println("Error: El aeropuerto de destino " + destinoCodigo + " no existe.");
            return;
        }
        if (origen.getCodigo().equals(destino.getCodigo())) {
            System.out.println("Error: El origen y destino deben ser diferentes.");
            return;
        }

        Vuelo vuelo = new Vuelo(codigo, origen, destino, capacidad);
        vuelos.agregar(vuelo);

        ciudadesDestino.add(destino.getCiudad());
        vuelosPorDestino.put(destino.getCiudad(), vuelosPorDestino.getOrDefault(destino.getCiudad(), 0) + 1);

        System.out.println("Vuelo registrado correctamente.");
    }

    public void listarVuelos() {
        List<Vuelo> lista = vuelos.obtenerTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay vuelos registrados.");
            return;
        }
        for (Vuelo v : lista) {
            System.out.println("--------------------------------");
            System.out.println("Vuelo: " + v.getCodigo());
            System.out.println("Origen: " + v.getOrigen().getCiudad());
            System.out.println("Destino: " + v.getDestino().getCiudad());
            System.out.println("Capacidad: " + v.getCapacidad());
            System.out.println("Pasajeros: " + v.getPasajerosCount());
            System.out.println("Estado: " + v.getEstado());
            System.out.println("--------------------------------");
        }
    }

    public void buscarVuelosPorDestino(String ciudad) {
        String busqueda = ciudad.trim().toLowerCase();
        List<Vuelo> lista = vuelos.obtenerTodos();
        boolean encontrado = false;

        System.out.println("Resultados encontrados:");
        for (Vuelo v : lista) {
            if (v.getDestino().getCiudad().toLowerCase().contains(busqueda)) {
                System.out.println(v.getCodigo());
                System.out.println(v.getOrigen().getCiudad() + " -> " + v.getDestino().getCiudad());
                System.out.println("Estado: " + v.getEstado());
                System.out.println();
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron vuelos para ese destino.");
        }
    }

    public void reservarVuelo(String documento, String codigoVuelo) throws VueloLlenoException {
        Pasajero pasajero = pasajeros.buscarPorCodigo(documento, Pasajero::getDocumento);
        if (pasajero == null) {
            System.out.println("Error: El pasajero no existe.");
            return;
        }

        Vuelo vuelo = vuelos.buscarPorCodigo(codigoVuelo, Vuelo::getCodigo);
        if (vuelo == null) {
            System.out.println("Error: El vuelo no existe.");
            return;
        }

        if (vuelo.getEstado() != EstadoVuelo.PROGRAMADO) {
            System.out.println("Error: El vuelo debe estar en estado PROGRAMADO para reservar.");
            return;
        }

        if (vuelo.getPasajerosCount() >= vuelo.getCapacidad()) {
            throw new VueloLlenoException("No existen asientos disponibles.");
        }

        for (Pasajero p : vuelo.getPasajeros()) {
            if (p.getDocumento().equals(documento)) {
                System.out.println("Error: El pasajero ya está registrado en este vuelo.");
                return;
            }
        }

        vuelo.getPasajeros().add(pasajero);
        System.out.println("Reserva realizada correctamente.");
    }

    public void verPasajerosVuelo(String codigoVuelo) {
        Vuelo vuelo = vuelos.buscarPorCodigo(codigoVuelo, Vuelo::getCodigo);
        if (vuelo == null) {
            System.out.println("Error: El vuelo no existe.");
            return;
        }
        List<Pasajero> lista = vuelo.getPasajeros();
        if (lista.isEmpty()) {
            System.out.println("No hay pasajeros en este vuelo.");
            return;
        }
        System.out.println("Pasajeros del vuelo " + codigoVuelo + ":");
        for (Pasajero p : lista) {
            System.out.println("- " + p.getDocumento() + ": " + p.getNombre() + " (" + p.getEdad() + " años)");
        }
    }

    public void cambiarEstadoVuelo(String codigo, String nuevoEstadoStr) {
        Vuelo vuelo = vuelos.buscarPorCodigo(codigo, Vuelo::getCodigo);
        if (vuelo == null) {
            System.out.println("Error: El vuelo no existe.");
            return;
        }
        try {
            EstadoVuelo nuevoEstado = EstadoVuelo.valueOf(nuevoEstadoStr.toUpperCase());
            vuelo.setEstado(nuevoEstado);
            System.out.println("Estado actualizado correctamente a: " + nuevoEstado);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Estado inválido. Opciones: PROGRAMADO, ABORDANDO, EN_VUELO, FINALIZADO, CANCELADO");
        }
    }

    public void verEstadisticas() {
        List<Vuelo> listaVuelos = vuelos.obtenerTodos();
        int programados = 0, enVuelo = 0, finalizados = 0, cancelados = 0;
        int totalReservas = 0;
        Vuelo maxOcupacion = null;
        double maxPorcentaje = 0;

        for (Vuelo v : listaVuelos) {
            switch (v.getEstado()) {
                case PROGRAMADO -> programados++;
                case EN_VUELO -> enVuelo++;
                case FINALIZADO -> finalizados++;
                case CANCELADO -> cancelados++;
            }
            totalReservas += v.getPasajerosCount();
            double pct = v.getPorcentajeOcupacion();
            if (pct > maxPorcentaje) {
                maxPorcentaje = pct;
                maxOcupacion = v;
            }
        }

        System.out.println("========= ESTADÍSTICAS =========");
        System.out.println("Aeropuertos registrados: " + aeropuertos.obtenerTodos().size());
        System.out.println("Vuelos registrados: " + listaVuelos.size());
        System.out.println("Pasajeros registrados: " + pasajeros.obtenerTodos().size());
        System.out.println("Vuelos programados: " + programados);
        System.out.println("Vuelos en vuelo: " + enVuelo);
        System.out.println("Vuelos finalizados: " + finalizados);
        System.out.println("Vuelos cancelados: " + cancelados);
        System.out.println("Total reservas realizadas: " + totalReservas);

        if (maxOcupacion != null) {
            System.out.printf("Vuelo con mayor ocupación: %s - %s -> %s %d/%d pasajeros (%.1f%%)%n",
                    maxOcupacion.getCodigo(),
                    maxOcupacion.getOrigen().getCiudad(),
                    maxOcupacion.getDestino().getCiudad(),
                    maxOcupacion.getPasajerosCount(),
                    maxOcupacion.getCapacidad(),
                    maxPorcentaje);
        }

        System.out.println("Destinos disponibles:");
        for (String ciudad : ciudadesDestino) {
            System.out.println(ciudad);
        }

        System.out.println("\n=== VUELOS POR DESTINO ===");
        for (Map.Entry<String, Integer> entry : vuelosPorDestino.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void consultarRutas(String origenCiudad, String destinoCiudad) {
        String origen = origenCiudad.trim().toLowerCase();
        String destino = destinoCiudad.trim().toLowerCase();
        List<Vuelo> lista = vuelos.obtenerTodos();
        boolean encontrado = false;

        System.out.println("Vuelos " + origenCiudad + " -> " + destinoCiudad);
        for (Vuelo v : lista) {
            if (v.getOrigen().getCiudad().toLowerCase().equals(origen) &&
                v.getDestino().getCiudad().toLowerCase().equals(destino)) {
                int disponibles = v.getCapacidad() - v.getPasajerosCount();
                System.out.println(v.getCodigo());
                System.out.println("Estado: " + v.getEstado());
                System.out.println("Disponibles: " + disponibles + " asientos");
                System.out.printf("Ocupación: %d/%d Porcentaje: %.1f%%%n",
                        v.getPasajerosCount(), v.getCapacidad(), v.getPorcentajeOcupacion());
                System.out.println();
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No hay vuelos para esa ruta.");
        }
    }

    public void mostrarPersonas() {
        for (Persona p : personas) {
            System.out.println(p.obtenerDescripcion());
        }
    }

    public Aeropuerto buscarAeropuerto(String codigo) {
        return aeropuertos.buscarPorCodigo(codigo, Aeropuerto::getCodigo);
    }

    public Pasajero buscarPasajero(String documento) {
        return pasajeros.buscarPorCodigo(documento, Pasajero::getDocumento);
    }

    public Vuelo buscarVuelo(String codigo) {
        return vuelos.buscarPorCodigo(codigo, Vuelo::getCodigo);
    }
}