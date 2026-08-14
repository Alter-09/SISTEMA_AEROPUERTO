package com.aerolinea.repositories;

import java.util.ArrayList;
import java.util.List;

public class Repositorio<T> {
    private List<T> elementos = new ArrayList<>();

    public void agregar(T elemento) {
        elementos.add(elemento);
    }

    public List<T> obtenerTodos() {
        return elementos;
    }

    public boolean existePorCodigo(String codigo, java.util.function.Function<T, String> getCodigo) {
        for (T elemento : elementos) {
            if (getCodigo.apply(elemento).equals(codigo)) {
                return true;
            }
        }
        return false;
    }

    public T buscarPorCodigo(String codigo, java.util.function.Function<T, String> getCodigo) {
        for (T elemento : elementos) {
            if (getCodigo.apply(elemento).equals(codigo)) {
                return elemento;
            }
        }
        return null;
    }
}