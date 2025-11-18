package uy.edu.ort.peaje.observador;

import java.util.ArrayList;
//que hace? Esta clase representa un objeto observable en el patrón de diseño Observador.
//Permite agregar, quitar y notificar a los observadores registrados sobre eventos que ocurren 
//en el objeto observable.

public class Observable {
    private ArrayList<Observador> observadores = new ArrayList<>();
     public void agregarObservador(Observador obs){
        if(!observadores.contains(obs)){
            observadores.add(obs);
        }
    }

    public void quitarObservador(Observador obs){
        observadores.remove(obs);
    }

    public void avisar(Object evento){
        ArrayList<Observador> copia = new ArrayList<>(observadores);
        for(Observador obs:copia){
            obs.actualizar(evento, this);
        }
    }
}
