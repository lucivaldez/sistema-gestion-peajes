package uy.edu.ort.peaje.modelo;

public class Exonerado extends Bonificacion {

    public Exonerado(String nombreBonificacion) {
        super(nombreBonificacion);
    }

    @Override
    public double calcularMonto(Transito transito) {
        return 0;
    }
}

