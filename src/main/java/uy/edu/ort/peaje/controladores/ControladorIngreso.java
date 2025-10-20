package uy.edu.ort.peaje.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.Usuario;
import uy.edu.ort.peaje.servicios.Fachada;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/ingreso")
public class ControladorIngreso {
    @PostMapping("/login")
    public List<Respuesta> login(HttpSession sessionHttp, @RequestParam String userName , @RequestParam String password) throws PeajeException {
        Usuario unUsuario = Fachada.getInstancia().login(userName, password);
        sessionHttp.setAttribute("usuarioLogueado", unUsuario);
        
        return Respuesta.lista(new Respuesta ("loginExitoso", "tablero.html"));
    }
    
}
