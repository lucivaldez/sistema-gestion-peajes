package uy.edu.ort.peaje.servicios.fachada;

import java.util.List;

import uy.edu.ort.peaje.dtos.EmularTransitoDto;
import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.Administrador;
import uy.edu.ort.peaje.modelo.Bonificacion;
import uy.edu.ort.peaje.modelo.CategoriaVehiculo;
import uy.edu.ort.peaje.modelo.Propietario;
import uy.edu.ort.peaje.modelo.Puesto;
import uy.edu.ort.peaje.modelo.Sesion;
import uy.edu.ort.peaje.modelo.Tarifa;
import uy.edu.ort.peaje.modelo.Transito;
import uy.edu.ort.peaje.modelo.Vehiculo;
import uy.edu.ort.peaje.servicios.ServicioUsuarios;
import uy.edu.ort.peaje.servicios.ServicioTransito;



public class Fachada {
    private static Fachada instancia;
    private ServicioUsuarios sUsuarios;
    private ServicioTransito sTransito;
    
    public static Fachada getInstancia(){
        if (instancia == null){
            instancia = new Fachada();
        }
        return instancia;
    }

    private Fachada(){
        sUsuarios = new ServicioUsuarios();
        sTransito = new ServicioTransito();
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

    public void agregarPuesto(Puesto puesto) {
        sTransito.agregarPuesto(puesto);
    }
    public List<Puesto> getPuestos() {
        return sTransito.getPuestos();
    }

    public void agregarCategoriaVehiculo(CategoriaVehiculo categoria) {
        sTransito.agregarCategoriaVehiculo(categoria);  
    }  

    public List<CategoriaVehiculo> getCategoriaVehiculos() {
        return sTransito.getCategoriasVehiculos();
    }
    
    public void agregarTarifa(Tarifa tarifa){
        sTransito.agregarTarifa(tarifa);
    }

    public List<Tarifa> getTarifas() {
        return sTransito.getTarifas();
    }

    public void agregarTransito(Transito transito){
        sTransito.agregarTransito(transito);
    }
    
    public Vehiculo buscarVehiculoPorMatricula(String matricula) throws PeajeException {
        return sUsuarios.buscarVehiculoPorMatricula(matricula);
    }

 
    
}

    

    public void agregarTipoBonificacion(Bonificacion bonificacion) {
        sTransito.agregarTipoBonificacion(bonificacion);
    }

    public void asignarBonificacion(Propietario propietario, Bonificacion bonificacion, Puesto puesto) {
        sTransito.asignarBonificacion(propietario, bonificacion, puesto);
    }

    // public EmularTransitoDto emularTransito(String nombrePuesto, String matricula, String fecha) throws PeajeException {
    //     return sTransito.emularTransito(nombrePuesto, matricula, fecha);
    // }


    

