package uy.edu.ort.peaje.modelo;

public class Tarifa {
    private double monto;
    private CategoriaVehiculo categoriaVehiculo;
    
    public Tarifa(double monto, CategoriaVehiculo categoriaVehiculo) {
        this.monto = monto;
        this.categoriaVehiculo = categoriaVehiculo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public CategoriaVehiculo getCategoriaVehiculo() {
        return categoriaVehiculo;
    }

    public void setCategoriaVehiculo(CategoriaVehiculo categoriaVehiculo) {
        this.categoriaVehiculo = categoriaVehiculo;
    }

}
