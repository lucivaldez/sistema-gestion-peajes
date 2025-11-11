package uy.edu.ort.peaje.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.AsignacionBonificacion;
import uy.edu.ort.peaje.modelo.Bonificacion;
import uy.edu.ort.peaje.modelo.CategoriaVehiculo;
import uy.edu.ort.peaje.modelo.Notificacion;
import uy.edu.ort.peaje.modelo.Propietario;
import uy.edu.ort.peaje.modelo.Puesto;
import uy.edu.ort.peaje.modelo.Tarifa;
import uy.edu.ort.peaje.modelo.Transito;
import uy.edu.ort.peaje.modelo.Vehiculo;



public class ServicioTransito {

    //experto en gestionar los transitos
    private ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo> ();
    private ArrayList<Puesto> puestos = new ArrayList<Puesto> ();
    private ArrayList<CategoriaVehiculo> categorias = new ArrayList<CategoriaVehiculo> ();
    private ArrayList<Tarifa> tarifas = new ArrayList<Tarifa> ();
    private ArrayList<Transito> listaTransitos = new ArrayList<Transito> ();
        
    public void agregarPuesto(Puesto puesto){
        puestos.add(puesto);
    }

      public ArrayList<Puesto> getPuestos(){
        return puestos;
    }

    public void agregarTransito(Transito transito){
       if (transito != null) listaTransitos.add(transito);
    }
 
    public void agregarCategoriaVehiculo(CategoriaVehiculo categoria){
        categorias.add(categoria);
    }
    
    public ArrayList<CategoriaVehiculo> getCategoriasVehiculos(){
        return categorias;
    }
    
    public void agregarTarifa(Tarifa tarifa){
        if (tarifa == null) return;
        tarifas.add(tarifa);

        Puesto p = tarifa.getPuesto();
        if (p != null) {
            p.addTarifa(tarifa); // 👉 el experto mantiene su colección
        }
    }

    public ArrayList<Tarifa> getTarifas(){
        return tarifas;
    }

    public void agregarVehiculo(Vehiculo vehiculo){
        vehiculos.add(vehiculo);
    }
    
    public ArrayList<Vehiculo> getVehiculos(){
        return vehiculos;
    }
  
    public void asignarBonificacion(Propietario propietario, Bonificacion bonificacion, Puesto puesto) {
        if (propietario == null || bonificacion == null || puesto == null) return;
        AsignacionBonificacion ab = new AsignacionBonificacion(new Date(), bonificacion, propietario, puesto);
        // Si tu Propietario usa otro nombre, ajustá aquí:
        propietario.asignarBonificacion(ab);
    }

    public double calcularMontoFinal(Transito transito) {
        double montoBase = transito.getTarifa().getMonto();
        double montoFinal = montoBase;

        Propietario propietario = transito.getVehiculo().getPropietario();
        Puesto puesto = transito.getPuesto();

        // Trae solo las bonificaciones asignadas a ese puesto
        List<AsignacionBonificacion> asignaciones = propietario.bonificacionesPara(puesto);

        for (AsignacionBonificacion ab : asignaciones) {
            Bonificacion b = ab.getBonificacion();
            double candidato = b.calcularMonto(transito); // cada Strategy aplica su regla

            if (candidato < montoFinal) {
                montoFinal = candidato;
                if (candidato == 0.0) break; // Exonerado
            }
        }

        return montoFinal;
    }

    public Transito emularTransito(Vehiculo vehiculo, Puesto puesto, Date fechaHora) throws PeajeException {
        if (vehiculo == null) throw new PeajeException("Vehículo nulo");
        if (puesto == null) throw new PeajeException("Puesto nulo");
        if (fechaHora == null) fechaHora = new Date();
        //se trae la tarifa por categoria
        Tarifa tarifa = puesto.getTarifaPorCategoria(vehiculo.getCategoriaVehiculo());
        if (tarifa == null) throw new PeajeException("No hay tarifa para la categoría del vehículo en el puesto");
        //el propietario del vehiculo
        Propietario propietario = vehiculo.getPropietario();
        if (propietario == null) throw new PeajeException("El vehículo no tiene propietario");
        //calcula el monto final a cobrar
        Transito t = new Transito(fechaHora, vehiculo, null, puesto, tarifa, 0.0);
        double montoFinal = calcularMontoFinal(t);     // elige la mejor bonificación o cobra tarifa base
        //verifica si el propietario tiene saldo suficiente
        if (propietario.getSaldoActual() < montoFinal) {
            throw new PeajeException("Saldo insuficiente");
        }
        //descuenta el saldo del propietario
        propietario.descontarSaldo(montoFinal);

        //todavia no se implementaron las notificaciones
        Notificacion notificacion = null;
        //crear el transito
        Transito transito = new Transito(fechaHora, vehiculo, notificacion, puesto, tarifa, montoFinal);


        vehiculo.registrarTransito(transito);
        listaTransitos.add(transito);
        return transito;
    }
}
