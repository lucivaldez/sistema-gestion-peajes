package uy.edu.ort.peaje.servicios;
 
import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.Administrador;
import uy.edu.ort.peaje.modelo.CategoriaVehiculo;
import uy.edu.ort.peaje.modelo.EstadoPropietario;
import uy.edu.ort.peaje.modelo.EstadoPropietarioDeshabilitado;
import uy.edu.ort.peaje.modelo.EstadoPropietarioHabilitado;
import uy.edu.ort.peaje.modelo.EstadoPropietarioPenalizado;
import uy.edu.ort.peaje.modelo.EstadoPropietarioSuspendido;
import uy.edu.ort.peaje.modelo.Propietario;
import uy.edu.ort.peaje.modelo.Puesto;
import uy.edu.ort.peaje.modelo.Tarifa;
import uy.edu.ort.peaje.modelo.Vehiculo;
import uy.edu.ort.peaje.servicios.fachada.Fachada;
 
public class DatosPrueba {
    public static void cargar() throws PeajeException { 
        Fachada fachada = Fachada.getInstancia();
 
        Puesto puesto1 = new Puesto("Peaje Canelones", "Av. Principal 1234");
        Puesto puesto2 = new Puesto("Peaje Montevideo", "Calle Norte 5678");
        Puesto puesto3 = new Puesto("Peaje Maldonado", "Ruta Sur 91011");

        Fachada.getInstancia().agregarPuesto(puesto1);
        Fachada.getInstancia().agregarPuesto(puesto2);
        Fachada.getInstancia().agregarPuesto(puesto3);
 
        CategoriaVehiculo cat1 = new CategoriaVehiculo("Automóvil");
        CategoriaVehiculo cat2 = new CategoriaVehiculo("Camion");
        CategoriaVehiculo cat3 = new CategoriaVehiculo("Moto");
        CategoriaVehiculo cat4 = new CategoriaVehiculo("Bus");
 
        Fachada.getInstancia().agregarCategoriaVehiculo(cat1);
        Fachada.getInstancia().agregarCategoriaVehiculo(cat2);
        Fachada.getInstancia().agregarCategoriaVehiculo(cat3);
        Fachada.getInstancia().agregarCategoriaVehiculo(cat4);
 
        // Datos de prueba de administradores y propietarios
 
        Administrador admin1 = new Administrador("12345678", "admin.123", "Usuario Administrador");
        Administrador admin2 = new Administrador("18765433", "admin.123", "Admin Dos");
        fachada.agregarAdmin(admin1);
        fachada.agregarAdmin(admin2);

 
        Propietario prop1 = new Propietario("23456789", "prop.123", "Usuario Propietario", 2000, 500);
        Propietario prop2 = new Propietario("11223344", "prop.123", "Ana Maria", 1000, 200);
        fachada.agregar(prop1);
        fachada.agregar(prop2);
 
        // tarifas puesto 1
        Tarifa tarifa1 = new Tarifa(puesto1, 50, cat1);
        Tarifa tarifa2 = new Tarifa(puesto1, 100, cat2);
        Tarifa tarifa3 = new Tarifa(puesto1, 60, cat3);
        Tarifa tarifa4 = new Tarifa(puesto1, 150, cat4);
 
        // tarifas puesto 2
        Tarifa tarifa5 = new Tarifa(puesto2, 70, cat1);
        Tarifa tarifa6 = new Tarifa(puesto2, 120, cat2);
        Tarifa tarifa7 = new Tarifa(puesto2, 80, cat3);
        Tarifa tarifa8 = new Tarifa(puesto2, 170, cat4);
 
        // tarifas puesto 3
        Tarifa tarifa9 = new Tarifa(puesto3, 60, cat1);
        Tarifa tarifa10 = new Tarifa(puesto3, 110, cat2);
        Tarifa tarifa11 = new Tarifa(puesto3, 70, cat3);
        Tarifa tarifa12 = new Tarifa(puesto3, 160, cat4);
 
        Fachada.getInstancia().agregarTarifa(tarifa1);
        Fachada.getInstancia().agregarTarifa(tarifa2);
        Fachada.getInstancia().agregarTarifa(tarifa3);
        Fachada.getInstancia().agregarTarifa(tarifa4);
        Fachada.getInstancia().agregarTarifa(tarifa5);
        Fachada.getInstancia().agregarTarifa(tarifa6);
        Fachada.getInstancia().agregarTarifa(tarifa7);
        Fachada.getInstancia().agregarTarifa(tarifa8);
        Fachada.getInstancia().agregarTarifa(tarifa9);
        Fachada.getInstancia().agregarTarifa(tarifa10);
        Fachada.getInstancia().agregarTarifa(tarifa11);
        Fachada.getInstancia().agregarTarifa(tarifa12);
 
        Vehiculo v1 = new Vehiculo("ABC123", "Toyota Corolla", "Rojo", cat1, prop1);
        Vehiculo v2 = new Vehiculo("DEF456", "Honda Civic", "Azul", cat1, prop2);
        Fachada.getInstancia().agregarVehiculo(v1);
        Fachada.getInstancia().agregarVehiculo(v2);
 
        // Asigna vehiculo a propietario
        Fachada.getInstancia().agregarVehiculoAPropietario(prop1, v1);
        Fachada.getInstancia().agregarVehiculoAPropietario(prop2, v2);
 
 
        // Agrega tipos de bonificaciones
        Fachada.getInstancia().agregarTipoBonificacion("Exonerado");
        Fachada.getInstancia().agregarTipoBonificacion("Frecuente");
        Fachada.getInstancia().agregarTipoBonificacion("Trabajador");
 
        // Asigna bonificaciones a propietarios en puestos
        Fachada.getInstancia().asignarBonificacion(prop1, "Exonerado", puesto1);
        Fachada.getInstancia().asignarBonificacion(prop1, "Frecuente", puesto2);
        Fachada.getInstancia().asignarBonificacion(prop2, "Trabajador", puesto1);
        Fachada.getInstancia().asignarBonificacion(prop2, "Frecuente", puesto3);    
 
        // estados
        EstadoPropietario estadoHabilitado = new EstadoPropietarioHabilitado();
        EstadoPropietario estadoDeshabilitado = new EstadoPropietarioDeshabilitado();
        EstadoPropietario estadoSuspendido = new EstadoPropietarioSuspendido();
        EstadoPropietario estadoPenalizado = new EstadoPropietarioPenalizado();
 
        Fachada.getInstancia().agregarEstadoPropietario(estadoHabilitado);
        Fachada.getInstancia().agregarEstadoPropietario(estadoDeshabilitado);
        Fachada.getInstancia().agregarEstadoPropietario(estadoSuspendido);
        Fachada.getInstancia().agregarEstadoPropietario(estadoPenalizado);
    }
}