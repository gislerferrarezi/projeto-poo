package src.jogodamemoria.view;

import javax.swing.*;
import java.awt.*;

public class JanelaMenuMultiplayer extends JPanel {

    private JButton btnJogarFacil;
    private JButton btnJogarPadrao;
    private JButton btnVoltar;

    public JanelaMenuMultiplayer() {              
        setSize(1280, 800);       
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("MODO MULTIPLAYER - SELECIONE A DIFICULDADE", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel painelModos = new JPanel(new GridLayout(1, 2, 20, 0));
        painelModos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // --- MODO FÁCIL (Esquerda) ---
        JPanel painelFacil = new JPanel(new BorderLayout());
        painelFacil.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel lblTituloFacil = new JLabel("Modo Fácil", JLabel.CENTER);
        lblTituloFacil.setFont(new Font("Arial", Font.BOLD, 18));

        // Texto adaptado para a dinâmica de 2 Jogadores (Turnos e pontuação)
        JLabel lblDescricaoFacil = new JLabel(
                "<html><center><body style='width: 180px; text-align: center; font-family: Arial; font-size: 12px; color: #555555;'>"
                        + "O objetivo é somar mais pontos que o seu adversário. Os jogadores alternam os turnos a cada erro.<br><br>"
                        + "Uma partida mais rápida e dinâmica, contando com <b>6 pares</b> (12 cartas no total). Perfeito para decidir no detalhe!"
                        + "</body></center></html>");

        btnJogarFacil = new JButton("Jogar Modo Fácil");
        btnJogarFacil.setFont(new Font("Arial", Font.BOLD, 14));
        btnJogarFacil.setFocusable(false);

        painelFacil.add(lblTituloFacil, BorderLayout.NORTH);
        painelFacil.add(lblDescricaoFacil, BorderLayout.CENTER);
        painelFacil.add(btnJogarFacil, BorderLayout.SOUTH);

        // --- MODO PADRÃO (Direita) ---
        JPanel painelPadrao = new JPanel(new BorderLayout());
        painelPadrao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel lblTituloPadrao = new JLabel("Modo Padrão", JLabel.CENTER);
        lblTituloPadrao.setFont(new Font("Arial", Font.BOLD, 18));

        // Texto adaptado para o desafio maior em dupla
        JLabel lblDescricaoPadrao = new JLabel(
                "<html><center><body style='width: 180px; text-align: center; font-family: Arial; font-size: 12px; color: #555555;'>"
                        + "As mesmas regras de turnos se aplicam aqui, exigindo o dobro de atenção para decorar as cartas reveladas pelo oponente.<br><br>"
                        + "São <b>12 pares</b> (24 cartas no total). Uma verdadeira batalha mental para ver quem tem a melhor memória!"
                        + "</body></center></html>");

        btnJogarPadrao = new JButton("Jogar Modo Padrão");
        btnJogarPadrao.setFont(new Font("Arial", Font.BOLD, 14));
        btnJogarPadrao.setFocusable(false);

        painelPadrao.add(lblTituloPadrao, BorderLayout.NORTH);
        painelPadrao.add(lblDescricaoPadrao, BorderLayout.CENTER);
        painelPadrao.add(btnJogarPadrao, BorderLayout.SOUTH);

        painelModos.add(painelFacil);
        painelModos.add(painelPadrao);
        add(painelModos, BorderLayout.CENTER);

        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Arial", Font.PLAIN, 12));
        btnVoltar.setFocusable(false);

        painelRodape.add(btnVoltar);
        add(painelRodape, BorderLayout.SOUTH);
    }

    public JButton getBtnJogarFacil() {
        return btnJogarFacil;
    }

    public JButton getBtnJogarPadrao() {
        return btnJogarPadrao;
    }

    public JButton getBtnVoltar() {
        return btnVoltar;
    }
}