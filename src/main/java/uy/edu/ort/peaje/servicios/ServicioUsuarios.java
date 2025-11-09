package uy.edu.ort.peaje.servicios;

import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.Administrador;
import uy.edu.ort.peaje.modelo.Bonificacion;
import uy.edu.ort.peaje.modelo.Propietario;
import uy.edu.ort.peaje.modelo.Sesion;
import uy.edu.ort.peaje.modelo.Usuario;
import uy.edu.ort.peaje.modelo.Vehiculo;

public class ServicioUsuarios {
    //experto en gestionar los usuarios
    private ArrayList<Propietario> propietarios;
    private ArrayList<Administrador> administradores;
    private ArrayList<Sesion> sesiones;

    
    public ServicioUsuarios() {
        this.propietarios = new ArrayList<>();
        this.administradores = new ArrayList<>();
        this.sesiones = new ArrayList<>();
    }
    public void agregar(Propietario propietario) {
        propietarios.add(propietario);
       
    }
    public void agregar(Administrador administrador) {
        administradores.add(administrador);
       
    }

    private Usuario login(String cedula, String password, List lista) throws PeajeException{
        Usuario usuario;
        for (Object o: lista) {
            usuario = (Usuario)o;
            if (usuario.getCedula().equals(cedula) && usuario.esContrasenaValida(password)) {
                return usuario;
            }
        }
        return null;
    }

    public Administrador loginAdmin(String cedula, String password) throws PeajeException{
        
        Administrador usuario = (Administrador) login(cedula, password, administradores);
        if(usuario!=null){
            return usuario;
        }        
        throw new PeajeException("Usuario y/o contraseña incorrectos");
    }

    public Sesion loginPropietario(String cedula, String password) throws PeajeException{
        Sesion sesion = null;
        Propietario usuario = (Propietario) login(cedula, password, propietarios);
        if(usuario!=null){
            sesion = new Sesion(usuario);
            sesiones.add(sesion);
            return sesion;
        }        
        throw new PeajeException("Usuario y/o contraseña incorrectos");
    }

    public List<Sesion> getSesiones() {
        return sesiones;
    }

    public Vehiculo buscarVehiculoPorMatricula(String matricula) throws PeajeException {
    for (Propietario p : propietarios) {
        Vehiculo encontrado = p.buscarVehiculoPorMatricula(matricula);
        if (encontrado != null) {
            return encontrado;
        }
    }
    throw new PeajeException("No existe vehículo con matrícula " + matricula);
}
 
}
