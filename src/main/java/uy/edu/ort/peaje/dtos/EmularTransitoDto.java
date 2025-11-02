package uy.edu.ort.peaje.dtos;

import java.util.Date;

import uy.edu.ort.peaje.modelo.Transito;

public class EmularTransitoDto  {
    private String puesto;
    private String matricula;
    private Date fechaHora;

    public EmularTransitoDto(Transito transito) {
        puesto = transito.getPuesto().getNombre()   ;
        matricula = transito.getVehiculo().getMatricula();
        fechaHora = transito.getFechaHora();
    }

    public String getPuesto() {
        return puesto;
    }
    public String getMatricula() {
        return matricula;
    }
    public Date getFechaHora() {
        return fechaHora;
    }
}
