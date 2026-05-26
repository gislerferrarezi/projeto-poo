package src.jogodamemoria.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

public class JanelaMenuPrincipal extends JFrame {
   
    private JButton btnUmJogador;
    private JButton btnDoisJogadores;
    private JButton btnCreditos;
    private JButton btnSair;

    public JanelaMenuPrincipal() {        
        setTitle("JOGO DA MEMÓRIA");
        setSize(1280, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
       
        JLabel lblTitulo = new JLabel("JOGO DA MEMÓRIA", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0)); // Margem no topo
        add(lblTitulo, BorderLayout.NORTH);
        
        JPanel painelBotoes = new JPanel(new GridLayout(4, 1));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 50, 40, 50)); // Margens laterais

        btnUmJogador = new JButton("Um Jogador (Solo)");
        btnUmJogador.setFont(new Font("Arial", Font.BOLD, 16));
        btnUmJogador.setFocusable(false);

        btnDoisJogadores = new JButton("Dois Jogadores (Versus)");
        btnDoisJogadores.setFont(new Font("Arial", Font.BOLD, 16));
        btnDoisJogadores.setFocusable(false);

        btnCreditos = new JButton("Creditos");
        btnCreditos.setFont(new Font("Arial", Font.BOLD, 16));
        btnCreditos.setFocusable(false);

        btnSair = new JButton("Sair do Jogo");
        btnSair.setFont(new Font("Arial", Font.BOLD, 16));
        btnSair.setFocusable(false);        
      
        painelBotoes.add(btnUmJogador);
        painelBotoes.add(btnDoisJogadores);
        painelBotoes.add(btnCreditos);
        painelBotoes.add(btnSair);

        add(painelBotoes, BorderLayout.CENTER);
    }    
    
    public JButton getBtnUmJogador() {
        return btnUmJogador;
    }

    public JButton getBtnDoisJogadores() {
        return btnDoisJogadores;
    }

    public JButton getBtnCreditos() {
        return btnCreditos;
    }

    public JButton getBtnSair() {
        return btnSair;
    }
}