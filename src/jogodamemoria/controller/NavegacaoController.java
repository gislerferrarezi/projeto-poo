package src.jogodamemoria.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import src.jogodamemoria.model.Jogador;
import src.jogodamemoria.model.Tabuleiro;
import src.jogodamemoria.view.JanelaMenuPrincipal;
import src.jogodamemoria.view.JanelaMenuSinglePlayer;
import src.jogodamemoria.view.JanelaSinglePlayer;
import src.jogodamemoria.view.JanelaVitoriaSingle;

public class NavegacaoController implements ActionListener {

    private JanelaMenuPrincipal janelaMenuPrincipal;
    private JanelaMenuSinglePlayer janelaMenuSinglePlayer;
    private JanelaVitoriaSingle janelaVitoriaSingle;

    public NavegacaoController(JanelaMenuPrincipal menuPrincipal) {
        this.janelaMenuPrincipal = menuPrincipal;

        this.janelaMenuPrincipal.getBtnUmJogador().addActionListener(this);
        this.janelaMenuPrincipal.getBtnDoisJogadores().addActionListener(this);
        this.janelaMenuPrincipal.getBtnCreditos().addActionListener(this);
        this.janelaMenuPrincipal.getBtnSair().addActionListener(this);
    }

    public void exibirVitoria(JanelaSinglePlayer janelaJogo, int tentativas, String tempo, Jogador jogador,
            Tabuleiro tabuleiro) {
        this.janelaVitoriaSingle = new JanelaVitoriaSingle(janelaJogo, tentativas, tempo, jogador, tabuleiro);

        this.janelaVitoriaSingle.getBtnMenu().addActionListener(this);
        this.janelaVitoriaSingle.getBtnJogarNovamente().addActionListener(this);
        this.janelaVitoriaSingle.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Botão Um Jogador (Solo)
        if (e.getSource() == janelaMenuPrincipal.getBtnUmJogador()) {
            janelaMenuPrincipal.setVisible(false);

            janelaMenuSinglePlayer = new JanelaMenuSinglePlayer();

            janelaMenuSinglePlayer.getBtnVoltar().addActionListener(this);
            janelaMenuSinglePlayer.getBtnJogarFacil().addActionListener(this);
            janelaMenuSinglePlayer.getBtnJogarPadrao().addActionListener(this);

            janelaMenuSinglePlayer.setVisible(true);
        }

        // Botão Dois Jogadores (Versus)
        else if (e.getSource() == janelaMenuPrincipal.getBtnDoisJogadores()) {
            JOptionPane.showMessageDialog(janelaMenuPrincipal,
                    "Modo Multiplayer em desenvolvimento!",
                    "Em Breve", JOptionPane.INFORMATION_MESSAGE);
        }

        // Botão Créditos
        else if (e.getSource() == janelaMenuPrincipal.getBtnCreditos()) {
            System.exit(0);
        }

        // Botão Sair do Jogo
        else if (e.getSource() == janelaMenuPrincipal.getBtnSair()) {
            System.exit(0);
        }

        // Cliques da Janela de Dificuldade
        if (janelaMenuSinglePlayer != null) {

            if (e.getSource() == janelaMenuSinglePlayer.getBtnVoltar()) {
                janelaMenuSinglePlayer.dispose();
                janelaMenuPrincipal.setVisible(true);
            }

            // Botão Modo Fácil
            else if (e.getSource() == janelaMenuSinglePlayer.getBtnJogarFacil()) {
                iniciarPartidaSolo(6);
            }

            // Botão Modo Padrão
            else if (e.getSource() == janelaMenuSinglePlayer.getBtnJogarPadrao()) {
                iniciarPartidaSolo(12);
            }
        }

        if (janelaVitoriaSingle != null) {

            // Botão Voltar ao Menu
            if (e.getSource() == janelaVitoriaSingle.getBtnMenu()) {
                janelaVitoriaSingle.dispose();
                janelaVitoriaSingle.getJanelaPrincipalJogo().dispose();
                janelaMenuPrincipal.setVisible(true);

                janelaVitoriaSingle = null;
            }

            // Botão Jogar Novamente
            else if (e.getSource() == janelaVitoriaSingle.getBtnJogarNovamente()) {
                janelaVitoriaSingle.dispose();
                JanelaSinglePlayer jogoAtual = (JanelaSinglePlayer) janelaVitoriaSingle.getJanelaPrincipalJogo();
                jogoAtual.reiniciarJogo();
                janelaVitoriaSingle = null;
            }
        }
    }

    private void iniciarPartidaSolo(int totalPares) {
        String nome = JOptionPane.showInputDialog(janelaMenuSinglePlayer,
                "Digite seu nome para começar:",
                "Identificação do Jogador",
                JOptionPane.QUESTION_MESSAGE);

        if (nome != null && !nome.trim().isEmpty()) {
            janelaMenuSinglePlayer.dispose();

            Tabuleiro tabuleiro = new Tabuleiro(totalPares);
            Jogador jogador = new Jogador(nome);

            JanelaSinglePlayer jogo = new JanelaSinglePlayer(tabuleiro, jogador, this);
            jogo.setVisible(true);
        }
    }
}