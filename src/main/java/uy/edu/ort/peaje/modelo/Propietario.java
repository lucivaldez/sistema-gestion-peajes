package uy.edu.ort.peaje.modelo;

import java.util.ArrayList;

public class Propietario extends Usuario 
{
    private int saldoActual;
    private int saldoMinimo;
    private EstadoPropietario estado;
    private ArrayList<Bonificacion> bonificacion;
    private ArrayList<Vehiculo> vehiculos; 
    private ArrayList<Notificacion> notificaciones;
    
    public Propietario(String cedula, String password, String nombreCompleto, int saldoActual, int saldoMinimo) {
        super(cedula, password, nombreCompleto);
         this.saldoActual = saldoActual;
         this.saldoMinimo = saldoMinimo;
         //this.estado = EstadoPropietario.HABILITADO;
     }

     //PARA EMULAR TRANSITO
     public Vehiculo buscarVehiculoPorMatricula(String matricula) {
         for (Vehiculo v : vehiculos) {
             if (v.getMatricula().equalsIgnoreCase(matricula)) {
                 return v;
             }
         }
         return null;
     }

    //  public void validarSaldo(double monto) {
    //      if (saldoActual < monto) {
    //          estado = EstadoPropietario.INHABILITADO;
    //      } else {
    //          estado = EstadoPropietario.HABILITADO;
    //      }
    //  }

     public int getSaldoActual() {
         return saldoActual;
     }

     public void setSaldoActual(int saldoActual) {
         this.saldoActual = saldoActual;
     }

     public int getSaldoMinimo() {
         return saldoMinimo;
     }

     public void setSaldoMinimo(int saldoMinimo) {
         this.saldoMinimo = saldoMinimo;
     }

     public EstadoPropietario getEstado() {
         return estado;
     }

     public void setEstado(EstadoPropietario estado) {
         this.estado = estado;
     }

     public ArrayList<Bonificacion> getBonificacion() {
         return bonificacion;
     }

     public void setBonificacion(ArrayList<Bonificacion> bonificacion) {
         this.bonificacion = bonificacion;
     }

     public ArrayList<Vehiculo> getVehiculos() {
         return vehiculos;
     }

     public void setVehiculos(ArrayList<Vehiculo> vehiculos) {
         this.vehiculos = vehiculos;
     }

     public ArrayList<Notificacion> getNotificaciones() {
         return notificaciones;
     }

     public void setNotificaciones(ArrayList<Notificacion> notificaciones) {
         this.notificaciones = notificaciones;
     }

}
