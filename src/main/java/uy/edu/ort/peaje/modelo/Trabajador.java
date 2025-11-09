package uy.edu.ort.peaje.modelo;

public class Trabajador extends Bonificacion {

    public Trabajador(String nombreBonificacion) {
        super(nombreBonificacion);
    }

    // Tienen un 80% de descuento si el tránsito por el puesto asignado se realiza
    // en un día de semana.

    @Override
    public double calcularMonto(Transito transito) {
        double montoBase = transito.getTarifa().getMonto();
        return montoBase * 0.2; // 80% de descuento
    }
}
