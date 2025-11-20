package uy.edu.ort.peaje.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uy.edu.ort.peaje.dtos.ResultadoDto;
import uy.edu.ort.peaje.dtos.TransitoTableroDto;
import uy.edu.ort.peaje.dtos.VehiculoTableroDto;
import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.AsignacionBonificacion;
import uy.edu.ort.peaje.modelo.Bonificacion;
import uy.edu.ort.peaje.modelo.CategoriaVehiculo;
import uy.edu.ort.peaje.modelo.EstadoPropietario;
import uy.edu.ort.peaje.modelo.FabricaBonificaciones;
import uy.edu.ort.peaje.modelo.Notificacion;
import uy.edu.ort.peaje.modelo.Propietario;
import uy.edu.ort.peaje.modelo.Puesto;
import uy.edu.ort.peaje.modelo.Tarifa;
import uy.edu.ort.peaje.modelo.TipoBonificacion;
import uy.edu.ort.peaje.modelo.TipoNotificacion;
import uy.edu.ort.peaje.modelo.Transito;
import uy.edu.ort.peaje.modelo.Vehiculo;
import uy.edu.ort.peaje.modelo.Propietario.Eventos;
import uy.edu.ort.peaje.servicios.fachada.Fachada;

public class ServicioTransito {

    private ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
    private ArrayList<Puesto> puestos = new ArrayList<Puesto>();
    private ArrayList<CategoriaVehiculo> categorias = new ArrayList<CategoriaVehiculo>();
    private ArrayList<Tarifa> tarifas = new ArrayList<Tarifa>();
    private ArrayList<Transito> listaTransitos = new ArrayList<Transito>();
    private ArrayList<TipoBonificacion> tiposBonificacion = new ArrayList<>();

    public void agregarTipoBonificacion(String nombre) {
        tiposBonificacion.add(new TipoBonificacion(nombre));
    }

    public ArrayList<TipoBonificacion> getTiposBonificacion() {
        return tiposBonificacion;
    }

    public void agregarPuesto(Puesto puesto) {
        puestos.add(puesto);
    }

    public ArrayList<Puesto> getPuestos() {
        return puestos;
    }

    public void agregarTransito(Transito transito) {
        if (transito != null)
            listaTransitos.add(transito);
    }

    public void agregarCategoriaVehiculo(CategoriaVehiculo categoria) {
        categorias.add(categoria);
    }

    public ArrayList<CategoriaVehiculo> getCategoriasVehiculos() {
        return categorias;
    }

    public void agregarTarifa(Tarifa tarifa) {
        if (tarifa == null)
            return;
        tarifas.add(tarifa);

        Puesto p = tarifa.getPuesto();
        if (p != null) {
            p.addTarifa(tarifa); 
        }
    }

    public ArrayList<Tarifa> getTarifas() {
        return tarifas;
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }
    
    public void agregarVehiculoAPropietario(Propietario p, Vehiculo v){
        p.agregarVehiculo(v);
    }

    public void asignarBonificacion(Propietario propietario, String tipoBonificacion, Puesto puesto)
            throws PeajeException {
        if (propietario == null) {
            throw new PeajeException("Propietario inválido");
        }
        if (puesto == null) {
            throw new PeajeException("Puesto inválido");
        }
        if (tipoBonificacion == null || tipoBonificacion.isBlank()) {
            throw new PeajeException("Debe especificar una bonificación");
        }

        if (propietario.tieneBonificacionDeTipoEnPuesto(puesto)) {
            throw new PeajeException("Ya tiene una bonificación para el puesto seleccionado");
        }

        Bonificacion bonificacion = FabricaBonificaciones.crearBonificacion(tipoBonificacion);

        AsignacionBonificacion ab = new AsignacionBonificacion(
                new Date(),
                bonificacion,
                propietario,
                puesto
                );

        propietario.asignarBonificacion(ab);
        propietario.avisar(Eventos.NUEVA_BONIFICACION);

    }

    public double calcularMontoFinal(Transito transito) {
        double montoBase = transito.getTarifa().getMonto();
        double montoFinal = montoBase;

        Propietario propietario = transito.getVehiculo().getPropietario();
        Puesto puesto = transito.getPuesto();

        List<AsignacionBonificacion> asignaciones = propietario.bonificacionesPara(puesto);

        String bonificacionElegida = null;

        for (AsignacionBonificacion ab : asignaciones) {
            Bonificacion b = ab.getBonificacion();
            double candidato = b.calcularMonto(transito); // cada Strategy aplica su regla

            if (candidato < montoFinal) {
                montoFinal = candidato;
                bonificacionElegida = b.getnombre(); // guardo nombre
                if (candidato == 0.0)
                    break; // exonerado: corto
            }
        }

        transito.setBonificacionAplicada(bonificacionElegida);
        return montoFinal;
    }

    public Transito emularTransito(Vehiculo vehiculo, Puesto puesto, Date fechaHora) throws PeajeException {

        if (vehiculo == null)
            throw new PeajeException("Vehículo nulo");
        if (puesto == null)
            throw new PeajeException("Puesto nulo");
        if (fechaHora == null)
            fechaHora = new Date();

        // Tarifa por categoría
        Tarifa tarifa = puesto.getTarifaPorCategoria(vehiculo.getCategoriaVehiculo());
        if (tarifa == null)
            throw new PeajeException("No hay tarifa para la categoría del vehículo en este puesto");

        // Propietario
        Propietario propietario = vehiculo.getPropietario();
        if (propietario == null)
            throw new PeajeException("El vehículo no tiene propietario");

        // Estado
        EstadoPropietario estado = propietario.getEstadoPropietario();
        if (!estado.puedeRealizarTransito())
            throw new PeajeException("El propietario no puede realizar tránsitos");

        // Crear tránsito base
        Transito transito = new Transito(fechaHora, vehiculo, null, puesto, tarifa, 0.0);

        // Calcular monto
        double montoFinal = estado.puedeRecibirBonificaciones()
            ? calcularMontoFinal(transito)
            : tarifa.getMonto();

        if (!estado.puedeRecibirBonificaciones())
        transito.setBonificacionAplicada(null);

        // Validar y descontar saldo
        propietario.validarSaldoSuficiente(montoFinal);
        propietario.descontarSaldo(montoFinal);
        transito.setMontoCobrado(montoFinal);

        // Registrar tránsito
        vehiculo.registrarTransito(transito);
        listaTransitos.add(transito);

        // DISPARAR NOTIFICACIONES desde métodos especializados
        notificarTransito(propietario, puesto);

        if (propietario.getSaldoActual() < 100) {
            notificarSaldoBajo(propietario);
        }
        return transito;
        
    }


    public Puesto buscarPuestoPorNombre(String nombre) {
        if (nombre == null)
            return null;
        for (Puesto p : puestos) {
            if (p != null && p.getNombre() != null && p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    public List<Tarifa> getTarifasPorPuesto(String nombrePuesto) {
        Puesto p = buscarPuestoPorNombre(nombrePuesto);
        if (p == null)
            return new ArrayList<>();

        List<Tarifa> list = new ArrayList<>();
        for (Tarifa t : tarifas) {
            if (t != null && t.getPuesto() != null
                    && t.getPuesto().getNombre().equalsIgnoreCase(nombrePuesto)) {
                list.add(t);
            }
        }
        return list;
    }

    public Puesto encontrarPuestoPorNombre(String nombrePuesto) {
        for (Puesto p : Fachada.getInstancia().getPuestos()) {
            if (p.getNombre() != null && p.getNombre().equalsIgnoreCase(nombrePuesto)) {
                return p;
            }
        }
        return null;
    }

    public ResultadoDto construirResultadoTransito(Transito transito) {
        if (transito == null)
            return null;

        Vehiculo v = transito.getVehiculo();
        Propietario propietario = v.getPropietario();

        // Ajustá estos getters a tus clases reales:
        String nombrePropietario = propietario.getNombreCompleto(); // de Usuario
        String estado = propietario.getEstadoPropietario().getNombre(); // "Habilitado", "Penalizado", etc.
        String categoria = v.getCategoriaVehiculo().getNombreCategoria(); // de CategoriaVehiculo

        String bonificacion = transito.getBonificacionAplicada();
        if (bonificacion == null || bonificacion.isBlank()) {
            bonificacion = "Sin bonificación";
        }

        double costo = transito.getMontoCobrado();
        double saldo = propietario.getSaldoActual();

        return new ResultadoDto(
            nombrePropietario,
            estado,
            categoria,
            bonificacion,
            costo,
            saldo
        );
    }

    public Vehiculo buscarVehiculoPorMatricula(String matricula) {
        if (matricula == null)
            return null;
        for (Vehiculo v : vehiculos) {
            if (v != null && v.getMatricula() != null &&
                    v.getMatricula().equalsIgnoreCase(matricula)) {
                return v;
            }
        }
        return null;
    }
    public List<Transito> obtenerTransitosDePropietario(Propietario propietario) {
        List<Transito> resultado = new ArrayList<>();
        if (propietario == null) return resultado;

        String cedulaProp = propietario.getCedula();

        for (Transito t : listaTransitos) {
            if (t == null || t.getVehiculo() == null || t.getVehiculo().getPropietario() == null) continue;

            Propietario dueño = t.getVehiculo().getPropietario();
            if (dueño.getCedula() != null && dueño.getCedula().equals(cedulaProp)) {
                resultado.add(t);
            }
        }

        resultado.sort((t1, t2) -> t2.getFechaHora().compareTo(t1.getFechaHora()));
        return resultado;
    }

    public List<VehiculoTableroDto> construirVehiculosTablero(Propietario propietario) {
        List<VehiculoTableroDto> vehiculosDto = new ArrayList<>();
        if (propietario == null) return vehiculosDto;

        List<Transito> transitosProp = obtenerTransitosDePropietario(propietario);

        for (Vehiculo v : propietario.getVehiculos()) {
            int cantidad = 0;
            double montoTotal = 0.0;

            for (Transito t : transitosProp) {
                if (t.getVehiculo() != null
                        && t.getVehiculo().getMatricula().equalsIgnoreCase(v.getMatricula())) {
                    cantidad++;
                    montoTotal += t.getMontoCobrado();
                }
            }

            vehiculosDto.add(new VehiculoTableroDto(v, cantidad, montoTotal));
        }

        return vehiculosDto;
    }

    public List<TransitoTableroDto> construirTransitosTablero(Propietario propietario) {
        List<TransitoTableroDto> dto = new ArrayList<>();
        if (propietario == null) return dto;

        List<Transito> transitosProp = obtenerTransitosDePropietario(propietario);
        for (Transito t : transitosProp) {
            dto.add(new TransitoTableroDto(t));
        }
        return dto;
    }

    public int cantidadTransitosVehiculo(Vehiculo v, Propietario p) {
        List<Transito> transitosProp = obtenerTransitosDePropietario(p);
        int cantidad = 0;

        for (Transito t : transitosProp) {
            if (t.getVehiculo() != null &&
                t.getVehiculo().getMatricula().equalsIgnoreCase(v.getMatricula())) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public double montoTotalVehiculo(Vehiculo v, Propietario p) {
        List<Transito> transitosProp = obtenerTransitosDePropietario(p);
        double total = 0;

        for (Transito t : transitosProp) {
            if (t.getVehiculo() != null &&
                t.getVehiculo().getMatricula().equalsIgnoreCase(v.getMatricula())) {
                total += t.getMontoCobrado();
            }
        }
        return total;
    }

    //NOTIFICACIONES

    public void notificarTransito(Propietario p, Puesto puesto) {
        Notificacion n = new Notificacion(
            TipoNotificacion.TRANSITO,
            "Tránsito registrado en " + puesto.getNombre(),
            p
        );
        p.registrarNotificacion(n);
        
        p.avisar(Propietario.Eventos.SALDO_BAJO);
    }

    public void notificarSaldoBajo(Propietario p) {

        Notificacion n = new Notificacion(
            TipoNotificacion.SALDO_BAJO,
            "Saldo bajo: $" + p.getSaldoActual(),
            p
        );
        p.registrarNotificacion(n);


        p.avisar(Propietario.Eventos.SALDO_BAJO);

    }


}
