package src.jogodamemoria.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;
import src.jogodamemoria.model.Tabuleiro;
import src.jogodamemoria.model.Jogador;

public class JanelaSinglePlayer extends JFrame {

    private Tabuleiro tabuleiro;
    private Jogador jogador;

    public JanelaSinglePlayer(Tabuleiro tabuleiro, Jogador jogador) {
        this.tabuleiro = tabuleiro;
        this.jogador = jogador;

        // 1. CONFIGURAÇÕES DA JANELA PRINCIPAL
        setTitle("Jogo da Memória - Modo Solo");
        setSize(1280, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);        
        
        setLayout(new BorderLayout(20, 20)); 
        
        String textoTitulo = "";
        int colunas = 0;
        int lines = 0; 

        if (tabuleiro.getTamanho() == 12) {
            textoTitulo = "UM JOGADOR - MODO FÁCIL";
            colunas = 4;
            lines = 3;          
        } else {
            textoTitulo = "UM JOGADOR - MODO PADRÃO";
            colunas = 5;
            lines = 4;
        }     
    
        JLabel labelTitulo = new JLabel(textoTitulo, JLabel.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24)); 
        
        JPanel superior = new JPanel(new GridLayout(1, 1));
        superior.add(labelTitulo);
        add(superior, BorderLayout.NORTH); 
       
        JLabel lblNome = new JLabel("Jogador: " + jogador.getNome(), JLabel.CENTER);
        JLabel lblPontos = new JLabel("Pares Feitos: 0", JLabel.CENTER);
        JLabel lblTempo = new JLabel("Tempo: 00:00", JLabel.CENTER);
        
        Font fonteHUD = new Font("Arial", Font.PLAIN, 18);
        lblNome.setFont(fonteHUD);
        lblPontos.setFont(fonteHUD);
        lblTempo.setFont(fonteHUD);
        
        JPanel inferior = new JPanel(new GridLayout(1, 3));
        inferior.add(lblNome);
        inferior.add(lblPontos);
        inferior.add(lblTempo);
        
        add(inferior, BorderLayout.SOUTH); 
       
        setVisible(true);
    }
}