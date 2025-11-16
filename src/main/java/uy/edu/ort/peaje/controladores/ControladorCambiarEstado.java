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

import uy.edu.ort.peaje.dtos.PropietarioDto;
import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.Administrador;
import uy.edu.ort.peaje.modelo.EstadoPropietario;
import uy.edu.ort.peaje.modelo.Notificacion;
import uy.edu.ort.peaje.modelo.Propietario;
import uy.edu.ort.peaje.observador.Observador;
import uy.edu.ort.peaje.servicios.fachada.Fachada;
import uy.edu.ort.peaje.utils.Respuesta;
import uy.edu.ort.peaje.observador.Observable;
import uy.edu.ort.peaje.observador.Observador;

@RestController
@RequestMapping("/cambiarEstado")
@Scope("session")
public class ControladorCambiarEstado implements Observador{

    private Propietario propietario;
    private List<EstadoPropietario> estadosAsignar;

    @GetMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin){
        if (admin == null) {
            return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "index.html"));
        }
        return Respuesta.lista(estadosAsignar());


    }
    
    private Respuesta estadosAsignar(){
        estadosAsignar = new ArrayList<EstadoPropietario>(Fachada.getInstancia().getEstadoPropietario());
             
        return new Respuesta("estadosAsignar", estadosAsignar);

    }
    

    @PostMapping("/buscar")
    public List<Respuesta> buscarPropietario(@RequestParam String cedula) throws PeajeException {
        // Validación básica
        if (cedula == null || cedula.isBlank()) {
            throw new PeajeException("Debe ingresar una cédula");
        }
        Propietario p = Fachada.getInstancia().buscarPropietarioPorCedula(cedula);

        if (p == null) {
            throw new PeajeException("No existe el propietario");
        }
        this.propietario = p;

        return Respuesta.lista(
        new Respuesta("propietario", new PropietarioDto(p)),
        new Respuesta("estadoActual", p.getEstadoPropietario().getNombre()));
    }


    @PostMapping("/cambiar")
    public List<Respuesta> cambiarEstadoPropietario(@RequestParam String nuevoEstado) throws PeajeException {
        if (this.propietario == null) {
            throw new PeajeException("Debe buscar un propietario antes");
        }
        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            throw new PeajeException("Debe seleccionar un estado");
        }
        EstadoPropietario estadoActual = this.propietario.getEstadoPropietario();
        if (estadoActual.getNombre().equals(nuevoEstado)) {
            throw new PeajeException("El propietario ya está en estado " + nuevoEstado);
        }
        EstadoPropietario estadoNuevo = Fachada.getInstancia().obtenerEstadoPorNombre(nuevoEstado);

        if (estadoNuevo == null) {
            throw new PeajeException("El estado seleccionado no es válido");
            
        }

        // Cambiar estado
        this.propietario.setEstadoPropietario(estadoNuevo);

        //disparar evento
        Fachada.getInstancia().avisar(Fachada.Eventos.cambioEstadoPropietario);

        // Respuesta a la vista
        return Respuesta.lista(
            new Respuesta("estadoActual", estadoNuevo.getNombre()),
            new Respuesta("mensaje", "Estado cambiado correctamente")
        );
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if (evento == Fachada.Eventos.cambioEstadoPropietario) {
            manejarCambioEstado();
        }  
    }



    

    
}



    



    





