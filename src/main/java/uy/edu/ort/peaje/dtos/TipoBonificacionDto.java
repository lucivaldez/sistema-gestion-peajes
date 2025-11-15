package uy.edu.ort.peaje.dtos;

import uy.edu.ort.peaje.modelo.TipoBonificacion;

public class TipoBonificacionDto {
    private String nombre;

    public TipoBonificacionDto(TipoBonificacion tb) {
        this.nombre = tb.getNombre();
    }

    public String getNombre() {
        return nombre;
    }
}
