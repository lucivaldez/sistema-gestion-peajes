package uy.edu.ort.peaje.dtos;

import uy.edu.ort.peaje.modelo.Tarifa;

public class TarifaDto {
    private String categoria;
    private double monto;
    private String puesto;

    public TarifaDto(Tarifa t) {
        this.categoria = t.getCategoriaVehiculo().getNombreCategoria();
        this.monto = t.getMonto();
        this.puesto = t.getPuesto().getNombre();
    }

    public String getCategoria() {
        return categoria;
    }
    public double getMonto() {
        return monto;
    }
    public String getPuesto() {
        return puesto;
    }
        
}
