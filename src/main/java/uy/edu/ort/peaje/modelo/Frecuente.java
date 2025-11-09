package uy.edu.ort.peaje.modelo;

import java.util.Date;

public class Frecuente extends Bonificacion {

    public Frecuente(String nombreBonificacion) {
        super(nombreBonificacion);
    }

    // Tienen un 50% de descuento a partir del segundo transito realizado en el día
    // por un puesto determinado con el mismo vehículo. En el primer tránsito del día (con
    // cada vehículo) no tienen descuento.

    @Override
    public double calcularMonto(Transito transito){
        Vehiculo vehiculo = transito.getVehiculo();
        Puesto puesto = transito.getPuesto();
        Date fechaHora = transito.getFechaHora();

        // contar tránsitos previos del mismo día y puesto
        int pasadasPrevias = vehiculo.getPasadasPorDia(fechaHora, puesto);

        double montoBase = transito.getTarifa().getMonto();

        // si ya hay al menos 1 pasada previa -> este es 2º o más -> aplica 50%
        if (pasadasPrevias >= 1) {
            return montoBase * 0.5;
        } else {
            return montoBase;
        }

    }



}

