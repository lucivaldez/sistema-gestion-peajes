package uy.edu.ort.peaje.modelo;

import java.util.Date;


public class Sesion {
    
    private Date fechaIngreso = new Date();
    private Propietario usuario;

    public Sesion( Propietario usuario) {
        this.usuario = usuario;
    }


    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public Propietario getUsuario() {
        return usuario;
    }       
}

