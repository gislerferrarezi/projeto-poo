package src.jogodamemoria.view;

import javax.swing.*;
import java.awt.*;

public class JanelaMenuMultiplayer extends JPanel {

    // Removemos o btnJogarFacil, pois o Multiplayer tem modo único!
    private JButton btnJogar; 
    private JButton btnVoltar;

    public JanelaMenuMultiplayer() {              
        setSize(1280, 800);       
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("MODO MULTIPLAYER - PREPARAÇÃO", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // Painel dividido ao meio (Esquerda: Jogar | Direita: Regras)
        JPanel painelModos = new JPanel(new GridLayout(1, 2, 20, 0));
        painelModos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // --- LADO ESQUERDO (Informações da Partida) ---
        JPanel painelInfo = new JPanel(new BorderLayout());
        painelInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel lblTituloInfo = new JLabel("Modo Competitivo (Difícil)", JLabel.CENTER);
        lblTituloInfo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblDescricaoInfo = new JLabel(
                "<html><center><body style='width: 220px; text-align: center; font-family: Arial; font-size: 13px; color: #555555;'>"
                        + "O objetivo é somar mais pontos que o seu adversário em uma verdadeira batalha mental.<br><br>"
                        + "Neste modo, o tabuleiro é maior e esconde armadilhas. Vocês alternam os turnos a cada erro ou ao puxar uma carta de penalidade.<br><br>"
                        + "Preparem-se para testar a concentração ao máximo!"
                        + "</body></center></html>");

        btnJogar = new JButton("Iniciar Partida Multiplayer");
        btnJogar.setFont(new Font("Arial", Font.BOLD, 14));
        btnJogar.setFocusable(false);

        painelInfo.add(lblTituloInfo, BorderLayout.NORTH);
        painelInfo.add(lblDescricaoInfo, BorderLayout.CENTER);
        painelInfo.add(btnJogar, BorderLayout.SOUTH);

        // --- LADO DIREITO (Regras Especiais) ---
        JPanel painelRegras = new JPanel(new BorderLayout());
        painelRegras.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 50, 50), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel lblTituloRegras = new JLabel("Regras Especiais", JLabel.CENTER);
        lblTituloRegras.setFont(new Font("Arial", Font.BOLD, 18));
        lblTituloRegras.setForeground(new Color(200, 50, 50));
        
        JLabel lblDescricaoRegras = new JLabel(
                "<html><body style='width: 240px; font-family: Arial; font-size: 13px; color: #333333;'>"
                        + "<b>⏱️ Tempo Limite:</b><br>"
                        + "Cada jogador tem no máximo <b>30 segundos</b> para fazer sua jogada, senão perde a vez!<br><br>"
                        + "<b>🃏 Cartas Especiais:</b><br>"
                        + "Além dos pares normais, vocês encontrarão:<br><br>"
                        + "• 🚫 <b>Perdeu a vez:</b> Passa o turno para o oponente imediatamente.<br><br>"
                        + "• 🔄 <b>Jogue de novo:</b> Você ganha uma tentativa extra na mesma rodada.<br><br>"
                        + "• ⚡ <b>Rodada 2x:</b> O próximo par que você acertar valerá 2 pontos!"
                        + "</body></html>");

        painelRegras.add(lblTituloRegras, BorderLayout.NORTH);
        painelRegras.add(lblDescricaoRegras, BorderLayout.CENTER);      

        painelModos.add(painelInfo);
        painelModos.add(painelRegras);
        add(painelModos, BorderLayout.CENTER);

        // --- RODAPÉ ---
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Arial", Font.PLAIN, 12));
        btnVoltar.setFocusable(false);

        painelRodape.add(btnVoltar);
        add(painelRodape, BorderLayout.SOUTH);
    }

    //GETTERS
    public JButton getBtnJogar() { return btnJogar; }
    public JButton getBtnVoltar() { return btnVoltar; }
}