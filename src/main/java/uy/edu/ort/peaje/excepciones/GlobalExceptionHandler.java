package uy.edu.ort.peaje.excepciones;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
 
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final int errorCodeStatus = 299;
   
    @ExceptionHandler(PeajeException.class)
     public ResponseEntity<String> manejarException(PeajeException ex) {
        return ResponseEntity.status(errorCodeStatus).body(ex.getMessage());
     }
}
 
