package uy.edu.ort.peaje.modelo;

public class Vehiculo {
    private String matricula;
    private String modelo;
    private String color;
    private CategoriaVehiculo categoriaVehiculo;
    private Propietario propietario;
    
    public Vehiculo(String matricula, String modelo, String color, CategoriaVehiculo categoriaVehiculo) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.color = color;
        this.categoriaVehiculo = categoriaVehiculo;
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



    
}
