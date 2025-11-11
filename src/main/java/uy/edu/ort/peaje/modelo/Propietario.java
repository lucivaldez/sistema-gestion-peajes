package uy.edu.ort.peaje.modelo;

import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.peaje.excepciones.PeajeException;

public class Propietario extends Usuario 
{
    private int saldoActual;
    private int saldoMinimo;
    //private EstadoPropietario estado;
    private ArrayList<AsignacionBonificacion> bonificacionesAsignadas;
    private ArrayList<Vehiculo> vehiculos; 
    private ArrayList<Notificacion> notificaciones;
    
    public Propietario(String cedula, String password, String nombreCompleto, int saldoActual, int saldoMinimo) {
        super(cedula, password, nombreCompleto);
        this.saldoActual = saldoActual;
        this.saldoMinimo = saldoMinimo;
        this.bonificacionesAsignadas = new ArrayList<AsignacionBonificacion>();
        this.vehiculos = new ArrayList<Vehiculo>();
        this.notificaciones = new ArrayList<Notificacion>();

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

    //  public EstadoPropietario getEstado() {
    //      return estado;
    //  }

    //  public void setEstado(EstadoPropietario estado) {
    //      this.estado = estado;
    //  }

    public ArrayList<AsignacionBonificacion> getAsignacionBonificacion() {
        return bonificacionesAsignadas;
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

    public void validarSaldoSuficiente(double monto) throws PeajeException {
        if (getSaldoActual() < monto)
            throw new PeajeException("Saldo insuficiente: $ " + String.format("%.2f", getSaldoActual()));
    }

    public void descontarSaldo(double monto) {
        setSaldoActual(getSaldoActual() - (int)monto);
    }

    public void asignarBonificacion(AsignacionBonificacion ab) {
        if (ab == null) return;
        if (bonificacionesAsignadas == null) bonificacionesAsignadas = new ArrayList<>();
        // Evita duplicar mismo tipo de bonificación en el mismo puesto
        for (AsignacionBonificacion existente : bonificacionesAsignadas) {
            if (existente.getPuesto().equals(ab.getPuesto()) &&
                existente.getBonificacion().getClass().equals(ab.getBonificacion().getClass())) {
                // ya existe: no agregar
                return;
            }
        }
        bonificacionesAsignadas.add(ab);
    }

    public List<AsignacionBonificacion> bonificacionesPara(Puesto puesto) {
        List<AsignacionBonificacion> resultado = new ArrayList<>();
        for (AsignacionBonificacion ab : bonificacionesAsignadas) {
            if (ab.aplicaA(puesto)) {
                resultado.add(ab);
            }
        }
        return resultado;
    }

    public Vehiculo buscarVehiculoDelPropietario(String matricula) throws PeajeException {
        for (Vehiculo v : vehiculos) {
            if (v.getMatricula().equalsIgnoreCase(matricula)) {
                return v;
            }
        }
        throw new PeajeException("No se encontró un vehículo con la matrícula: " + matricula);
    }
}
