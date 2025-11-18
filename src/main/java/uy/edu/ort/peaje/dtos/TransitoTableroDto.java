package uy.edu.ort.peaje.dtos;

import java.util.Date;

import uy.edu.ort.peaje.modelo.Transito;
import uy.edu.ort.peaje.modelo.Tarifa;
import uy.edu.ort.peaje.modelo.Vehiculo;

public class TransitoTableroDto {

    private String puesto;
    private String matricula;
    private String categoria;
    private double montoTarifa;
    private String bonificacion;
    private double montoBonificacion;
    private double montoPagado;
    private long fechaHora; // epoch millis

    public TransitoTableroDto(Transito t) {
        Vehiculo v = t.getVehiculo();
        Tarifa tarifa = t.getTarifa();

        this.puesto = t.getPuesto() != null ? t.getPuesto().getNombre() : "";
        this.matricula = (v != null) ? v.getMatricula() : "";
        this.categoria = (v != null && v.getCategoriaVehiculo() != null)
                ? v.getCategoriaVehiculo().getNombreCategoria()
                : "";

        this.montoTarifa = (tarifa != null) ? tarifa.getMonto() : 0.0;
        this.montoPagado = t.getMontoCobrado();

        // monto bonificación = tarifa - monto pagado (si hay tarifa)
        this.montoBonificacion = montoTarifa - montoPagado;

        String bonif = t.getBonificacionAplicada();
        this.bonificacion = (bonif != null && !bonif.isBlank()) ? bonif : "Sin bonificación";

        Date fh = t.getFechaHora();
        this.fechaHora = (fh != null) ? fh.getTime() : 0L;
    }

    public String getPuesto() { return puesto; }
    public String getMatricula() { return matricula; }
    public String getCategoria() { return categoria; }
    public double getMontoTarifa() { return montoTarifa; }
    public String getBonificacion() { return bonificacion; }
    public double getMontoBonificacion() { return montoBonificacion; }
    public double getMontoPagado() { return montoPagado; }
    public long getFechaHora() { return fechaHora; }
}