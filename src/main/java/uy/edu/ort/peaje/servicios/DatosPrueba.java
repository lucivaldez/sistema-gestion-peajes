package uy.edu.ort.peaje.servicios;

public class DatosPrueba {
    public static void cargar(){
        Fachada.getInstancia().agregar("12345678", "password1", "Juan Perez", 1000, 100);
        Fachada.getInstancia().agregar("11111111", "password1", "Ana Gomez", 1500, 150);
        Fachada.getInstancia().agregar("12312312", "password1", "Luis Martinez", 2000, 200);
    }
    
}
