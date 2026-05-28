package src.jogodamemoria.controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import src.jogodamemoria.model.Jogador;
import src.jogodamemoria.model.Tabuleiro;
import src.jogodamemoria.view.JanelaCreditos;
import src.jogodamemoria.view.JanelaMenuMultiplayer;
import src.jogodamemoria.view.JanelaMenuPrincipal;
import src.jogodamemoria.view.JanelaMenuSinglePlayer;
import src.jogodamemoria.view.JanelaSinglePlayer;
import src.jogodamemoria.view.JanelaVitoriaSingle;
import src.jogodamemoria.view.JanelaMultiplayer;
import src.jogodamemoria.view.JanelaVitoriaMultiplayer;

public class NavegacaoController implements ActionListener {

    private JanelaMenuPrincipal janelaMenuPrincipal;
    private JanelaMenuSinglePlayer janelaMenuSinglePlayer;
    private JanelaMenuMultiplayer janelaMenuMultiplayer;
    private JanelaCreditos janelaCreditos;

    private JanelaVitoriaSingle janelaVitoriaSingle;
    private JanelaVitoriaMultiplayer janelaVitoriaMultiplayer;

    private CardLayout cardLayout;
    private JPanel painelContentor;

    public NavegacaoController(JanelaMenuPrincipal menuPrincipal) {
        this.janelaMenuPrincipal = menuPrincipal;
        this.cardLayout = new CardLayout();
        this.painelContentor = new JPanel(cardLayout);
        this.janelaMenuSinglePlayer = new JanelaMenuSinglePlayer();
        this.janelaMenuMultiplayer = new JanelaMenuMultiplayer();
        this.janelaCreditos = new JanelaCreditos();

        JPanel painelInicio = (JPanel) menuPrincipal.getContentPane();

        this.painelContentor.add(painelInicio, "MENU_PRINCIPAL");
        this.painelContentor.add(janelaMenuSinglePlayer, "MENU_SINGLEPLAYER");
        this.painelContentor.add(janelaMenuMultiplayer, "MENU_MULTIPLAYER");
        this.painelContentor.add(janelaCreditos, "CREDITOS");

        menuPrincipal.setContentPane(painelContentor);
        cardLayout.show(painelContentor, "MENU_PRINCIPAL"); // Começa no menu inicial

        this.janelaMenuPrincipal.getBtnUmJogador().addActionListener(this);
        this.janelaMenuPrincipal.getBtnDoisJogadores().addActionListener(this);
        this.janelaMenuPrincipal.getBtnCreditos().addActionListener(this);
        this.janelaMenuPrincipal.getBtnSair().addActionListener(this);

        this.janelaMenuSinglePlayer.getBtnVoltar().addActionListener(this);
        this.janelaMenuSinglePlayer.getBtnJogarFacil().addActionListener(this);
        this.janelaMenuSinglePlayer.getBtnJogarPadrao().addActionListener(this);

        this.janelaMenuMultiplayer.getBtnVoltar().addActionListener(this);
        this.janelaMenuMultiplayer.getBtnJogar().addActionListener(this);
      

        this.janelaCreditos.getBtnVoltar().addActionListener(this);
    }

    // --- MÉTODOS DE EXIBIR POPUP DE VITÓRIA ---
    public void exibirVitoria(JanelaSinglePlayer janelaJogo, int tentativas, String tempo, Jogador jogador,
            Tabuleiro tabuleiro) {
        this.janelaVitoriaSingle = new JanelaVitoriaSingle(janelaJogo, tentativas, tempo, jogador, tabuleiro);
        this.janelaVitoriaSingle.getBtnMenu().addActionListener(this);
        this.janelaVitoriaSingle.getBtnJogarNovamente().addActionListener(this);
        this.janelaVitoriaSingle.setVisible(true);
    }

    public void exibirVitoriaMultiplayer(JanelaMultiplayer janelaJogo, Jogador vencedor, Jogador jogador1,
            Jogador jogador2) {
        this.janelaVitoriaMultiplayer = new JanelaVitoriaMultiplayer(janelaJogo, vencedor, jogador1, jogador2, null);
        this.janelaVitoriaMultiplayer.getBtnMenu().addActionListener(this);
        this.janelaVitoriaMultiplayer.getBtnJogarNovamente().addActionListener(this);
        this.janelaVitoriaMultiplayer.setVisible(true);
    }

    // --- EVENTOS DE CLIQUE ---
    @Override
    public void actionPerformed(ActionEvent e) {
        // 1. CLIQUES DO MENU PRINCIPAL
        if (e.getSource() == janelaMenuPrincipal.getBtnUmJogador()) {
            cardLayout.show(painelContentor, "MENU_SINGLEPLAYER");
            janelaMenuPrincipal.revalidate();
            janelaMenuPrincipal.repaint();
        }

        else if (e.getSource() == janelaMenuPrincipal.getBtnDoisJogadores()) {
            cardLayout.show(painelContentor, "MENU_MULTIPLAYER");
            janelaMenuPrincipal.revalidate();
            janelaMenuPrincipal.repaint();
        }

        else if (e.getSource() == janelaMenuPrincipal.getBtnCreditos()) {
            cardLayout.show(painelContentor, "CREDITOS");
            janelaMenuPrincipal.revalidate();
            janelaMenuPrincipal.repaint();
        }

        else if (e.getSource() == janelaMenuPrincipal.getBtnSair()) {            
            int resposta = JOptionPane.showConfirmDialog(
                    janelaMenuPrincipal,
                    "Deseja realmente sair do jogo?",
                    "Confirmar Saída",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            
            if (resposta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }            
        }

        // 2. CLIQUES DO MENU DE DIFICULDADE SINGLEPLAYER
        else if (e.getSource() == janelaMenuSinglePlayer.getBtnVoltar()) {
            cardLayout.show(painelContentor, "MENU_PRINCIPAL");
            janelaMenuPrincipal.revalidate();
            janelaMenuPrincipal.repaint();
        }

        else if (e.getSource() == janelaMenuSinglePlayer.getBtnJogarFacil()) {
            iniciarPartidaSolo(6); // 6 pares = 12 cartas
        }

        else if (e.getSource() == janelaMenuSinglePlayer.getBtnJogarPadrao()) {
            iniciarPartidaSolo(12); // 12 pares = 24 cartas
        }

        // 3. CLIQUES DO MENU DE DIFICULDADE MULTIPLAYER
        else if (e.getSource() == janelaMenuMultiplayer.getBtnVoltar()) {
            cardLayout.show(painelContentor, "MENU_PRINCIPAL");
            janelaMenuPrincipal.revalidate();
            janelaMenuPrincipal.repaint();
        }      

        else if (e.getSource() == janelaMenuMultiplayer.getBtnJogar()) {
            iniciarPartidaMultiplayer(12);
        }

        // 4. CLIQUES DA JANELA CREDITOS
        else if (e.getSource() == janelaCreditos.getBtnVoltar()) {
            cardLayout.show(painelContentor, "MENU_PRINCIPAL");
            janelaMenuPrincipal.revalidate();
            janelaMenuPrincipal.repaint();
        }

        // 5. CLIQUES DA JANELA DE VITÓRIA SINGLE (POPUP)
        if (janelaVitoriaSingle != null) {

            if (e.getSource() == janelaVitoriaSingle.getBtnMenu()) {
                janelaVitoriaSingle.dispose();
                janelaVitoriaSingle.getJanelaPrincipalJogo().dispose();

                cardLayout.show(painelContentor, "MENU_PRINCIPAL");
                janelaMenuPrincipal.setVisible(true);

                janelaVitoriaSingle = null;
            }

            else if (e.getSource() == janelaVitoriaSingle.getBtnJogarNovamente()) {
                janelaVitoriaSingle.dispose();

                JanelaSinglePlayer jogoAtual = (JanelaSinglePlayer) janelaVitoriaSingle.getJanelaPrincipalJogo();
                jogoAtual.reiniciarJogo();

                janelaVitoriaSingle = null;
            }
        }

        // 6. CLIQUES DA JANELA DE VITÓRIA MULTIPLAYER (POPUP)
        if (janelaVitoriaMultiplayer != null) {

            if (e.getSource() == janelaVitoriaMultiplayer.getBtnMenu()) {
                janelaVitoriaMultiplayer.dispose();
                janelaVitoriaMultiplayer.getJanelaPrincipalJogo().dispose();

                cardLayout.show(painelContentor, "MENU_PRINCIPAL");
                janelaMenuPrincipal.setVisible(true);

                janelaVitoriaMultiplayer = null;
            }

            else if (e.getSource() == janelaVitoriaMultiplayer.getBtnJogarNovamente()) {
                janelaVitoriaMultiplayer.dispose();

                JanelaMultiplayer jogoAntigo = (JanelaMultiplayer) janelaVitoriaMultiplayer.getJanelaPrincipalJogo();

                String nome1 = janelaVitoriaMultiplayer.getJogador1().getNome();
                String nome2 = janelaVitoriaMultiplayer.getJogador2().getNome();

                // Calculando quantos pares tinha no jogo que acabou de terminar
                int totalPares = jogoAntigo.getContentPane().getComponentCount() > 14 ? 12 : 6;
                jogoAntigo.dispose();

                // Recria a partida
                Tabuleiro novoTabuleiro = new Tabuleiro(totalPares, true);
                Jogador j1 = new Jogador(nome1);
                Jogador j2 = new Jogador(nome2);

                JanelaMultiplayer novoJogo = new JanelaMultiplayer(novoTabuleiro, j1, j2, this);
                novoJogo.setVisible(true);

                janelaVitoriaMultiplayer = null;
            }
        }
    }

    // --- MÉTODOS DE INICIALIZAÇÃO DE PARTIDA ---
    private void iniciarPartidaSolo(int totalPares) {
        String nome = JOptionPane.showInputDialog(janelaMenuPrincipal,
                "Digite seu nome para começar:",
                "Identificação do Jogador",
                JOptionPane.QUESTION_MESSAGE);

        if (nome != null && !nome.trim().isEmpty()) {
            Tabuleiro tabuleiro = new Tabuleiro(totalPares, false);
            Jogador jogador = new Jogador(nome);

            JanelaSinglePlayer jogo = new JanelaSinglePlayer(tabuleiro, jogador, this);
            jogo.setVisible(true);

            janelaMenuPrincipal.setVisible(false);
        }
    }

    private void iniciarPartidaMultiplayer(int totalPares) {
        String nome1 = JOptionPane.showInputDialog(janelaMenuPrincipal,
                "Digite o nome do Jogador 1:",
                "Identificação do Jogador 1",
                JOptionPane.QUESTION_MESSAGE);

        if (nome1 != null && !nome1.trim().isEmpty()) {

            String nome2 = JOptionPane.showInputDialog(janelaMenuPrincipal,
                    "Digite o nome do Jogador 2:",
                    "Identificação do Jogador 2",
                    JOptionPane.QUESTION_MESSAGE);

            if (nome2 != null && !nome2.trim().isEmpty()) {
                Tabuleiro tabuleiro = new Tabuleiro(totalPares, true);
                Jogador j1 = new Jogador(nome1);
                Jogador j2 = new Jogador(nome2);

                JanelaMultiplayer jogo = new JanelaMultiplayer(tabuleiro, j1, j2, this);
                jogo.setVisible(true);

                janelaMenuPrincipal.setVisible(false);
            }
        }
    }
}