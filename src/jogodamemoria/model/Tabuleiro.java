package src.jogodamemoria.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tabuleiro {

    private List<Carta> cartas;
    private int totalPares;
    private boolean isMultiplayer;

    public Tabuleiro(int totalPares, boolean isMultiplayer) {
        this.totalPares = totalPares;
        this.cartas = new ArrayList<>();
        this.isMultiplayer = isMultiplayer;
        inicializarTabuleiro();
    }

    private void inicializarTabuleiro() {
        if (!isMultiplayer) {
            // --- MODO SOLO ---
            for (int i = 1; i <= totalPares; i++) {
                String valorCarta = "Icone_" + i;
                cartas.add(new Carta(i, valorCarta));
                cartas.add(new Carta(i, valorCarta));
            }
        } else {            
            int j = 1;            
            for (int i = 1; i <= (totalPares); i++) {
                String valorCarta = "Icone_" + j;
                cartas.add(new Carta(j, valorCarta));
                cartas.add(new Carta(j, valorCarta));
                j++; 
            }                
            String valorPerdeu1 = "Perdeu_A_Vez_1";
            cartas.add(new Carta(j, valorPerdeu1, Carta.Tipo_Carta.PERDEU_A_VEZ));
            j++; 
            
            String valorPerdeu2 = "Perdeu_A_Vez_2";
            cartas.add(new Carta(j, valorPerdeu2, Carta.Tipo_Carta.PERDEU_A_VEZ));
            j++; 
            
            String valorDeNovo = "Jogue_De_Novo";
            cartas.add(new Carta(j, valorDeNovo, Carta.Tipo_Carta.JOGUE_DE_NOVO));
            cartas.add(new Carta(j, valorDeNovo, Carta.Tipo_Carta.JOGUE_DE_NOVO));
            j++; 
            
            String valorDobro = "Dobro_Pontos";
            cartas.add(new Carta(j, valorDobro, Carta.Tipo_Carta.DOBRO_PONTOS));
            cartas.add(new Carta(j, valorDobro, Carta.Tipo_Carta.DOBRO_PONTOS));
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