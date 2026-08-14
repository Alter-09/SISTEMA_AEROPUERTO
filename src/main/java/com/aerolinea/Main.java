package com.aerolinea;

import com.aerolinea.exceptions.VueloLlenoException;
import com.aerolinea.models.EstadoVuelo;
import com.aerolinea.services.SistemaVuelos;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaVuelos sistema = new SistemaVuelos();
        sistema.cargarDatosIniciales();

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n==================================");
            System.out.println("SISTEMA AEROPUERTO JAVA");
            System.out.println("==================================");
            System.out.println("1. Registrar aeropuerto");
            System.out.println("2. Registrar pasajero");
            System.out.println("3. Registrar vuelo");
            System.out.println("4. Listar vuelos");
            System.out.println("5. Buscar vuelos por destino");
            System.out.println("6. Reservar vuelo");
            System.out.println("7. Ver pasajeros de un vuelo");
            System.out.println("8. Cambiar estado de vuelo");
            System.out.println("9. Ver estadísticas");
            System.out.println("10. Consultar rutas disponibles");
            System.out.println("11. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> {
                    System.out.print("Código: ");
                    String codigo = scanner.nextLine().trim().toUpperCase();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine().trim();
                    System.out.print("Ciudad: ");
                    String ciudad = scanner.nextLine().trim();
                    System.out.print("País: ");
                    String pais = scanner.nextLine().trim();
                    sistema.registrarAeropuerto(codigo, nombre, ciudad, pais);
                }
                case 2 -> {
                    System.out.print("Documento: ");
                    String documento = scanner.nextLine().trim();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine().trim();
                    System.out.print("Edad: ");
                    int edad;
                    try {
                        edad = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Edad inválida.");
                        break;
                    }
                    sistema.registrarPasajero(documento, nombre, edad);
                }
                case 3 -> {
                    System.out.print("Código del vuelo: ");
                    String codigo = scanner.nextLine().trim().toUpperCase();
                    System.out.print("Código aeropuerto origen: ");
                    String origen = scanner.nextLine().trim().toUpperCase();
                    System.out.print("Código aeropuerto destino: ");
                    String destino = scanner.nextLine().trim().toUpperCase();
                    System.out.print("Capacidad máxima: ");
                    int capacidad;
                    try {
                        capacidad = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Capacidad inválida.");
                        break;
                    }
                    sistema.registrarVuelo(codigo, origen, destino, capacidad);
                }
                case 4 -> sistema.listarVuelos();
                case 5 -> {
                    System.out.print("Ingrese destino: ");
                    String ciudad = scanner.nextLine();
                    sistema.buscarVuelosPorDestino(ciudad);
                }
                case 6 -> {
                    System.out.print("Documento del pasajero: ");
                    String documento = scanner.nextLine().trim();
                    System.out.print("Código del vuelo: ");
                    String codigoVuelo = scanner.nextLine().trim().toUpperCase();
                    try {
                        sistema.reservarVuelo(documento, codigoVuelo);
                    } catch (VueloLlenoException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 7 -> {
                    System.out.print("Código del vuelo: ");
                    String codigo = scanner.nextLine().trim().toUpperCase();
                    sistema.verPasajerosVuelo(codigo);
                }
                case 8 -> {
                    System.out.print("Código del vuelo: ");
                    String codigo = scanner.nextLine().trim().toUpperCase();
                    System.out.println("Nuevo estado:");
                    System.out.println("PROGRAMADO");
                    System.out.println("ABORDANDO");
                    System.out.println("EN_VUELO");
                    System.out.println("FINALIZADO");
                    System.out.println("CANCELADO");
                    System.out.print("Seleccione: ");
                    String estado = scanner.nextLine().trim();
                    sistema.cambiarEstadoVuelo(codigo, estado);
                }
                case 9 -> sistema.verEstadisticas();
                case 10 -> {
                    System.out.print("Ciudad origen: ");
                    String origen = scanner.nextLine();
                    System.out.print("Ciudad destino: ");
                    String destino = scanner.nextLine();
                    sistema.consultarRutas(origen, destino);
                }
                case 11 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 11);

        scanner.close();
    }
}