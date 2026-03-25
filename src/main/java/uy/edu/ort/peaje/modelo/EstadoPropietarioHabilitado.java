package uy.edu.ort.peaje.modelo;

public class EstadoPropietarioHabilitado extends EstadoPropietario {

    public EstadoPropietarioHabilitado() {
        super("Habilitado");
    }

    @Override
    public boolean puedeIngresarAlSistema() {
        return true;
    }

    @Override  
    public boolean puedeRealizarTransito() {
        return true;
    }

    @Override
    public boolean puedeSerNotificado() {
        return true;
    }

    @Override
    public boolean puedeRecibirBonificaciones() {
        return true;
    }
}

