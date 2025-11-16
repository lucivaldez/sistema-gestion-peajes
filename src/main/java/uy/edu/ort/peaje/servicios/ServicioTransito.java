package uy.edu.ort.peaje.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uy.edu.ort.peaje.dtos.ResultadoDto;
import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.AsignacionBonificacion;
import uy.edu.ort.peaje.modelo.Bonificacion;
import uy.edu.ort.peaje.modelo.CategoriaVehiculo;
import uy.edu.ort.peaje.modelo.EstadoPropietario;
import uy.edu.ort.peaje.modelo.EstadoPropietarioDeshabilitado;
import uy.edu.ort.peaje.modelo.EstadoPropietarioSuspendido;
import uy.edu.ort.peaje.modelo.FabricaBonificaciones;
import uy.edu.ort.peaje.modelo.Propietario;
import uy.edu.ort.peaje.modelo.Puesto;
import uy.edu.ort.peaje.modelo.Tarifa;
import uy.edu.ort.peaje.modelo.TipoBonificacion;
import uy.edu.ort.peaje.modelo.Transito;
import uy.edu.ort.peaje.modelo.Vehiculo;
import uy.edu.ort.peaje.servicios.fachada.Fachada;

public class ServicioTransito {

    // experto en gestionar los transitos
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

        if (propietario.tieneBonificacionDeTipoEnPuesto(propietario, tipoBonificacion, puesto)) {
            throw new PeajeException("Ya tiene una bonificación de ese tipo para el puesto seleccionado");
        }

        Bonificacion bonificacion = FabricaBonificaciones.crearBonificacion(tipoBonificacion);

        AsignacionBonificacion ab = new AsignacionBonificacion(
                new Date(),
                bonificacion,
                propietario,
                puesto);

        propietario.asignarBonificacion(ab);
    }

    public double calcularMontoFinal(Transito transito) {
        double montoBase = transito.getTarifa().getMonto();
        double montoFinal = montoBase;

        Propietario propietario = transito.getVehiculo().getPropietario();
        Puesto puesto = transito.getPuesto();

        // Trae solo las bonificaciones asignadas a ese puesto
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
        if (tarifa == null) {
            throw new PeajeException("No hay tarifa para la categoría del vehículo en el puesto");
        }

        // Propietario
        Propietario propietario = vehiculo.getPropietario();
        if (propietario == null) {
            throw new PeajeException("El vehículo no tiene propietario");
        }

        // Estado del propietario
        EstadoPropietario estado = propietario.getEstadoPropietario();

        // Si NO puede realizar tránsito...
        if (!estado.puedeRealizarTransito()) {
            if (estado instanceof EstadoPropietarioDeshabilitado) {
                throw new PeajeException("El propietario del vehículo está deshabilitado, no puede realizar tránsitos");
            } else if (estado instanceof EstadoPropietarioSuspendido) {
                throw new PeajeException("El propietario del vehículo está suspendido, no puede realizar tránsitos");
            } else {
                throw new PeajeException("El propietario no puede realizar tránsitos");
            }
        }

        // Armo el tránsito base
        Transito transito = new Transito(fechaHora, vehiculo, null, puesto, tarifa, 0.0);

        // Calcula monto final según si puede recibir bonificaciones
        double montoFinal;
        if (estado.puedeRecibirBonificaciones()) {
            montoFinal = calcularMontoFinal(transito);
        } else {
            // No puede recibir bonificaciones
            montoFinal = tarifa.getMonto();
            transito.setBonificacionAplicada(null);
        }

        // Verificar saldo suficiente
        propietario.validarSaldoSuficiente(montoFinal);

        // Descontar saldo
        propietario.descontarSaldo(montoFinal);

        // Notificaciones
        // Notificacion notificacionTransito = null;

        // if (estado.puedeSerNotificado()) {
        // Date ahora = new Date();

        // // Notificación de tránsito
        // String mensajeTransito = "[" + ahora + "] " +
        // "Pasaste por el puesto " + puesto.getNombre() +
        // " con el vehículo " + vehiculo.getMatricula();

        // notificacionTransito = new Notificacion(ahora, mensajeTransito);
        // propietario.getNotificaciones().add(notificacionTransito);
        // // Si tenés un método tipo propietario.registrar(notificacionTransito), usalo
        // en vez del getNotificaciones().add(...)

        // // Notificación de saldo bajo
        // int saldoLuego = propietario.getSaldoActual();
        // int saldoMinimoAlerta = propietario.getSaldoMinimo();

        // if (saldoLuego < saldoMinimoAlerta) {
        // String mensajeSaldo = "[" + ahora + "] " +
        // "Tu saldo actual es de $ " + saldoLuego +
        // " Te recomendamos hacer una recarga";

        // Notificacion notificacionSaldo = new Notificacion(ahora, mensajeSaldo);
        // propietario.getNotificaciones().add(notificacionSaldo);
        // }
        // }

        transito.setMontoCobrado(montoFinal);
        // transito.setNotificacion(notificacionTransito);

        vehiculo.registrarTransito(transito);
        listaTransitos.add(transito);

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
                saldo);
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
}
