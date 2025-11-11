package uy.edu.ort.peaje.modelo;

import java.util.ArrayList;
import java.util.Objects;

public class Puesto {
    private int id;
    private String nombre;
    private String direccion;
    private ArrayList<Tarifa> tarifas;
    private ArrayList<AsignacionBonificacion> asignacionBonificacions;

    public Puesto( String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.tarifas = new ArrayList<>();
        this.asignacionBonificacions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public ArrayList<Tarifa> getTarifas() {
        return tarifas;
    }
    public void setTarifas(ArrayList<Tarifa> tarifas) {
        this.tarifas = tarifas;
    }
    public ArrayList<AsignacionBonificacion> getAsignacionBonificacions() {
        return asignacionBonificacions;
    }
    public void setAsignacionBonificacion(ArrayList<AsignacionBonificacion> bonificacion) {
        this.asignacionBonificacions = bonificacion;
    }

    public Tarifa getTarifaPorCategoria(CategoriaVehiculo categoria) {
        for (Tarifa tarifa : tarifas) {
            if (tarifa.getCategoriaVehiculo().equals(categoria)) {
                return tarifa;
            }
        }
        return null;
    }

    public void addTarifa(Tarifa t) {
        if (t == null) return;
        if (tarifas == null) tarifas = new ArrayList<>();

        // Regla común: una tarifa por categoría -> reemplaza si ya existe
        for (int i = 0; i < tarifas.size(); i++) {
            Tarifa existente = tarifas.get(i);
            if (existente.getCategoriaVehiculo().equals(t.getCategoriaVehiculo())) {
                tarifas.set(i, t);
                return;
            }
        }
        tarifas.add(t);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Puesto)) return false;
        Puesto other = (Puesto) o;
        return this.id == other.id; // igualdad solo por id
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // hash solo por id
    }
}
