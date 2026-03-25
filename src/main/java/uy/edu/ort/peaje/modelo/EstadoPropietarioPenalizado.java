package uy.edu.ort.peaje.modelo;


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
