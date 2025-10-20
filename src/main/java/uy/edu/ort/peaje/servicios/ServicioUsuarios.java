package uy.edu.ort.peaje.servicios;

import java.util.ArrayList;

import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.Usuario;

public class ServicioUsuarios {
    private ArrayList<Usuario> usuarios;
    
    public ServicioUsuarios() {
        usuarios = new ArrayList<>();
    }
    public void agregar(String cedula, String password, String nombreCompleto, int saldoActual, int saldoMinimo) {
        Usuario nuevoUsuario = new Usuario(cedula, password, nombreCompleto);
        usuarios.add(nuevoUsuario);
    }
    public Usuario login(String cedula, String password) throws PeajeException {
        for (Usuario u : usuarios) {
            if (u.getCedula().equals(cedula) && u.getPassword().equals(password)) {
                return u;
            }
        }
        throw new PeajeException("Credenciales inválidas");
    }
}
