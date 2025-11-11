package uy.edu.ort.peaje.modelo;

import java.util.Calendar;

public class Trabajador extends Bonificacion {

    public Trabajador(String nombreBonificacion) {
        super(nombreBonificacion);
    }

    // Tienen un 80% de descuento si el tránsito por el puesto asignado se realiza
    // en un día de semana.

    @Override
    public double calcularMonto(Transito transito) {
        double montoBase = transito.getTarifa().getMonto();

        // obtener día de la semana
        Calendar cal = Calendar.getInstance();
        cal.setTime(transito.getFechaHora());
        int dow = cal.get(Calendar.DAY_OF_WEEK);

        boolean esDiaDeSemana = (dow != Calendar.SATURDAY && dow != Calendar.SUNDAY);

        return esDiaDeSemana ? (montoBase * 0.2) : montoBase;
    }
}
