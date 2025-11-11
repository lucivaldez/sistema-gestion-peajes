package uy.edu.ort.peaje.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import uy.edu.ort.peaje.dtos.BonificacionAsignadaDto;
import uy.edu.ort.peaje.dtos.PropietarioDto;
import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.Administrador;
import uy.edu.ort.peaje.modelo.AsignacionBonificacion;
import uy.edu.ort.peaje.modelo.Bonificacion;
import uy.edu.ort.peaje.modelo.Exonerado;
import uy.edu.ort.peaje.modelo.Frecuente;
import uy.edu.ort.peaje.modelo.Propietario;
import uy.edu.ort.peaje.modelo.Puesto;
import uy.edu.ort.peaje.modelo.Trabajador;
import uy.edu.ort.peaje.servicios.fachada.Fachada;
import uy.edu.ort.peaje.utils.Respuesta;

@RestController
@RequestMapping("/bonificaciones")
@Scope("session")
public class ControladorAsignarBonificaciones {

    @GetMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin){
        if (admin == null) {
            return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "index.html"));
        }
        return Respuesta.lista(
            listarBonificacionesDefinidas(),   
            listarPuestos()                    
        );
    }

    @GetMapping("/buscarPropietario")
    public List<Respuesta> buscarPropietario(
            @SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin,
            @RequestParam String cedula) {
        if (admin == null) {
            return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "index.html"));
        }
        try {
            Propietario p = Fachada.getInstancia().buscarPropietarioPorCedula(cedula);
            if (p == null) {
                return Respuesta.lista(new Respuesta("error", "no existe el propietario"));
            }
            return Respuesta.lista(
                propietarioDto(p),
                bonificacionesAsignadasDto(p)
            );
        } catch (Exception e) {
            return Respuesta.lista(new Respuesta("error", "Error al buscar propietario"));
        }
    }

    @PostMapping("/asignar")
    public List<Respuesta> asignarBonificacion(
            @SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin,
            @RequestParam String cedula,
            @RequestParam String tipoBonificacion,
            @RequestParam String nombrePuesto) {

        if (admin == null) {
            return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "index.html"));
        }
        if (tipoBonificacion == null || tipoBonificacion.isBlank()) {
            return Respuesta.lista(new Respuesta("error", "Debe especificar una bonificación"));
        }
        if (nombrePuesto == null || nombrePuesto.isBlank()) {
            return Respuesta.lista(new Respuesta("error", "Debe especificar un puesto"));
        }

        try {
            Propietario p = Fachada.getInstancia().buscarPropietarioPorCedula(cedula);
            if (p == null) return Respuesta.lista(new Respuesta("error", "no existe el propietario"));

            Puesto puesto = encontrarPuestoPorNombre(nombrePuesto);
            if (puesto == null) return Respuesta.lista(new Respuesta("error", "El puesto no existe"));

            // Evitar duplicado por tipo y puesto
            Bonificacion nueva = crearBonificacion(tipoBonificacion);
            for (AsignacionBonificacion ab : p.bonificacionesPara(puesto)) {
                if (ab.getBonificacion().getClass().equals(nueva.getClass())) {
                    return Respuesta.lista(new Respuesta("error",
                        "Ya tiene una bonificación de ese tipo para el puesto seleccionado"));
                }
            }

            Fachada.getInstancia().asignarBonificacion(p, nueva, puesto);

            return Respuesta.lista(
                new Respuesta("mensaje", "Bonificación asignada correctamente"),
                new Respuesta("limpiarFormulario", ""),
                propietarioDto(p),
                bonificacionesAsignadasDto(p)
            );

        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("error", e.getMessage()));
        } catch (IllegalArgumentException iae) {
            return Respuesta.lista(new Respuesta("error", "Tipo de bonificación inválido"));
        } catch (Exception e) {
            return Respuesta.lista(new Respuesta("error", "Error inesperado al asignar"));
        }
    }

    private Respuesta propietarioDto(Propietario p) {
        return new Respuesta("propietario", new PropietarioDto(p));
    }

    private Respuesta bonificacionesAsignadasDto(Propietario p) {
        List<BonificacionAsignadaDto> lista = new ArrayList<>();
        if (p.getAsignacionBonificacion() != null) {
            for (AsignacionBonificacion ab : p.getAsignacionBonificacion()) {
                lista.add(new BonificacionAsignadaDto(ab));
            }
        }
        return new Respuesta("bonificacionesAsignadas", lista);
    }

    private Respuesta listarBonificacionesDefinidas() {
        // Como no hay catálogo, devolvemos los nombres fijos 
        List<String> tipos = new ArrayList<>();
        tipos.add("Exonerado");
        tipos.add("Frecuente");
        tipos.add("Trabajador");
        return new Respuesta("bonificacionesDefinidas", tipos);
    }

    private Respuesta listarPuestos() {
        List<Puesto> puestos = new ArrayList<>(Fachada.getInstancia().getPuestos());
        
        List<String> nombres = new ArrayList<>();
        for (Puesto p : puestos) {
            nombres.add(p.getNombre());
        }
        return new Respuesta("puestos", nombres);
    }

    private Puesto encontrarPuestoPorNombre(String nombrePuesto) {
        for (Puesto p : Fachada.getInstancia().getPuestos()) {
            if (p.getNombre() != null && p.getNombre().equalsIgnoreCase(nombrePuesto)) {
                return p;
            }
        }
        return null;
    }

    private Bonificacion crearBonificacion(String tipo) {
        if ("Exonerado".equalsIgnoreCase(tipo))  return new Exonerado("Exonerado");
        if ("Frecuente".equalsIgnoreCase(tipo))  return new Frecuente("Frecuente");
        if ("Trabajador".equalsIgnoreCase(tipo)) return new Trabajador("Trabajador");
        throw new IllegalArgumentException("Tipo de bonificación inválido: " + tipo);
        }
    }

