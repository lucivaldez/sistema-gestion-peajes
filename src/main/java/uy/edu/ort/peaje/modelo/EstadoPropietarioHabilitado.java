package uy.edu.ort.peaje.modelo;
// Habilitado: Es el estado por defecto de los propietarios cuando se dan de alta en el
// sistema. El propietario tiene todas las funcionalidades habilitadas.

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

