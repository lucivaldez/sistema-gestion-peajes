package uy.edu.ort.peaje.modelo;
import java.util.Date;


public class Notificacion {
    private Date fecha;
    private TipoNotificacion tipo;
    private Transito transito;
    private Propietario propietario;

    public Notificacion(Date fecha, TipoNotificacion tipo, Transito transito, Propietario propietario) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.transito = transito;
        this.propietario = propietario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoNotificacion tipo) {
        this.tipo = tipo;
    }

    public Transito getTransito() {
        return transito;
    }

    public void setTransito(Transito transito) {
        this.transito = transito;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    
    
}
