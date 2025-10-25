package uy.edu.ort.peaje.servicios;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.Administrador;
import uy.edu.ort.peaje.modelo.Propietario;
import uy.edu.ort.peaje.modelo.Sesion;

public class Fachada {
    private static Fachada instancia;
    private ServicioUsuarios sUsuarios;
    
    public static Fachada getInstancia(){
        if (instancia == null){
            instancia = new Fachada();
        }
        return instancia;

    }

    private Fachada(){
        sUsuarios = new ServicioUsuarios();
    }

    public void agregar(Propietario propietario) {
        sUsuarios.agregar(propietario);
    }

    public void agregarAdmin(Administrador administrador) {
        sUsuarios.agregar(administrador);
    }

    public Administrador loginAdmin(String cedula, String password) throws PeajeException {
        return sUsuarios.loginAdmin(cedula, password);
    }

    public Sesion loginPropietario(String cedula, String password) throws PeajeException {
        return sUsuarios.loginPropietario(cedula, password);
    }

    public List<Sesion> getSesiones() {
        return sUsuarios.getSesiones();
    }

    

    

  

    
}
