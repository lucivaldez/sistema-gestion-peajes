package uy.edu.ort.peaje.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import uy.edu.ort.peaje.dtos.SesionDto;
import uy.edu.ort.peaje.modelo.Administrador;
import uy.edu.ort.peaje.observador.Observable;
import uy.edu.ort.peaje.observador.Observador;
import uy.edu.ort.peaje.servicios.fachada.Fachada;
import uy.edu.ort.peaje.utils.ConexionNavegador;
import uy.edu.ort.peaje.utils.Respuesta;

@RestController
@RequestMapping("/administrador")
@Scope("session")
public class ControladorAdministrador implements Observador{
    
    private final ConexionNavegador conexionNavegador;

    public ControladorAdministrador(@Autowired ConexionNavegador unaConexion) {
        this.conexionNavegador = unaConexion;   
    }

    @GetMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "usuarioAdmin") Administrador admin){

         if (admin == null) {
             // Manejar el caso en que el usuario no está en la sesión pide redireccionar a la página de login
             return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "index.html"));
         }
         Fachada.getInstancia().agregarObservador(this);


        return Respuesta.lista(
            new Respuesta("sesiones", SesionDto.listaSesionesDto(Fachada.getInstancia().getSesiones()))
        );
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if (evento.equals(Fachada.Eventos.nuevoUsuarioConectado) || evento.equals(Fachada.Eventos.usuarioDesconectado))
            System.out.println("Le voy a avisar al HTML que se actualice");
            conexionNavegador.enviarJSON(Respuesta.lista(sesiones()));
    }

    private Respuesta sesiones(){
        return new Respuesta ("sesiones", SesionDto.listaSesionesDto(Fachada.getInstancia().getSesiones()));
    }
}
