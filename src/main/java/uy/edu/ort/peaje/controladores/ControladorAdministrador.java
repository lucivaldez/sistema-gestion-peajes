package uy.edu.ort.peaje.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import uy.edu.ort.peaje.dtos.SesionDto;
import uy.edu.ort.peaje.modelo.Administrador;
import uy.edu.ort.peaje.servicios.fachada.Fachada;
import uy.edu.ort.peaje.utils.Respuesta;

@RestController
@RequestMapping("/administrador")

public class ControladorAdministrador {

    @GetMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "usuarioAdmin") Administrador admin){

         if (admin == null) {
             // Manejar el caso en que el usuario no está en la sesión pide redireccionar a la página de login
             return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "index.html"));
         }

        return Respuesta.lista(
            new Respuesta("sesiones", SesionDto.listaSesionesDto(Fachada.getInstancia().getSesiones()))
        );
    }
}
