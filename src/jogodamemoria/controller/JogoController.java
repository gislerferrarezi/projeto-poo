package src.jogodamemoria.controller;

import java.util.Scanner;
import java.util.ArrayList;
import src.jogodamemoria.model.Carta;
import src.jogodamemoria.model.Tabuleiro;
import src.jogodamemoria.model.Jogador;

public class JogoController {

    public void TentativaPares(Tabuleiro tabuleiro, int totalPares, ArrayList<Jogador> jogadores) {
        Scanner sc = new Scanner(System.in);
        int totalParesFormados = 0;
        int indiceJogadorAtual = 0;

        imprimirTabuleiro(tabuleiro);

        while (totalParesFormados < totalPares) {
            Jogador jogadorAtual = jogadores.get(indiceJogadorAtual);

            System.out.println("\n=== VEZ DE: " + jogadorAtual.getNome() + " ===");

            System.out
                    .println("\nDigite a posicao da carta que deseja virar (0-" + (tabuleiro.getTamanho() - 1) + "):");
            Carta carta1 = escolherCartaValida(sc, tabuleiro, null);
            carta1.virar();
            imprimirTabuleiro(tabuleiro);

            System.out.println("Digite outra posicao:");
            Carta carta2 = escolherCartaValida(sc, tabuleiro, carta1);
            carta2.virar();
            imprimirTabuleiro(tabuleiro);

            if (carta1.getId() == carta2.getId()) {
                carta1.setDescoberta(true);
                carta2.setDescoberta(true);
                totalParesFormados++;
                System.out.println("Boa! Achou uma combinação!");
                jogadorAtual.ganharPonto();

            } else {
                System.out.println("Você errou! As cartas vão voltar a se esconder.");
                carta1.esconder();
                carta2.esconder();
                if (jogadores.size() > 1) {
                    if (indiceJogadorAtual == 0) {
                        indiceJogadorAtual++;
                    } else {
                        indiceJogadorAtual--;
                    }
                }
            }
        }

        System.out.println("\nParabéns! Você completou o jogo!");
        sc.close();
    }

    private Carta escolherCartaValida(Scanner sc, Tabuleiro tabuleiro, Carta primeiraCartaEscolhida) {
        while (true) {
            int pos = sc.nextInt();

            // Validação 1: Está dentro dos limites do tabuleiro?
            if (pos < 0 || pos >= tabuleiro.getTamanho()) {
                System.out
                        .println("Posição inválida! Digite um número entre 0 e " + (tabuleiro.getTamanho() - 1) + ":");
                continue;
            }

            Carta carta = tabuleiro.getCarta(pos);

            // Validação 2: A carta já foi limpa/descoberta antes?
            if (carta.isDescoberta()) {
                System.out.println("Essa carta já foi descoberta! Escolha outra posição:");
                continue;
            }

            // Validação 3: É a mesma posição da primeira carta da rodada?
            if (primeiraCartaEscolhida != null && carta == primeiraCartaEscolhida) {
                System.out.println("Você não pode escolher a mesma carta duas vezes! Escolha outra:");
                continue;
            }
            return carta;
        }
    }

    public static void imprimirTabuleiro(Tabuleiro tabuleiro) {
        System.out.println();
        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            Carta carta = tabuleiro.getCarta(i);

            if (carta.isVirada() || carta.isDescoberta()) {
                System.out.print("[" + carta.getValor() + "] ");
            } else {
                System.out.print("[  X  ] ");
            }
        }
        System.out.println("\n");
    }
}