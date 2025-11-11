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
import uy.edu.ort.peaje.modelo.Administrador;
import uy.edu.ort.peaje.modelo.CategoriaVehiculo;
import uy.edu.ort.peaje.modelo.Puesto;
import uy.edu.ort.peaje.modelo.Tarifa;
import uy.edu.ort.peaje.modelo.Transito;
import uy.edu.ort.peaje.servicios.fachada.Fachada;
import uy.edu.ort.peaje.utils.Respuesta;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/transito")
@Scope("session")
public class ControladorEmularTransito {
    private List<Puesto> puestos;
    private List<Tarifa> tarifas;
    private List<CategoriaVehiculo> categorias;
    private Transito transito;


    @GetMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin){
        if (admin == null) {
             // Manejar el caso en que el usuario no está en la sesión pide redireccionar a la página de login
             return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "index.html"));
        }
        return Respuesta.lista(listarPuestos());    
    }

    @GetMapping("/tarifasPuesto")
    public List<Respuesta> tarifasPorPuesto(
            @SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin,
            @RequestParam String puesto ){
        if (admin == null) {
        return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "index.html"));
        }
        return Respuesta.lista(listarTarifasPorPuesto(puesto));
    }
    

    //SIN TERMINAR PUNTO 4 
    // @PostMapping("/emularTransito")
    // public List<Respuesta> emularTransito(
    //         @SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin,
    //         @RequestParam String puesto,
    //         @RequestParam String matricula,
    //         @RequestParam String fechaHora) {
    //     if (admin == null) {
    //         return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "index.html"));
    //     }

    //     try {
    //     // 1️⃣ Buscar el puesto por nombre
    //     Puesto p = Fachada.getInstancia().buscarPuestoPorNombre(puesto);
    //     if (p == null) {
    //         return Respuesta.lista(new Respuesta("error", "El puesto no existe."));
    //     }

    //     // 2️⃣ Emular el tránsito a través de la fachada
    //     Transito nuevo = Fachada.getInstancia().emularTransito(matricula, p, fechaHora);

    //     // 3️⃣ Devolver DTO con éxito
    //     EmularTransitoDto transitoDto = new EmularTransitoDto(nuevo);
    //     return Respuesta.lista(
    //             new Respuesta("transito", transitoDto),
    //             mensaje("Tránsito emulado correctamente")
    //     );

    // } catch (PeajeException e) {
    //     return Respuesta.lista(new Respuesta("error", e.getMessage()));
    // } catch (Exception e) {
    //     return Respuesta.lista(new Respuesta("error", "Error inesperado al emular el tránsito"));
    // }


    private Respuesta listarTarifasPorPuesto(String nombrePuesto){
    
        tarifas = new ArrayList<>(Fachada.getInstancia().getTarifas());

        List<TarifaDto> tarifaDto = new ArrayList<>();
        for (Tarifa t : tarifas) {
            Puesto p = t.getPuesto(); 
            if (p != null && p.getNombre().equalsIgnoreCase(nombrePuesto)) {
                tarifaDto.add(new TarifaDto(t)); 
            }
        }
        return new Respuesta("tarifa", tarifaDto);
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
        return new Respuesta("categoria", categoriaDto);
    }


    private Respuesta mensaje(String msg){
        return new Respuesta("mensaje", msg);
    }   
}
