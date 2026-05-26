package src.jogodamemoria.view;

import javax.swing.*;
import java.awt.*;

public class JanelaMenuSinglePlayer extends JFrame {

    private JButton btnJogarFacil;
    private JButton btnJogarPadrao;
    private JButton btnVoltar;

    public JanelaMenuSinglePlayer() {
        setTitle("SELECIONE A DIFICULDADE");
        setSize(1280, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("MODO SOLO - SELECIONE A DIFICULDADE", JLabel.CENTER);
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

        JLabel lblDescricaoFacil = new JLabel(
                "<html><center><body style='width: 180px; text-align: center; font-family: Arial; font-size: 12px; color: #555555;'>"
                        + "O objetivo é encontrar todos os pares o mais rápido possível, competindo contra o tempo e você mesmo.<br><br>"
                        + "Traz uma dinâmica mais tranquila, contando com <b>6 pares</b> (12 cartas no total) e <b>tentativas ilimitadas</b>."
                        + "</body></center></html>");

        btnJogarFacil = new JButton("Jogar Modo Fácil");
        btnJogarFacil.setFont(new Font("Arial", Font.BOLD, 14));
        btnJogarFacil.setFocusable(false);

        painelFacil.add(lblTituloFacil, BorderLayout.NORTH);
        painelFacil.add(lblDescricaoFacil, BorderLayout.CENTER);
        painelFacil.add(btnJogarFacil, BorderLayout.SOUTH);

        // --- MODO Padrão (Direita) ---
        JPanel painelPadrao = new JPanel(new BorderLayout());
        painelPadrao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel lblTituloPadrao = new JLabel("Modo Padrão", JLabel.CENTER);
        lblTituloPadrao.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblDescricaoPadrao = new JLabel(
                "<html><center><body style='width: 180px; text-align: center; font-family: Arial; font-size: 12px; color: #555555;'>"
                        + "As mesmas regras do modo fácil se aplicam aqui, porém com um desafio muito maior para a sua mente.<br><br>"
                        + "São <b>12 pares</b> (24 cartas no total). Prepare-se para testar sua concentração ao máximo!"
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