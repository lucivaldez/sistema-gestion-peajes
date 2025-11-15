package uy.edu.ort.peaje.modelo;

public class FabricaBonificaciones {

    public static Bonificacion crearBonificacion(String nombreTipo) {
        
        try {

            if (nombreTipo == null) {
            throw new IllegalArgumentException("Debe especificar un tipo de bonificación");
            }

            switch (nombreTipo) {
                case "Exonerado":
                    return new Exonerado("Exonerado");
                case "Frecuente":  
                    return new Frecuente("Frecuente");
                case "Trabajador":
                    return new Trabajador("Trabajador");
            
                default: throw new IllegalArgumentException("Tipo de bonificación desconocido: " + nombreTipo);         
            }
        
        }catch (Exception e) {

            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
