package uy.edu.ort.peaje.dtos;

import uy.edu.ort.peaje.modelo.Vehiculo;

public class VehiculoTableroDto {
    private String matricula;
    private String modelo;
    private String color;
    private int cantidadTransitos;
    private double montoTotal;

    public VehiculoTableroDto(Vehiculo v, int cantidadTransitos, double montoTotal) {
        this.matricula = v.getMatricula();
        this.modelo = v.getModelo();
        this.color = v.getColor();
        this.cantidadTransitos = cantidadTransitos;
        this.montoTotal = montoTotal;
    }

    public String getMatricula() { return matricula; }
    public String getModelo() { return modelo; }
    public String getColor() { return color; }
    public int getCantidadTransitos() { return cantidadTransitos; }
    public double getMontoTotal() { return montoTotal; }
}