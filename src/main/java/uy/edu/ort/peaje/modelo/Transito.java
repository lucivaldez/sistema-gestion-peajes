package uy.edu.ort.peaje.modelo;

import java.util.Date;

public class Transito {
    private Date fechaHora;
    private Vehiculo vehiculo;
    private Notificacion notificacion;
    private Puesto puesto;
    private Tarifa tarifa;
    private double montoCobrado;
    private String bonificacionAplicada;

    public Transito(Date fechaHora, Vehiculo vehiculo, Notificacion notificacion, Puesto puesto, Tarifa tarifa, double montoCobrado) {
        this.fechaHora = fechaHora;
        this.vehiculo = vehiculo;
        this.notificacion = notificacion;
        this.puesto = puesto;
        this.tarifa = tarifa;
        this.montoCobrado = montoCobrado;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getBonificacionAplicada() {
        return bonificacionAplicada;
    }

    public void setBonificacionAplicada(String bonificacionAplicada) {
        this.bonificacionAplicada = bonificacionAplicada;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    public double getMontoCobrado() {
        return montoCobrado;
    }

    public void setMontoCobrado(double montoCobrado) {
        this.montoCobrado = montoCobrado;
    }
}
