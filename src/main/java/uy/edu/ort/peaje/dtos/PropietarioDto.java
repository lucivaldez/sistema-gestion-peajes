package uy.edu.ort.peaje.dtos;

import uy.edu.ort.peaje.modelo.Propietario;

public class PropietarioDto {
    private String cedula;
    private String nombreCompleto;
    private int saldoActual;
    private int saldoMinimo;
    private String estadoActual;


    public PropietarioDto(Propietario p) {
        this.cedula = p.getCedula();
        this.nombreCompleto = p.getNombreCompleto();
        this.saldoActual = p.getSaldoActual();
        this.saldoMinimo = p.getSaldoMinimo();
        this.estadoActual = p.getEstadoPropietario().getNombre(); 
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
    
    public String getestadoActual() { 
        return estadoActual; 
    }
    
}

