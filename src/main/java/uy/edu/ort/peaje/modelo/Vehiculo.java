package uy.edu.ort.peaje.modelo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

public class Vehiculo {
    private String matricula;
    private String modelo;
    private String color;
    private CategoriaVehiculo categoriaVehiculo;
    private Propietario propietario;
    //historial de transitos de ese vehiculo
    private final List<Transito> historialTransitos = new ArrayList<>();
 
    public Vehiculo(String matricula, String modelo, String color, CategoriaVehiculo categoriaVehiculo, Propietario propietario) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.color = color;
        this.categoriaVehiculo = categoriaVehiculo;
        this.propietario = propietario;
    }

    public String getMatricula() {
        return matricula;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public CategoriaVehiculo getCategoriaVehiculo() {
        return categoriaVehiculo;
    }
    
    public void setCategoriaVehiculo(CategoriaVehiculo categoriaVehiculo) {
        this.categoriaVehiculo = categoriaVehiculo;
    }

    //si en frecuente se concreta el transito lo registra en el historial asi puede aplicar el 50%
    public void registrarTransito(Transito transito) {
        if (transito != null) {
            historialTransitos.add(transito);
        }
    }

    public int getPasadasPorDia(Date fechaHoraActual, Puesto puesto) {
        if (fechaHoraActual == null || puesto == null) return 0;

        int count = 0;
        for (Transito t : historialTransitos) {
            if (t == null || t.getFechaHora() == null) continue;

            // mismo puesto
            if (!puesto.equals(t.getPuesto())) continue;

            // misma fecha (día civil)
            if (!mismoDia(t.getFechaHora(), fechaHoraActual)) continue;

            // solo pasadas anteriores (no incluir la actual)
            if (t.getFechaHora().before(fechaHoraActual)) {
                count++;
            }
        }
        return count;
    }

    private boolean mismoDia(Date a, Date b) {
        if (a == null || b == null) return false;
        Calendar ca = Calendar.getInstance();
        Calendar cb = Calendar.getInstance();
        ca.setTime(a);
        cb.setTime(b);
        return ca.get(Calendar.YEAR) == cb.get(Calendar.YEAR)
            && ca.get(Calendar.DAY_OF_YEAR) == cb.get(Calendar.DAY_OF_YEAR);
    }  
}
