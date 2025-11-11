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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoriaVehiculo)) return false;
        CategoriaVehiculo other = (CategoriaVehiculo) o;
        return this.nombreCategoria != null &&
               this.nombreCategoria.equalsIgnoreCase(other.nombreCategoria);
    }

    @Override
    public int hashCode() {
        return (nombreCategoria == null) ? 0 : nombreCategoria.toLowerCase().hashCode();
    }
}
