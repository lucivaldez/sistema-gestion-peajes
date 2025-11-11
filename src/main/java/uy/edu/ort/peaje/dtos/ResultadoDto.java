package uy.edu.ort.peaje.dtos;

public class ResultadoDto {

    private String propietario;
    private String estado;
    private String categoria;
    private String bonificacion;
    private double costo;
    private double saldo;

    public ResultadoDto(String propietario, String estado, String categoria, String bonificacion, double costo,
            double saldo) {
        this.propietario = propietario;
        this.estado = estado;
        this.categoria = categoria;
        this.bonificacion = bonificacion;
        this.costo = costo;
        this.saldo = saldo;
    }

    public String getPropietario() {
        return propietario;
    }
    public String getEstado() {
        return estado;
    }
    public String getCategoria() {
        return categoria;
    }
    public String getBonificacion() {
        return bonificacion;
    }
    public double getCosto() {
        return costo;
    }
    public double getSaldo() {
        return saldo;
    }
}
