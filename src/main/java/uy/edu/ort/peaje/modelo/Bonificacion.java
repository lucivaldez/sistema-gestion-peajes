package uy.edu.ort.peaje.modelo;

public class Bonificacion {
    private String nombreBonificacion;
    private Puesto puesto;
    private Usuario usuario;

    public Bonificacion(String nombreBonificacion, Puesto puesto, Usuario usuario) {
        this.nombreBonificacion = nombreBonificacion;
        this.puesto = puesto;
        this.usuario = usuario;
    }

    public String getNombreBonificacion() {
        return nombreBonificacion;
    }

    public void setNombreBonificacion(String nombreBonificacion) {
        this.nombreBonificacion = nombreBonificacion;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }   

    
}
