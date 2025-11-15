package uy.edu.ort.peaje.modelo;

// Deshabilitado: El usuario no puede ingresar al sistema ni puede realizar tránsitos.
// Tampoco se le pueden asignar bonificaciones.

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
