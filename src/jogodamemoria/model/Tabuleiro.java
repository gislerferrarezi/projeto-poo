package jogodamemoria.model;

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
        // 1. CARTAS NORMAIS 
        for (int i = 0; i < totalPares; i++) {
            String valorCarta = "" + (i + 1);
            cartas.add(new Carta(i, valorCarta));
            cartas.add(new Carta(i, valorCarta));
        }

        // 2. CARTAS ESPECIAIS (MULTIPLAYER) 
        if (isMultiplayer) {
            int idEspecial = totalPares;

            // Par 1: Perdeu a Vez
            String valorPerdeu = "Perdeu_A_Vez";
            cartas.add(new Carta(idEspecial, valorPerdeu, Carta.Tipo_Carta.PERDEU_A_VEZ));
            cartas.add(new Carta(idEspecial, valorPerdeu, Carta.Tipo_Carta.PERDEU_A_VEZ));
            idEspecial++;

            // Par 2: Jogue de Novo
            String valorDeNovo = "Jogue_De_Novo";
            cartas.add(new Carta(idEspecial, valorDeNovo, Carta.Tipo_Carta.JOGUE_DE_NOVO));
            cartas.add(new Carta(idEspecial, valorDeNovo, Carta.Tipo_Carta.JOGUE_DE_NOVO));
            idEspecial++;

            // Par 3: Dobro de Pontos
            String valorDobro = "Dobro_Pontos";
            cartas.add(new Carta(idEspecial, valorDobro, Carta.Tipo_Carta.DOBRO_PONTOS));
            cartas.add(new Carta(idEspecial, valorDobro, Carta.Tipo_Carta.DOBRO_PONTOS));
        }

        // Embaralha as cartas
        Collections.shuffle(cartas);
    }

    public Carta getCarta(int indice) {
        return cartas.get(indice);
    }

    public int getTamanho() {
        return cartas.size();
    }
}