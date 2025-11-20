package uy.edu.ort.peaje.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.Administrador;
import uy.edu.ort.peaje.modelo.Propietario;
import uy.edu.ort.peaje.modelo.Sesion;
import uy.edu.ort.peaje.servicios.fachada.Fachada;
import uy.edu.ort.peaje.utils.Respuesta;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/ingreso")
public class ControladorIngreso {


    @PostMapping("/loginPropietario")
    public List<Respuesta> loginPropietario(HttpSession sessionHttp, @RequestParam String cedula , @RequestParam String password) throws PeajeException {
        Sesion sesion  = Fachada.getInstancia().loginPropietario(cedula, password);
        sessionHttp.setAttribute("propietario", sesion.getUsuario());
        
        return Respuesta.lista(new Respuesta ("loginExitoso", "menuProp.html"));
    }

    @PostMapping("/logoutPropietario")
    public List<Respuesta> logout(HttpSession sesionHttp) {
        Propietario usuario = (Propietario) sesionHttp.getAttribute("propietario");
        if (usuario != null) {
            sesionHttp.removeAttribute("propietario");
            
        }


        return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "index.html"));
    }

    @PostMapping("/loginAdmin")
    public List<Respuesta> loginAdmin(HttpSession sesionHttp, @RequestParam String cedula, @RequestParam String password) throws PeajeException {

        Sesion sesion   = Fachada.getInstancia().loginAdmin(cedula, password);
        sesionHttp.setAttribute("usuarioAdmin", sesion.getUsuario());
        return Respuesta.lista(new Respuesta("loginExitoso", "menuAdmin.html"));
    }

    @PostMapping("/logoutAdmin")
    public List<Respuesta> logoutAdmin(HttpSession sesionHttp) {
        Administrador usuario = (Administrador) sesionHttp.getAttribute("usuarioAdmin");
        if (usuario != null) {
            sesionHttp.removeAttribute("usuarioAdmin");
            sesionHttp.invalidate();
        }
        return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "index.html"));
    }
}
