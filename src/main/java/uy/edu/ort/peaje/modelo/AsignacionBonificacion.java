package uy.edu.ort.peaje.modelo;

import java.util.Date;

public class AsignacionBonificacion {
    
    private Date fechaAsignacion;
    private Bonificacion bonificacion;
    private Propietario propietario;
    private Puesto puesto;

    public AsignacionBonificacion(Date fechaAsignacion, Bonificacion bonificacion, Propietario propietario, Puesto puesto) {
        this.fechaAsignacion = fechaAsignacion;
        this.bonificacion = bonificacion;
        this.propietario = propietario;
        this.puesto = puesto;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public Bonificacion getBonificacion() {
        return bonificacion;
    }
    public Propietario getPropietario() {
        return propietario;
    }
    public Puesto getPuesto() {
        return puesto;
    }

    //verifica si la bonificacion aplica para el puesto recibido por parametro
    public boolean aplicaA(Puesto puesto){
        return this.puesto != null && this.puesto.equals(puesto);
    }


    
}
