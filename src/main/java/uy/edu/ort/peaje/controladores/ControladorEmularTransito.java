package uy.edu.ort.peaje.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import uy.edu.ort.peaje.dtos.CategoriaVehiculoDto;
import uy.edu.ort.peaje.dtos.PuestoDto;
import uy.edu.ort.peaje.dtos.TarifaDto;
import uy.edu.ort.peaje.modelo.CategoriaVehiculo;
import uy.edu.ort.peaje.modelo.Puesto;
import uy.edu.ort.peaje.modelo.Sesion;
import uy.edu.ort.peaje.modelo.Tarifa;
import uy.edu.ort.peaje.servicios.fachada.Fachada;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/emularTransito")
@Scope("session")

public class ControladorEmularTransito {
    private List<Puesto> puestos;
    private List<Tarifa> tarifas;
    private List<CategoriaVehiculo> categorias;


    @GetMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "sesion", required = false) Sesion sesion){
        if (sesion == null) {
             // Manejar el caso en que el usuario no está en la sesión pide redireccionar a la página de login
             return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "login.html"));
        }

        return Respuesta.lista(listarPuestos(),listarTarifas(), listarCategoriasVehiculos());
        
    }




    private Respuesta listarPuestos(){

        puestos = new ArrayList<Puesto>(Fachada.getInstancia().getPuestos());

        List<PuestoDto> puestoDto = new ArrayList<PuestoDto>();

        for(Puesto p: puestos){
            puestoDto.add(new PuestoDto(p));
        }
        return new Respuesta("puesto", puestoDto);
    }

    private Respuesta listarTarifas(){

        tarifas = new ArrayList<Tarifa>(Fachada.getInstancia().getTarifas());

        List<TarifaDto> tarifaDto = new ArrayList<TarifaDto>();

        for(Tarifa t: tarifas){
            tarifaDto.add(new TarifaDto(t));
        }
        return new Respuesta("tarifa", tarifaDto);
    }
    
    private Respuesta listarCategoriasVehiculos(){

        categorias = new ArrayList<CategoriaVehiculo>(Fachada.getInstancia().getCategoriaVehiculos());

        List<CategoriaVehiculoDto> categoriaDto = new ArrayList<CategoriaVehiculoDto>();

        for(CategoriaVehiculo c: categorias){
            categoriaDto.add(new CategoriaVehiculoDto(c));
        }
        return new Respuesta("tarifa", categoriaDto);
    }


    private Respuesta mensaje(String msg){
        return new Respuesta("mensaje", msg);
    }   
    


}
