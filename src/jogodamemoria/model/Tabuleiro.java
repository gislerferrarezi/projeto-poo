package src.jogodamemoria.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tabuleiro {

    private List<Carta> cartas;
    private int totalPares;

    public Tabuleiro(int totalPares) {
        this.totalPares = totalPares;
        this.cartas = new ArrayList<>();
        inicializarTabuleiro();
    }

    private void inicializarTabuleiro() {        
        for (int i = 1; i <= totalPares; i++) {
            String valorCarta = "Icone_" + i;      
            cartas.add(new Carta(i, valorCarta));            
            cartas.add(new Carta(i, valorCarta));
        }        
        Collections.shuffle(cartas);
    }
  
    public Carta getCarta(int indice) {
        return cartas.get(indice);
    }
  
    public int getTamanho() {
        return cartas.size();
    }
}