package src.jogodamemoria.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaMenu extends JFrame {

    public JanelaMenu() {
        setTitle("Menu - Jogo da Memória");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);        
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        JLabel titulo = new JLabel("JOGO DA MEMÓRIA");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton btnSingle = new JButton("Modo 1 Jogador");
        JButton btnMulti = new JButton("Modo 2 Jogadores");
        
        btnSingle.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMulti.setAlignmentX(Component.CENTER_ALIGNMENT);
      
        add(Box.createVerticalStrut(30));
        add(titulo);
        add(Box.createVerticalStrut(40));
        add(btnSingle);
        add(Box.createVerticalStrut(15));
        add(btnMulti);
        
        btnSingle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clicou em 1 jogador! Vamos abrir a JanelaSingleplayer...");                
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