package uy.edu.ort.peaje.modelo;

// Penalizado: El usuario puede ingresar al sistema, pero no se le registran notificaciones.
// Puede realizar tránsitos, pero no aplican las bonificaciones que tenga asignadas.

public class EstadoPropietarioPenalizado extends EstadoPropietario {

    public EstadoPropietarioPenalizado(){
        super("Penalizado");
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
        return false;
    }
    @Override
    public boolean puedeRecibirBonificaciones() {
        return false;
    }

}
