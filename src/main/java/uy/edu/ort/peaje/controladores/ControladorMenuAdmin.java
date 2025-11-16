package uy.edu.ort.peaje.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import uy.edu.ort.peaje.modelo.Administrador;
import uy.edu.ort.peaje.utils.Respuesta;

@RestController
@RequestMapping("/menuAdmin") 
public class ControladorMenuAdmin {
@GetMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "usuarioAdmin", required=false) Administrador usuario){
        if (usuario == null) {
             // Manejar el caso en que el usuario no está en la sesión pide redireccionar a la página de login
             return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "loginAdmin.html"));
         }
         return Respuesta.lista(new Respuesta("nombreCompleto", usuario.getNombreCompleto()));   
    }
}
