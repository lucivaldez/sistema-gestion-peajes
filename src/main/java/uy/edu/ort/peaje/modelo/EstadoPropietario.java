package uy.edu.ort.peaje.modelo;

import lombok.Getter;

public abstract class EstadoPropietario {

    @Getter
    private String nombreEstado;

    public EstadoPropietario(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    

    
}
