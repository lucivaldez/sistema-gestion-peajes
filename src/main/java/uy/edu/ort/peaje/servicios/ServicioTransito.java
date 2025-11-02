package uy.edu.ort.peaje.servicios;

import java.util.ArrayList;
import uy.edu.ort.peaje.modelo.CategoriaVehiculo;
import uy.edu.ort.peaje.modelo.Puesto;
import uy.edu.ort.peaje.modelo.Tarifa;



public class ServicioTransito {
    
    private ArrayList<Puesto> puestos = new ArrayList<Puesto> ();
    private ArrayList<CategoriaVehiculo> categorias = new ArrayList<CategoriaVehiculo> ();
    private ArrayList<Tarifa> tarifas = new ArrayList<Tarifa> ();
        
    public void agregarPuesto(Puesto puesto){
        puestos.add(puesto);
    }
    public ArrayList<Puesto> getPuestos(){
        return puestos;
    }

    public void agregarCategoriaVehiculo(CategoriaVehiculo categoria){
        categorias.add(categoria);
    }
    
    public ArrayList<CategoriaVehiculo> getCategoriasVehiculos(){
        return categorias;
    }
    
    public void agregarTarifa(Tarifa tarifa){
        tarifas.add(tarifa);
    }

    public ArrayList<Tarifa> getTarifas(){
        return tarifas;
    }

}
