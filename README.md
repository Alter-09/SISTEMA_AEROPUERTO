<div align="center">

# ✈️ Sistema Aeropuerto
### Gestión de vuelos · Pasajeros · Aeropuertos

**Aplicación de consola para administrar operaciones básicas de un sistema aeroportuario.**

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Jackson](https://img.shields.io/badge/Jackson-JSON-59666C?style=for-the-badge)
![JUnit](https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white)

</div>

## Descripción

Sistema Aeropuerto es una aplicación Java de consola cuyo propósito declarado es la gestión de vuelos, pasajeros y aeropuertos. El proyecto utiliza Maven para la construcción, Jackson para serialización JSON y JUnit Jupiter para pruebas.

## Tecnologías

| Tecnología | Uso |
|---|---|
| Java 17 | Lenguaje y plataforma |
| Maven | Gestión de construcción y dependencias |
| Jackson Databind | Serialización y procesamiento JSON |
| Jackson JSR-310 | Soporte de tipos de fecha y hora |
| JUnit Jupiter 5 | Pruebas automatizadas |

## Ejecutar el proyecto

Requisitos: JDK 17 y Maven.

```bash
git clone https://github.com/Alter-09/SISTEMA_AEROPUERTO.git
cd SISTEMA_AEROPUERTO
mvn test
mvn compile
mvn exec:java
```

El plugin de ejecución está configurado para iniciar `com.aerolinea.Main`.

## Estructura

El código fuente se encuentra en `src/`. La configuración de compilación, dependencias y clase principal está declarada en `pom.xml`.

---

<div align="center">

*Project documentation · Java 17*

</div>
