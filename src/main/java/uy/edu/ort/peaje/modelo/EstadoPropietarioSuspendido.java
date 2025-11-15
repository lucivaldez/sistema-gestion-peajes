package uy.edu.ort.peaje.modelo;

// Suspendido: El usuario puede ingresar al sistema, pero no puede realizar tránsitos.

public class EstadoPropietarioSuspendido extends EstadoPropietario {

    public EstadoPropietarioSuspendido() {
        super("Suspendido");
    }
    @Override
    public boolean puedeIngresarAlSistema() {
        return true;
    }
    @Override
    public boolean puedeRealizarTransito() {
        return false;
    }
    @Override
    public boolean puedeSerNotificado() {
        return true;
    }
    @Override
    public boolean puedeRecibirBonificaciones() {
        return false;
    }


}
