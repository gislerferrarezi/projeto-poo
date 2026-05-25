package src.jogodamemoria.main;

import java.util.Scanner;
import java.util.ArrayList;

import src.jogodamemoria.controller.GerenciadorJogo;
import src.jogodamemoria.model.Tabuleiro;
import src.jogodamemoria.view.JanelaMenu;
import src.jogodamemoria.model.Jogador;

public class Main {
    public static void main(String[] args) {

        new JanelaMenu();

        Scanner sc = new Scanner(System.in);
        ArrayList<Jogador> jogadores = new ArrayList<>();

        System.out.println("=========================================");
        System.out.println("      BEM-VINDO AO JOGO DA MEMÓRIA       ");
        System.out.println("=========================================");
       
        int totalPares = 4;
        Tabuleiro tabuleiro = new Tabuleiro(totalPares);
        GerenciadorJogo controlador = new GerenciadorJogo();      
        
        System.out.println("Quantidade de jogadores (1-2): ");
        int qtd_jogadores = sc.nextInt();

        while (qtd_jogadores != 1 && qtd_jogadores != 2) {
            System.out.println("Quantidade inválida! Digite 1 ou 2: ");
            qtd_jogadores = sc.nextInt();
        }

        sc.nextLine(); 

        if (qtd_jogadores == 1) {
            System.out.println("Digite o nome do Jogador: ");
            String nome = sc.nextLine();
            jogadores.add(new Jogador(nome));
        } else {
            System.out.println("Digite o nome do Jogador 1: ");
            String nome1 = sc.nextLine();
            jogadores.add(new Jogador(nome1));
            
            System.out.println("Digite o nome do Jogador 2: ");
            String nome2 = sc.nextLine();
            jogadores.add(new Jogador(nome2));
        }   
               
        controlador.TentativaPares(tabuleiro, totalPares, jogadores);
        
        sc.close();
    }   
}