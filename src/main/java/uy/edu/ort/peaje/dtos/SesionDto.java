package uy.edu.ort.peaje.dtos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.peaje.modelo.Sesion;

public class SesionDto {
    private String fechaIngreso;
    private String usuario;
    private String nombreCompleto;
    
    public SesionDto(Sesion sesion) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        fechaIngreso = sdf.format(sesion.getFechaIngreso());
        usuario = sesion.getUsuario().getNombreCompleto();
        nombreCompleto = sesion.getUsuario().getNombreCompleto();
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public static List<SesionDto> listaSesionesDto(List<Sesion> sesiones) {
                
        List<SesionDto> sesionDtos = new ArrayList<>();
        for (Sesion sesion : sesiones) {
            sesionDtos.add(new SesionDto(sesion));
        }
        return sesionDtos;
    }
}
