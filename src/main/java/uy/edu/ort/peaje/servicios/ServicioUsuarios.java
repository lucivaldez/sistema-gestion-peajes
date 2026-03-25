package uy.edu.ort.peaje.servicios;

import java.util.ArrayList;
import java.util.List;


import uy.edu.ort.peaje.dtos.BonificacionAsignadaDto;
import uy.edu.ort.peaje.dtos.NotificacionDto;
import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.Administrador;
import uy.edu.ort.peaje.modelo.AsignacionBonificacion;
import uy.edu.ort.peaje.modelo.EstadoPropietario;
import uy.edu.ort.peaje.modelo.EstadoPropietarioDeshabilitado;
import uy.edu.ort.peaje.modelo.Notificacion;
import uy.edu.ort.peaje.modelo.Propietario;
import uy.edu.ort.peaje.modelo.Sesion;
import uy.edu.ort.peaje.modelo.TipoNotificacion;
import uy.edu.ort.peaje.modelo.Usuario;


public class ServicioUsuarios {
    private ArrayList<Propietario> propietarios;
    private ArrayList<Administrador> administradores;
    private ArrayList<Sesion> sesiones;
    private ArrayList<EstadoPropietario> estadosPropietarios;

    public ServicioUsuarios() {
        this.propietarios = new ArrayList<>();
        this.administradores = new ArrayList<>();
        this.sesiones = new ArrayList<>();
        this.estadosPropietarios = new ArrayList<>();

    }

    public void agregarEstadoPropietario(EstadoPropietario ep) {
        estadosPropietarios.add(ep);
    }

    public ArrayList<EstadoPropietario> getEstadosPropietario() {
        return estadosPropietarios;
    }

    public void agregar(Propietario propietario) {
        propietarios.add(propietario);

    }

    public void agregar(Administrador administrador) {
        administradores.add(administrador);
    }

    private Usuario login(String cedula, String password, List lista) throws PeajeException {
        Usuario usuario;
        for (Object o : lista) {
            usuario = (Usuario) o;
            if (usuario.getCedula().equals(cedula) && usuario.esContrasenaValida(password)) {
                return usuario;
            }
        }
        return null;
    }

    public Sesion loginAdmin(String cedula, String password) throws PeajeException{
        Sesion sesion = null;
        Administrador usuario = (Administrador) login(cedula, password, administradores);
        if(usuario!=null){
            sesion = new Sesion(usuario);
            sesiones.add(sesion);
            return sesion;
        }        
        throw new PeajeException("Usuario y/o contraseña incorrectos");
    }

    public Sesion loginPropietario(String cedula, String password) throws PeajeException {
        Sesion sesion = null;
        Propietario usuario = (Propietario) login(cedula, password, propietarios);
        if (usuario != null) {
            if (usuario.getEstadoPropietario() instanceof EstadoPropietarioDeshabilitado) {
                throw new PeajeException("Su usuario está deshabilitado por el administrador.");
            }

            sesion = new Sesion(usuario);
            sesiones.add(sesion);
            return sesion;
        }
        throw new PeajeException("Usuario y/o contraseña incorrectos");
    }

    public List<Sesion> getSesiones() {
        return sesiones;
    }
    public void logout(Sesion s){
        sesiones.remove(s);       
    }

    public Propietario buscarPropietarioPorCedula(String cedula) {
        if (cedula == null)
            return null;
        for (Propietario p : propietarios) {
            if (cedula.equals(p.getCedula())) {
                return p;
            }
        }
        return null;
    }

    public EstadoPropietario obtenerEstadoPorNombre(String nombre) {
        for (EstadoPropietario e : estadosPropietarios) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                return e;
            }
        }
        return null;
    }
    public void borrarNotificaciones(Propietario propietario) {
        if (propietario == null) return;
        if (propietario.getNotificaciones() == null) return;
        propietario.getNotificaciones().clear();
    }

    public List<BonificacionAsignadaDto> construirBonificacionesAsignadas(Propietario propietario) {
        List<BonificacionAsignadaDto> res = new ArrayList<>();
        if (propietario == null) return res;

        for (AsignacionBonificacion ab : propietario.getAsignacionBonificacion()) {
            res.add(new BonificacionAsignadaDto(ab));
        }
        return res;
    }

    public List<NotificacionDto> construirNotificacionesDto(Propietario propietario) {
        List<NotificacionDto> res = new ArrayList<>();
        if (propietario == null || propietario.getNotificaciones() == null) return res;

        for (Notificacion n : propietario.getNotificaciones()) {
            res.add(new NotificacionDto(n));
        }
        return res;
    }
    
    public void notificarCambioEstado(Propietario p) {
        Notificacion n = new Notificacion(
            TipoNotificacion.CAMBIO_ESTADO,
            "Tu estado cambió a: " + p.getEstadoPropietario().getNombre(),
            p
        );
        p.registrarNotificacion(n);


        p.avisar(Propietario.Eventos.CAMBIO_ESTADO);
    }
}
