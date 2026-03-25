package uy.edu.ort.peaje.modelo;

import java.util.Date;

public class Frecuente extends Bonificacion {

    public Frecuente(String nombreBonificacion) {
        super(nombreBonificacion);
    }

    @Override
    public double calcularMonto(Transito transito){
        Vehiculo vehiculo = transito.getVehiculo();
        Puesto puesto = transito.getPuesto();
        Date fechaHora = transito.getFechaHora();
        int pasadasPrevias = vehiculo.getPasadasPorDia(fechaHora, puesto);

        double montoBase = transito.getTarifa().getMonto();

        if (pasadasPrevias >= 1) {
            return montoBase * 0.5;
        } else {
            return montoBase;
        }
    }
}

