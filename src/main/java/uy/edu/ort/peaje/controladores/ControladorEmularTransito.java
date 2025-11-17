package uy.edu.ort.peaje.controladores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import uy.edu.ort.peaje.dtos.PuestoDto;
import uy.edu.ort.peaje.dtos.ResultadoDto;
import uy.edu.ort.peaje.dtos.TarifaDto;
import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.modelo.Administrador;
import uy.edu.ort.peaje.modelo.CategoriaVehiculo;
import uy.edu.ort.peaje.modelo.Puesto;
import uy.edu.ort.peaje.modelo.Tarifa;
import uy.edu.ort.peaje.modelo.Transito;
import uy.edu.ort.peaje.modelo.Vehiculo;
import uy.edu.ort.peaje.servicios.fachada.Fachada;
import uy.edu.ort.peaje.utils.Respuesta;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/transito")
@Scope("session")
public class ControladorEmularTransito {
    private List<Puesto> puestos;
    private List<Tarifa> tarifas;

    @GetMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(
            @SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin) {
        if (admin == null) {

            return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "index.html"));
        }
        return Respuesta.lista(listarPuestos());
    }

    @GetMapping("/tarifasPuesto")
    public List<Respuesta> tarifasPorPuesto(
            @SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin,
            @RequestParam String puesto) {
        if (admin == null) {
            return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "index.html"));
        }
        return Respuesta.lista(listarTarifasPorPuesto(puesto));
    }

    @PostMapping("/emularTransito")
    public List<Respuesta> emularTransito(
            @SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin,
            @RequestParam String puesto,
            @RequestParam String matricula,
            @RequestParam(name = "fecha", required = false) String fechaHoraStr) {

        if (admin == null) {
            return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "index.html"));
        }

        try {
            // Buscar puesto por fachada
            Puesto p = Fachada.getInstancia().buscarPuestoPorNombre(puesto);
            if (p == null) {
                return Respuesta.lista(new Respuesta("error", "El puesto no existe."));
            }

            Vehiculo v = Fachada.getInstancia().buscarVehiculoPorMatricula(matricula);
            if (v == null) {
                return Respuesta.lista(new Respuesta("error", "No existe el vehículo."));
            }

            Date fechaHora = parsearFechaHora(fechaHoraStr);

            Transito nuevo = Fachada.getInstancia().emularTransito(v, p, fechaHora);

            ResultadoDto resultadoDto = Fachada.getInstancia().construirResultadoTransito(nuevo);

            return Respuesta.lista(
                    new Respuesta("resultado", resultadoDto),
                    new Respuesta("mensaje","Tránsito emulado correctamente"),
                    new Respuesta("limpiarFormulario", null));

        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("error", e.getMessage()));
        } catch (Exception e) {
            return Respuesta.lista(new Respuesta("error", "Error inesperado al emular el tránsito"));
        }
    }

    private Date parsearFechaHora(String fechaHoraStr) throws PeajeException {

        if (fechaHoraStr == null || fechaHoraStr.isBlank()) {
            throw new PeajeException("Debe ingresar la fecha y hora del tránsito (formato yyyy-MM-dd HH:mm).");
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            sdf.setLenient(false);
            return sdf.parse(fechaHoraStr);

        } catch (ParseException e) {
            throw new PeajeException("Formato de fecha inválido. Use yyyy-MM-dd HH:mm.");
        }
    }

    private Respuesta listarTarifasPorPuesto(String nombrePuesto) {

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

    private Respuesta listarPuestos() {

        puestos = new ArrayList<Puesto>(Fachada.getInstancia().getPuestos());

        List<PuestoDto> puestoDto = new ArrayList<PuestoDto>();

        for (Puesto p : puestos) {
            puestoDto.add(new PuestoDto(p));
        }
        return new Respuesta("puesto", puestoDto);
    }   
}
