package uy.edu.ort.peaje.modelo;
import java.util.Date;


public class Notificacion {
    private Date fecha;
    private TipoNotificacion tipo;
    private String mensaje;
    private Propietario propietario;

    public Notificacion(TipoNotificacion tipo, String mensaje, Propietario propietario) {
        this.fecha = new Date();
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.propietario = propietario;
    }

    public Date getFecha() {
        return fecha;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Propietario getPropietario() {
        return propietario;
    }


}
