package uy.edu.ort.peaje.modelo;

public class Exonerado extends Bonificacion {

    public Exonerado(String nombreBonificacion) {
        super(nombreBonificacion);
    }

    //No pagan el tránsito en un determinado puesto.

    @Override
    public double calcularMonto(Transito transito) {
        return 0;
    }
    

}

