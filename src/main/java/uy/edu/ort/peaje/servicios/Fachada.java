package uy.edu.ort.peaje.servicios;

import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.Usuario;

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

    public void agregar(String cedula, String password, String nombreCompleto, int saldoActual, int saldoMinimo) {
        sUsuarios.agregar(cedula, password, nombreCompleto, saldoActual, saldoMinimo);
    }

    public Usuario login(String cedula, String password) throws PeajeException {
        return sUsuarios.login(cedula, password);
    }

    

  

    
}
