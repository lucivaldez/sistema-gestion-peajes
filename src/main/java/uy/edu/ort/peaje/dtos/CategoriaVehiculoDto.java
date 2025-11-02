package uy.edu.ort.peaje.dtos;

import uy.edu.ort.peaje.modelo.CategoriaVehiculo;

public class CategoriaVehiculoDto {
    private String nombreCategoria;

    public CategoriaVehiculoDto(CategoriaVehiculo c) {
        this.nombreCategoria = c.getNombreCategoria();
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }
}
