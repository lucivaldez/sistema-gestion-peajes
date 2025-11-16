package uy.edu.ort.peaje.modelo;

public abstract class EstadoPropietario {
    private String nombre;

    public EstadoPropietario( String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public abstract boolean puedeIngresarAlSistema();
    public abstract boolean puedeRealizarTransito();
    public abstract boolean puedeSerNotificado();
    public abstract boolean puedeRecibirBonificaciones();  
}





