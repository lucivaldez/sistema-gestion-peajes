package uy.edu.ort.peaje.servicios;

import uy.edu.ort.peaje.modelo.Administrador;
import uy.edu.ort.peaje.modelo.Propietario;

public class DatosPrueba {
    public static void cargar(){
        Fachada fachada = Fachada.getInstancia();
        Administrador admin1 = new Administrador("1548789", "pass1", "Administrador Uno");
        fachada.agregarAdmin(admin1);

        Propietario prop1 = new Propietario("12345678", "prop1", "Propietario Uno", 100, 200);
        fachada.agregar(prop1);
    }
    
}
