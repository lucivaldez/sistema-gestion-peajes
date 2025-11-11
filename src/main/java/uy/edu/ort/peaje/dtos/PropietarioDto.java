package uy.edu.ort.peaje.dtos;

import uy.edu.ort.peaje.modelo.Propietario;

public class PropietarioDto {
    private String cedula;
    private String nombreCompleto;
    private int saldoActual;
    private int saldoMinimo;
    // private String estado; // si luego lo agregan

    public PropietarioDto(Propietario p) {
        this.cedula = p.getCedula();
        this.nombreCompleto = p.getNombreCompleto();
        this.saldoActual = p.getSaldoActual();
        this.saldoMinimo = p.getSaldoMinimo();
        // this.estado = p.getEstado().name();
    }

    public String getCedula() { 
        return cedula; 
    }

    public String getNombreCompleto() { 
        return nombreCompleto; 
    }

    public int getSaldoActual() { 
        return saldoActual; 
    }

    public int getSaldoMinimo() { 
        return saldoMinimo; 
    }
    
    // public String getEstado() { return estado; }
}

