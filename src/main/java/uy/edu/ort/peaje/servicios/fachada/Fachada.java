package uy.edu.ort.peaje.servicios.fachada;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uy.edu.ort.peaje.dtos.ResultadoDto;
import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.Administrador;
import uy.edu.ort.peaje.modelo.CategoriaVehiculo;
import uy.edu.ort.peaje.modelo.EstadoPropietario;
import uy.edu.ort.peaje.modelo.Propietario;
import uy.edu.ort.peaje.modelo.Puesto;
import uy.edu.ort.peaje.modelo.Sesion;
import uy.edu.ort.peaje.modelo.Tarifa;
import uy.edu.ort.peaje.modelo.TipoBonificacion;
import uy.edu.ort.peaje.modelo.Transito;
import uy.edu.ort.peaje.modelo.Vehiculo;
import uy.edu.ort.peaje.observador.Observable;

import uy.edu.ort.peaje.servicios.ServicioUsuarios;
import uy.edu.ort.peaje.servicios.ServicioTransito;

public class Fachada extends Observable{

    private static Fachada instancia;
    private ServicioUsuarios sUsuarios;
    private ServicioTransito sTransito;

    public enum Eventos {nuevoUsuarioConectado,usuarioDesconectado, cambioEstadoPropietario}
    
    private Fachada(){
        sUsuarios = new ServicioUsuarios();
        sTransito = new ServicioTransito();
    }
    
    public static Fachada getInstancia(){
        if (instancia == null){
            instancia = new Fachada();
        }
        return instancia;
    }

    public void agregar(Propietario propietario) {
        sUsuarios.agregar(propietario);
    }

    public void agregarAdmin(Administrador administrador) {
        sUsuarios.agregar(administrador);
    }

    public Sesion loginAdmin(String cedula, String password) throws PeajeException {
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

    public void agregarEstadoPropietario(EstadoPropietario ep) {
        sUsuarios.agregarEstadoPropietario(ep);
    }

    public ArrayList<EstadoPropietario> getEstadoPropietario() {
        return sUsuarios.getEstadosPropietario();
    }

    public void agregarCategoriaVehiculo(CategoriaVehiculo categoria) {
        sTransito.agregarCategoriaVehiculo(categoria);
    }

    public void agregarTarifa(Tarifa tarifa) {
        sTransito.agregarTarifa(tarifa);
    }

    public List<Tarifa> getTarifas() {
        return sTransito.getTarifas();
    }

    public Vehiculo buscarVehiculoPorMatricula(String matricula) throws PeajeException {
        return sTransito.buscarVehiculoPorMatricula(matricula);
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        sTransito.agregarVehiculo(vehiculo);
    }

    public void asignarBonificacion(Propietario propietario, String nombreBonificacion, Puesto puesto)
            throws PeajeException {
        sTransito.asignarBonificacion(propietario, nombreBonificacion, puesto);
    }

    public Transito emularTransito(Vehiculo vehiculo, Puesto puesto, Date fechaHora) throws PeajeException {
        return sTransito.emularTransito(vehiculo, puesto, fechaHora);
    }

    public Puesto buscarPuestoPorNombre(String nombre) {
        return sTransito.buscarPuestoPorNombre(nombre);
    }
    
    public Propietario buscarPropietarioPorCedula(String cedula) throws PeajeException {
        return sUsuarios.buscarPropietarioPorCedula(cedula);
    }

    public void agregarTipoBonificacion(String nombre) {
        sTransito.agregarTipoBonificacion(nombre);
    }

    public ArrayList<TipoBonificacion> getTiposBonificacion() {
        return sTransito.getTiposBonificacion();
    }

    public EstadoPropietario obtenerEstadoPorNombre(String nombre) {
        return sUsuarios.obtenerEstadoPorNombre(nombre);
    }

    public ResultadoDto construirResultadoTransito(Transito transito) {
        return sTransito.construirResultadoTransito(transito);
    }

}
