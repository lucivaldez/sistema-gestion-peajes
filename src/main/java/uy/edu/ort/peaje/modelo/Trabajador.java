package uy.edu.ort.peaje.modelo;

import java.util.Calendar;

public class Trabajador extends Bonificacion {

    public Trabajador(String nombreBonificacion) {
        super(nombreBonificacion);
    }
    @Override
    public double calcularMonto(Transito transito) {
        double montoBase = transito.getTarifa().getMonto();
        Calendar cal = Calendar.getInstance();
        cal.setTime(transito.getFechaHora());
        int dow = cal.get(Calendar.DAY_OF_WEEK);

        boolean esDiaDeSemana = (dow != Calendar.SATURDAY && dow != Calendar.SUNDAY);

        return esDiaDeSemana ? (montoBase * 0.2) : montoBase;
    }
}
