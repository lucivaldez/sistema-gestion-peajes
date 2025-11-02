package uy.edu.ort.peaje.dtos;

import uy.edu.ort.peaje.modelo.Puesto;

public class PuestoDto {
    private String nombre;

    public PuestoDto(Puesto puesto) {
        nombre = puesto.getNombre();
    }
    public String getNombre() {
        return nombre;
    }
}
