package src.jogodamemoria.view;

import javax.swing.*;

import src.jogodamemoria.model.Jogador;
import src.jogodamemoria.model.Tabuleiro;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaMenu extends JFrame {

    public JanelaMenu() {        
        setTitle("Jogo da Memória");
        setSize(300, 250); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new GridLayout(3, 1, 10, 10)); 
        
        JLabel titulo = new JLabel("JOGO DA MEMÓRIA", SwingConstants.CENTER); 
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        JButton btnSingle = new JButton("Modo 1 Jogador");
        JButton btnMulti = new JButton("Modo 2 Jogadores");
        
        add(titulo);
        add(btnSingle);
        add(btnMulti);

        btnSingle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tabuleiro tabuleiro = new Tabuleiro(6);
                Jogador jogadorSolo = new Jogador("Jogador 1");

                new JanelaSinglePlayer(tabuleiro, jogadorSolo);
                dispose();
            }
        });

        btnMulti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clicou em 2 jogadores! Vamos abrir a JanelaMultiplayer...");
            }
        });

        setVisible(true);
    }
}