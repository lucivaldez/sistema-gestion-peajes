package uy.edu.ort.peaje.servicios;

import java.util.ArrayList;

import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.CategoriaVehiculo;
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

    public void agregarTransito(Transito transito){
        listaTransitos.add(transito);
    }

//     public Transito emularTransito(Vehiculo vehiculo, Puesto puesto, String fechaHora) throws PeajeException {
//     if (vehiculo == null) throw new PeajeException("Vehículo nulo");
//     Tarifa tarifa = puesto.getTarifaPorCategoria(vehiculo.getCategoriaVehiculo());
//     if (tarifa == null) throw new PeajeException("No hay tarifa para esa categoría en el puesto");
//     // calcular monto final (bonificaciones, etc.)
//     double montoFinal = calcularMontoFinal(vehiculo.getPropietario(), puesto, tarifa);
//     Transito t = new Transito(vehiculo, puesto, montoFinal, fechaHora);
//     // registrar transito (lista interna, notificaciones, etc.)
//     this.transitos.add(t);
//     return t;
// }
 
    public ArrayList<Puesto> getPuestos(){
        return puestos;
    }

    // === Categorías ===
    public void agregarCategoriaVehiculo(CategoriaVehiculo categoria){
        categorias.add(categoria);
    }
    
    public ArrayList<CategoriaVehiculo> getCategoriasVehiculos(){
        return categorias;
    }
    
    // === Tarifas ===
    public void agregarTarifa(Tarifa tarifa){
        tarifas.add(tarifa);
    }

    public ArrayList<Tarifa> getTarifas(){
        return tarifas;
    }


    // === Vehiculo ===
    public void agregarVehiculo(Vehiculo vehiculo){
        vehiculos.add(vehiculo);
    }
    
    public ArrayList<Vehiculo> getVehiculos(){
        return vehiculos;
    }

    // === Bonificaciones ===

    public void agregarTipoBonificacion(Bonificacion bonificacion) {
        tiposBonificacion.add(bonificacion);
    }

    public ArrayList<Bonificacion> getTiposBonificacion() {
        return tiposBonificacion;
    }

    public void asignarBonificacion(Propietario propietario, Bonificacion bonificacion, Puesto puesto) {
        AsignacionBonificacion ab = new AsignacionBonificacion(new Date(), bonificacion, propietario, puesto);
        propietario.asignacionBonificacion(ab);
    }

    // === Transitos ===

    public double calcularMontoFinal(Transito transito) {
        double montoBase = transito.getTarifa().getMonto();
        double montoFinal = montoBase;

        Propietario propietario = transito.getVehiculo().getPropietario();

        for (AsignacionBonificacion ab : propietario.getAsignacionBonificacion()) {
             Bonificacion b = ab.getBonificacion();

            if (ab.aplicaA(transito.getPuesto())) {
                montoFinal = b.calcularMonto(transito);
                break; 
            }
        }   

        // Si ninguna bonificación aplica, se cobra el monto completo
        return montoFinal;
    }

    // public Transito emularTransito (Puesto p, String matricula, Date fecha){

    //     vehiculo v = Fachada.getInstancia().buscarVehiculoPorMatricula(matricula);
    // }




    



}
