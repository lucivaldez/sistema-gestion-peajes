package uy.edu.ort.peaje.modelo;

public abstract class Bonificacion {
    
    private String nombreBonificacion;
    

    public Bonificacion(String nombreBonificacion) {
        this.nombreBonificacion = nombreBonificacion;
    }

    public String getNombreBonificacion() {
        return nombreBonificacion;
    }

    public abstract double calcularMonto(Transito transito);

}
