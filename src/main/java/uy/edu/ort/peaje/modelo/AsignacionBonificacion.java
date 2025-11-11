package uy.edu.ort.peaje.modelo;

import java.util.Date;
import java.util.Objects;

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

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsignacionBonificacion)) return false;
        AsignacionBonificacion that = (AsignacionBonificacion) o;
        return Objects.equals(propietario, that.propietario)
            && Objects.equals(puesto, that.puesto)
            && Objects.equals(bonificacion.getClass(), that.bonificacion.getClass());
    }

    @Override public int hashCode() {
        return Objects.hash(propietario, puesto, bonificacion.getClass());
    }    
}
