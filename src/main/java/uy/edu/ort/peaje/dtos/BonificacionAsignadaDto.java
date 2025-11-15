package uy.edu.ort.peaje.dtos;

import uy.edu.ort.peaje.modelo.AsignacionBonificacion;

public class BonificacionAsignadaDto {

    private String nombre;
    private String nombrePuesto;
    private long fechaAsignacion; 

    public BonificacionAsignadaDto(AsignacionBonificacion ab) {
        this.nombre = ab.getBonificacion().getnombre();
        this.nombrePuesto = ab.getPuesto() != null ? ab.getPuesto().getNombre() : "";
        this.fechaAsignacion = ab.getFechaAsignacion() != null ? ab.getFechaAsignacion().getTime() : 0L;
    }

    public String getNombreBonificacion() { 
        return nombre; 
    }

    public String getNombrePuesto() { 
        return nombrePuesto; 
    }

    public long getFechaAsignacion() { 
        return fechaAsignacion; 
    }
}
