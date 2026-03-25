package uy.edu.ort.peaje.modelo;


public class EstadoPropietarioDeshabilitado extends EstadoPropietario {

    public EstadoPropietarioDeshabilitado() {
        super("Deshabilitado");
    }

    @Override
    public boolean puedeIngresarAlSistema() {
        return false;
    }

    @Override
    public boolean puedeRealizarTransito() {
        return false;
    }

    @Override
    public boolean puedeRecibirBonificaciones() {
        return false;
    }

    @Override
    public boolean puedeSerNotificado() {
        return false;
    }
}
