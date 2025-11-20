package uy.edu.ort.peaje.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import uy.edu.ort.peaje.modelo.Propietario;
import uy.edu.ort.peaje.utils.ConexionNavegador;
import uy.edu.ort.peaje.utils.Respuesta;

@RestController
@RequestMapping("/menuProp")
@Scope("session")     
public class ControladorMenuProp {

    private final ConexionNavegador conexionNavegador;  

    public ControladorMenuProp(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }

    @GetMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "propietario", required=false) Propietario usuario){
        if (usuario == null) {
             return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "loginPropietario.html"));
         }
         return Respuesta.lista(new Respuesta("nombreCompleto", usuario.getNombreCompleto()));    
    }

    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE(); 
       
    }
}
