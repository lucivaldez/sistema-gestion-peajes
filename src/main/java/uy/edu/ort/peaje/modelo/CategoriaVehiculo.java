package uy.edu.ort.peaje.modelo;

public class CategoriaVehiculo {
    private String nombreCategoria;

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public CategoriaVehiculo(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}
