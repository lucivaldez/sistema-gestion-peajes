package uy.edu.ort.peaje;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import uy.edu.ort.peaje.excepciones.PeajeException;
import uy.edu.ort.peaje.servicios.DatosPrueba;

@SpringBootApplication
public class PeajeApplication {

	public static void main(String[] args) throws PeajeException {
		SpringApplication.run(PeajeApplication.class, args);
		DatosPrueba.cargar();
	}
}
