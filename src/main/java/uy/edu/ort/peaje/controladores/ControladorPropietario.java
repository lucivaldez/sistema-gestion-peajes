package uy.edu.ort.peaje.controladores;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;


import uy.edu.ort.peaje.dtos.BonificacionAsignadaDto;
import uy.edu.ort.peaje.dtos.NotificacionDto;
import uy.edu.ort.peaje.dtos.PropietarioDto;
import uy.edu.ort.peaje.dtos.TransitoTableroDto;
import uy.edu.ort.peaje.dtos.VehiculoTableroDto;
import uy.edu.ort.peaje.modelo.AsignacionBonificacion;
import uy.edu.ort.peaje.modelo.Notificacion;
import uy.edu.ort.peaje.modelo.Transito;
import uy.edu.ort.peaje.modelo.Vehiculo;
import uy.edu.ort.peaje.modelo.Propietario.Eventos;
import uy.edu.ort.peaje.utils.Respuesta;
import uy.edu.ort.peaje.modelo.Propietario;
import uy.edu.ort.peaje.observador.Observable;
import uy.edu.ort.peaje.observador.Observador;
import uy.edu.ort.peaje.servicios.fachada.Fachada;
import uy.edu.ort.peaje.utils.ConexionNavegador;

@RestController
@RequestMapping("/propietario")
@Scope("session")

public class ControladorPropietario implements Observador {

    private Propietario propietarioActual;
    private final ConexionNavegador conexionNavegador;


    public ControladorPropietario(@Autowired ConexionNavegador unaConexion) {
        this.conexionNavegador = unaConexion;
    }

    @GetMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "propietario") Propietario propietario){

         if (propietario == null) {
             // Manejar el caso en que el usuario no está en la sesión pide redireccionar a la página de login
             return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "login.html"));
         }

         this.propietarioActual = propietario;
         
         //registro al observador
         propietario.agregarObservador(this);

         PropietarioDto propietarioDto = new PropietarioDto(propietario);
        
        return Respuesta.lista(
            new Respuesta("propietario", propietarioDto),
                listarBonificaciones(propietario),
                listarVehiculos(propietario),
                listarTransitos(propietario),
                listarNotificaciones(propietario)
        );
        
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        Propietario p = propietarioActual;

        if (evento instanceof Eventos) {
            Eventos e = (Eventos) evento;

        // Evento: CAMBIO DE ESTADO
        if (e == Eventos.CAMBIO_ESTADO) {
            conexionNavegador.enviarJSON(
                Respuesta.lista(
                    new Respuesta("evento", "CAMBIO_ESTADO"),
                    new Respuesta("estadoNuevo", p.getEstadoPropietario().getNombre())
                )
            );
        }

        // Evento: SALDO BAJO
        if (e == Eventos.SALDO_BAJO) {
            conexionNavegador.enviarJSON(
                Respuesta.lista(
                    new Respuesta("evento", "SALDO_BAJO"),
                    new Respuesta("saldoActual", p.getSaldoActual())
                )
            );
        }

        // Evento: NUEVO TRANSITO
        if (e == Eventos.NUEVO_TRANSITO) {
            conexionNavegador.enviarJSON(
                Respuesta.lista(
                    new Respuesta("evento", "NUEVO_TRANSITO")
                )
            );
        }

        // Evento: NUEVA BONIFICACION
        if (e == Eventos.NUEVA_BONIFICACION) {
            conexionNavegador.enviarJSON(
                Respuesta.lista(
                    new Respuesta("evento", "NUEVA_BONIFICACION")
                )
            );
        }
    }
    // 2) Vista completa (lo que ya mandabas antes)
        conexionNavegador.enviarJSON(
            Respuesta.lista(
                new Respuesta("propietario", new PropietarioDto(p)),
                listarBonificaciones(p),
                listarVehiculos(p),
                listarTransitos(p),
                listarNotificaciones(p)
            )
        );
    }

    @PostMapping("/borrarNotificaciones")
    public List<Respuesta> borrarNotificaciones(
        @SessionAttribute(name = "propietario", required = false) Propietario propietario) {
    
            if (propietario == null) {
                return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "loginPropietario.html"));
            }
    
            if (propietario.getNotificaciones() == null || propietario.getNotificaciones().isEmpty()) {
                return Respuesta.lista(
                        new Respuesta("mensaje", "No hay notificaciones para borrar"));
            }
    
            Fachada.getInstancia().borrarNotificaciones(propietario);
    
            Respuesta notificacionesResp = listarNotificaciones(propietario);
    
            return Respuesta.lista(
                    notificacionesResp,
                    new Respuesta("mensaje", "Notificaciones borradas correctamente"));
        }
    
    
    private Respuesta listarBonificaciones(Propietario propietario) {
    
        List<BonificacionAsignadaDto> bonifDtos = new ArrayList<>();
    
        for (AsignacionBonificacion ab : propietario.getAsignacionBonificacion()) {
            bonifDtos.add(new BonificacionAsignadaDto(ab));
        }
    
        return new Respuesta("bonificaciones", bonifDtos);
        }
    
    private Respuesta listarVehiculos(Propietario propietario) {

        List<VehiculoTableroDto> vehiculosDto = new ArrayList<>();

        for (Vehiculo v : propietario.getVehiculos()) {
            int cantidad = Fachada.getInstancia().cantidadTransitosVehiculo(v, propietario);
            double montoTotal = Fachada.getInstancia().montoTotalVehiculo(v, propietario);

            vehiculosDto.add(new VehiculoTableroDto(v, cantidad, montoTotal));
        }

        return new Respuesta("vehiculos", vehiculosDto);
    }


    
    private Respuesta listarTransitos(Propietario propietario) {
    
        List<TransitoTableroDto> transitosDto = new ArrayList<>();

        List<Transito> transitosProp = Fachada.getInstancia().obtenerTransitosDePropietario(propietario);
    
        for (Transito t : transitosProp) {
            transitosDto.add(new TransitoTableroDto(t));
        }
        return new Respuesta("transitos", transitosDto);
    }
    
    private Respuesta listarNotificaciones(Propietario propietario) {
    
        List<NotificacionDto> notificacionesDto = new ArrayList<>();
    
        if (propietario.getNotificaciones() != null) {
            for (Notificacion n : propietario.getNotificaciones()) {
                notificacionesDto.add(new NotificacionDto(n));
                }
            }
    
        return new Respuesta("notificaciones", notificacionesDto);
    }
    
}


