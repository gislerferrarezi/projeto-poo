package src.jogodamemoria.main;

import src.jogodamemoria.controller.JogoController;
import src.jogodamemoria.model.Carta;
import src.jogodamemoria.model.Tabuleiro;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== INICIALIZANDO O JOGO DA MEMÓRIA ===");
       
        Tabuleiro tabuleiro = new Tabuleiro(4);

        System.out.println("\n--- Estado Inicial das Cartas (Como o jogador vê): ---");
        exibirTabuleiro(tabuleiro, false);

        System.out.println("\n--- Gabarito (Cartas Embaralhadas por trás dos panos): ---");
        exibirTabuleiro(tabuleiro, true);

        JogoController controlador = new JogoController();

        controlador.TentativaPares(tabuleiro, 4);        
    }

    private static void exibirTabuleiro(Tabuleiro tabuleiro, boolean mostrarGabarito) {
        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            Carta carta = tabuleiro.getCarta(i);

            if (mostrarGabarito) {                
                System.out.print("[" + carta.getValor() + " (ID:" + carta.getId() + ")] ");
            } else {              
                if (carta.isVirada()) {
                    System.out.print("[" + carta.getValor() + "] ");
                } else {
                    System.out.print("[ X ] ");
                }
            }
        }
        System.out.println(); 
    }

    
}
