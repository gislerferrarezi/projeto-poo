package jogodamemoria.controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Component;

import jogodamemoria.model.Jogador;
import jogodamemoria.model.Tabuleiro;
import jogodamemoria.view.JanelaCreditos;
import jogodamemoria.view.JanelaMenuMultiplayer;
import jogodamemoria.view.JanelaMenuPrincipal;
import jogodamemoria.view.JanelaMenuSinglePlayer;
import jogodamemoria.view.JanelaSinglePlayer;
import jogodamemoria.view.JanelaVitoriaSingle;
import jogodamemoria.view.JanelaMultiplayer;
import jogodamemoria.view.JanelaVitoriaMultiplayer;

public class NavegacaoController implements ActionListener {

    private JanelaMenuPrincipal janelaMenuPrincipal;
    private JanelaMenuSinglePlayer janelaMenuSinglePlayer;
    private JanelaMenuMultiplayer janelaMenuMultiplayer;
    private JanelaCreditos janelaCreditos;

    // Painéis das partidas
    private JanelaSinglePlayer painelJogoSingle;
    private JanelaMultiplayer painelJogoMulti;

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

        // Registra as telas estáticas no CardLayout
        this.painelContentor.add(painelInicio, "MENU_PRINCIPAL");
        this.painelContentor.add(janelaMenuSinglePlayer, "MENU_SINGLEPLAYER");
        this.painelContentor.add(janelaMenuMultiplayer, "MENU_MULTIPLAYER");
        this.painelContentor.add(janelaCreditos, "CREDITOS");

        menuPrincipal.setContentPane(painelContentor);
        cardLayout.show(painelContentor, "MENU_PRINCIPAL");

        // Listeners dos Menus
        this.janelaMenuPrincipal.getBtnUmJogador().addActionListener(this);
        this.janelaMenuPrincipal.getBtnDoisJogadores().addActionListener(this);
        this.janelaMenuPrincipal.getBtnCreditos().addActionListener(this);
        this.janelaMenuPrincipal.getBtnSair().addActionListener(this);

        this.janelaMenuSinglePlayer.getBtnVoltar().addActionListener(this);
        this.janelaMenuSinglePlayer.getBtnJogarFacil().addActionListener(this);
        this.janelaMenuSinglePlayer.getBtnJogarPadrao().addActionListener(this);

        // Listeners do Menu Multiplayer
        this.janelaMenuMultiplayer.getBtnVoltar().addActionListener(this);
        this.janelaMenuMultiplayer.getBtnMultiplayerLocal().addActionListener(this);
        this.janelaMenuMultiplayer.getBtnMultiplayerOnline().addActionListener(this);

        this.janelaCreditos.getBtnVoltar().addActionListener(this);
    }

    // --- MÉTODOS DE EXIBIR POPUP DE VITÓRIA ---
    public void exibirVitoria(JanelaSinglePlayer janelaJogo, int tentativas, String tempo, Jogador jogador,
            Tabuleiro tabuleiro) {
        this.janelaVitoriaSingle = new JanelaVitoriaSingle(janelaMenuPrincipal, tentativas, tempo, jogador, tabuleiro);
        this.janelaVitoriaSingle.getBtnMenu().addActionListener(this);
        this.janelaVitoriaSingle.getBtnJogarNovamente().addActionListener(this);
        this.janelaVitoriaSingle.setVisible(true);
    }

    public void exibirVitoriaMultiplayer(JanelaMultiplayer janelaJogo, Jogador vencedor, Jogador jogador1,
            Jogador jogador2) {
        this.janelaVitoriaMultiplayer = new JanelaVitoriaMultiplayer(janelaMenuPrincipal, vencedor, jogador1, jogador2,
                null);
        this.janelaVitoriaMultiplayer.getBtnMenu().addActionListener(this);
        this.janelaVitoriaMultiplayer.getBtnJogarNovamente().addActionListener(this);
        this.janelaVitoriaMultiplayer.setVisible(true);
    }

    // --- EVENTOS DE CLIQUE ---
    @Override
    public void actionPerformed(ActionEvent e) {
        Object fonte = e.getSource();

        // 1. CLIQUES DO MENU PRINCIPAL
        if (fonte == janelaMenuPrincipal.getBtnUmJogador()) {
            AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");

            
            trocarTela("MENU_SINGLEPLAYER");
        } else if (fonte == janelaMenuPrincipal.getBtnDoisJogadores()) {
            AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
            trocarTela("MENU_MULTIPLAYER");
        } else if (fonte == janelaMenuPrincipal.getBtnCreditos()) {
            AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
            trocarTela("CREDITOS");
        } else if (fonte == janelaMenuPrincipal.getBtnSair()) {
            AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
            System.exit(0);
        }

        // 2. CLIQUES DO MENU SINGLEPLAYER
        else if (fonte == janelaMenuSinglePlayer.getBtnVoltar()) {
            AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
            trocarTela("MENU_PRINCIPAL");
        } else if (fonte == janelaMenuSinglePlayer.getBtnJogarFacil()) {
            AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
            iniciarPartidaSolo(6);          
        } else if (fonte == janelaMenuSinglePlayer.getBtnJogarPadrao()) {
            AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
            iniciarPartidaSolo(12);          
        }

        // 3. CLIQUES DO MENU MULTIPLAYER
        else if (fonte == janelaMenuMultiplayer.getBtnVoltar()) {
            AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
            trocarTela("MENU_PRINCIPAL");
        } else if (fonte == janelaMenuMultiplayer.getBtnMultiplayerLocal()) {
            AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
            iniciarPartidaMultiplayer(12);
        } else if (fonte == janelaMenuMultiplayer.getBtnMultiplayerOnline()) {
            AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
            JOptionPane.showMessageDialog(janelaMenuPrincipal,
                    "O modo Multiplayer Online estará disponível em breve!",
                    "Em Desenvolvimento",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        // 4. CLIQUES DA JANELA CREDITOS
        else if (fonte == janelaCreditos.getBtnVoltar()) {
            AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
            trocarTela("MENU_PRINCIPAL");
        }

        // 5. CLIQUES DA JANELA DE VITÓRIA SINGLE (POPUP)
        if (janelaVitoriaSingle != null) {
            if (fonte == janelaVitoriaSingle.getBtnMenu()) {
                AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
                janelaVitoriaSingle.dispose();
                janelaVitoriaSingle = null;
                trocarTela("MENU_PRINCIPAL");
            } else if (fonte == janelaVitoriaSingle.getBtnJogarNovamente()) {
                AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
                janelaVitoriaSingle.dispose();
                janelaVitoriaSingle = null;
                if (painelJogoSingle != null) {
                    painelJogoSingle.reiniciarJogo();
                }
            }
        }

        // 6. CLIQUES DA JANELA DE VITÓRIA MULTIPLAYER (POPUP)
        if (janelaVitoriaMultiplayer != null) {
            if (fonte == janelaVitoriaMultiplayer.getBtnMenu()) {
                AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
                janelaVitoriaMultiplayer.dispose();
                janelaVitoriaMultiplayer = null;
                trocarTela("MENU_PRINCIPAL");
            } else if (fonte == janelaVitoriaMultiplayer.getBtnJogarNovamente()) {
                AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
                String nome1 = janelaVitoriaMultiplayer.getJogador1().getNome();
                String nome2 = janelaVitoriaMultiplayer.getJogador2().getNome();
                janelaVitoriaMultiplayer.dispose();
                janelaVitoriaMultiplayer = null;

                Tabuleiro novoTabuleiro = new Tabuleiro(12, true);
                Jogador j1 = new Jogador(nome1);
                Jogador j2 = new Jogador(nome2);

                painelJogoMulti = new JanelaMultiplayer(novoTabuleiro, j1, j2, this);
                painelContentor.add(painelJogoMulti, "JOGO_MULTIPLAYER");
                trocarTela("JOGO_MULTIPLAYER");
            }
        }
    }

    // --- MÉTODOS AUXILIARES ---
    private void trocarTela(String nomeCard) {
        cardLayout.show(painelContentor, nomeCard);
        painelContentor.revalidate();
        painelContentor.repaint();
    }

    private void iniciarPartidaSolo(int totalPares) {
        String nome = JOptionPane.showInputDialog(janelaMenuPrincipal,
                "Digite seu nome para começar:",
                "Identificação do Jogador",
                JOptionPane.QUESTION_MESSAGE);

        if (nome != null && !nome.trim().isEmpty()) {
            Tabuleiro tabuleiro = new Tabuleiro(totalPares, false);
            Jogador jogador = new Jogador(nome);

            painelJogoSingle = new JanelaSinglePlayer(tabuleiro, jogador, this);
            painelContentor.add(painelJogoSingle, "JOGO_SINGLEPLAYER");

            trocarTela("JOGO_SINGLEPLAYER");
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

                painelJogoMulti = new JanelaMultiplayer(tabuleiro, j1, j2, this);
                painelContentor.add(painelJogoMulti, "JOGO_MULTIPLAYER");

                trocarTela("JOGO_MULTIPLAYER");
            }
        }
    }

    public void solicitarVoltarAoMenu(Component telaAtual, Runnable acaoPausar, Runnable acaoRetomar) {
        if (acaoPausar != null) {
            acaoPausar.run();
        }

        int resposta = JOptionPane.showConfirmDialog(
                telaAtual,
                "Deseja realmente cancelar a partida e voltar ao menu?",
                "Voltar ao Menu",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (resposta == JOptionPane.YES_OPTION) {
            trocarTela("MENU_PRINCIPAL");
        } else {
            if (acaoRetomar != null) {
                acaoRetomar.run();
            }
        }
    }
}