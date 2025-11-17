package uy.edu.ort.peaje.modelo;

public class Tarifa {

    private Puesto puesto;
    private double monto;
    private CategoriaVehiculo categoriaVehiculo;

    public Tarifa(Puesto puesto, double monto, CategoriaVehiculo categoriaVehiculo) {
        this.puesto = puesto;
        this.monto = monto;
        this.categoriaVehiculo = categoriaVehiculo;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
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
