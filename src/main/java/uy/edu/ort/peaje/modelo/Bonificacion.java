package uy.edu.ort.peaje.modelo;

public abstract class Bonificacion {
    
    private String nombre;
    
    public Bonificacion(String nombre) {
        this.nombre = nombre;
    }

    public String getnombre() {
        return nombre;
    }

    public abstract double calcularMonto(Transito transito);
}
