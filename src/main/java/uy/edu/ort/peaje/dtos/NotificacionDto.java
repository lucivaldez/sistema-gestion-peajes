package uy.edu.ort.peaje.dtos;

import uy.edu.ort.peaje.modelo.Notificacion;
import java.util.Date;

public class NotificacionDto {

    private long fechaHora;
    private String mensaje;
    private String tipo;

    public NotificacionDto(Notificacion n) {
        Date fh = n.getFecha();
        this.fechaHora = (fh != null) ? fh.getTime() : 0L;
        this.tipo = n.getTipo().name(); 
        this.mensaje = n.getMensaje();
   }

    public long getFechaHora() { return fechaHora; }
    public String getMensaje() { return mensaje; }
}