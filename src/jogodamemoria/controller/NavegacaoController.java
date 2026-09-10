package jogodamemoria.controller;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import jogodamemoria.model.Jogador;
import jogodamemoria.model.Tabuleiro;
import jogodamemoria.view.JanelaCreditos;
import jogodamemoria.view.JanelaMenuMultiplayer;
import jogodamemoria.view.JanelaMenuPrincipal;
import jogodamemoria.view.JanelaMenuSinglePlayer;
import jogodamemoria.view.JanelaSinglePlayer;
import jogodamemoria.view.JanelaVitoriaSingle;
import jogodamemoria.view.componentes.CaixasDeDialogo;
import jogodamemoria.view.JanelaMultiplayer;
import jogodamemoria.view.JanelaVitoriaMultiplayer;

public class NavegacaoController implements ActionListener {

    private JanelaMenuPrincipal janelaMenuPrincipal;
    private JanelaMenuSinglePlayer janelaMenuSinglePlayer;
    private JanelaMenuMultiplayer janelaMenuMultiplayer;
    private JanelaCreditos janelaCreditos;

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

        this.painelContentor.add(painelInicio, "MENU_PRINCIPAL");
        this.painelContentor.add(janelaMenuSinglePlayer, "MENU_SINGLEPLAYER");
        this.painelContentor.add(janelaMenuMultiplayer, "MENU_MULTIPLAYER");
        this.painelContentor.add(janelaCreditos, "CREDITOS");

        menuPrincipal.setContentPane(painelContentor);
        cardLayout.show(painelContentor, "MENU_PRINCIPAL");

        this.janelaMenuPrincipal.getBtnUmJogador().addActionListener(this);
        this.janelaMenuPrincipal.getBtnDoisJogadores().addActionListener(this);
        this.janelaMenuPrincipal.getBtnCreditos().addActionListener(this);
        this.janelaMenuPrincipal.getBtnSair().addActionListener(this);
        this.janelaMenuPrincipal.getBtnSom().addActionListener(this);

        this.janelaMenuSinglePlayer.getBtnVoltar().addActionListener(this);
        this.janelaMenuSinglePlayer.getBtnJogarFacil().addActionListener(this);
        this.janelaMenuSinglePlayer.getBtnJogarPadrao().addActionListener(this);

        this.janelaMenuMultiplayer.getBtnVoltar().addActionListener(this);
        this.janelaMenuMultiplayer.getBtnMultiplayerLocal().addActionListener(this);

        this.janelaCreditos.getBtnVoltar().addActionListener(this);
    }

    public void iniciar() {
        AudioController.tocarMusicaFundo("/jogodamemoria/recursos/sons/musica_fundo.wav");
        janelaMenuPrincipal.setVisible(true);
    }

    public void exibirVitoria(JanelaSinglePlayer janelaJogo, int tentativas, String tempo, Jogador jogador,
            Tabuleiro tabuleiro) {
        this.janelaVitoriaSingle = new JanelaVitoriaSingle(janelaMenuPrincipal, tentativas, tempo, jogador, tabuleiro);
        this.janelaVitoriaSingle.getBtnMenu().addActionListener(this);
        this.janelaVitoriaSingle.getBtnJogarNovamente().addActionListener(this);
        this.janelaVitoriaSingle.setVisible(true);
    }

    public void exibirVitoriaMultiplayer(JanelaMultiplayer janelaJogo, Jogador vencedor, Jogador jogador1,
            Jogador jogador2, int paresJogador1, int paresJogador2, Tabuleiro tabuleiro) {

        this.painelJogoMulti = janelaJogo;
        this.janelaVitoriaMultiplayer = new JanelaVitoriaMultiplayer(
                janelaMenuPrincipal, vencedor, jogador1, jogador2, tabuleiro);

        this.janelaVitoriaMultiplayer.getBtnMenu().addActionListener(this);
        this.janelaVitoriaMultiplayer.getBtnJogarNovamente().addActionListener(this);
        this.janelaVitoriaMultiplayer.setVisible(true);
    }

    public void voltarAoMenuPrincipal() {
        trocarTela("MENU_PRINCIPAL");
    }

    public void solicitarVoltarAoMenu(Component telaAtual, Runnable acaoPausar, Runnable acaoRetomar) {
        if (acaoPausar != null)
            acaoPausar.run();

        boolean resposta = CaixasDeDialogo.confirmarAcao(
                telaAtual,
                "Deseja realmente cancelar a partida?",
                "Voltar ao Menu");

        if (resposta) {
            trocarTela("MENU_PRINCIPAL");
        } else if (acaoRetomar != null) {
            acaoRetomar.run();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object fonte = e.getSource();

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
        } else if (fonte == janelaMenuPrincipal.getBtnSom()) {
            AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
            boolean novoEstado = !AudioController.isSomAtivado();
            AudioController.setSomAtivado(novoEstado);
            janelaMenuPrincipal.atualizarIconeSomBotao(novoEstado);
        }

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

        else if (fonte == janelaMenuMultiplayer.getBtnVoltar()) {
            AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
            trocarTela("MENU_PRINCIPAL");
        } else if (fonte == janelaMenuMultiplayer.getBtnMultiplayerLocal()) {
            AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
            iniciarPartidaMultiplayer(12);
        }

        else if (fonte == janelaCreditos.getBtnVoltar()) {
            AudioController.tocarEfeito("/jogodamemoria/recursos/sons/navegacao.wav");
            trocarTela("MENU_PRINCIPAL");
        }

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

                if (painelJogoMulti != null) {
                    painelContentor.remove(painelJogoMulti);
                }

                Tabuleiro novoTabuleiro = new Tabuleiro(12, true);
                Jogador j1 = new Jogador(nome1);
                Jogador j2 = new Jogador(nome2);

                painelJogoMulti = new JanelaMultiplayer(novoTabuleiro, j1, j2, this);
                painelContentor.add(painelJogoMulti, "JOGO_MULTIPLAYER");
                trocarTela("JOGO_MULTIPLAYER");
            }
        }
    }

    private void trocarTela(String nomeCard) {
        cardLayout.show(painelContentor, nomeCard);
        painelContentor.revalidate();
        painelContentor.repaint();
    }

    private void iniciarPartidaSolo(int totalPares) {
        String nome = CaixasDeDialogo.pedirNomeSingle(janelaMenuPrincipal);

        if (nome != null && !nome.trim().isEmpty()) {
            if (painelJogoSingle != null) {
                painelContentor.remove(painelJogoSingle);
            }

            Tabuleiro tabuleiro = new Tabuleiro(totalPares, false);
            Jogador jogador = new Jogador(nome);

            painelJogoSingle = new JanelaSinglePlayer(tabuleiro, jogador, this);
            painelContentor.add(painelJogoSingle, "JOGO_SINGLEPLAYER");

            trocarTela("JOGO_SINGLEPLAYER");
        }
    }

    private void iniciarPartidaMultiplayer(int totalPares) {
        String[] nomes = CaixasDeDialogo.pedirNomesMulti(janelaMenuPrincipal);

        if (nomes != null && nomes.length == 2) {
            String nome1 = nomes[0];
            String nome2 = nomes[1];

            if (nome1 != null && !nome1.trim().isEmpty() && nome2 != null && !nome2.trim().isEmpty()) {
                if (painelJogoMulti != null) {
                    painelContentor.remove(painelJogoMulti);
                }

                Tabuleiro tabuleiro = new Tabuleiro(totalPares, true);
                Jogador j1 = new Jogador(nome1);
                Jogador j2 = new Jogador(nome2);

                painelJogoMulti = new JanelaMultiplayer(tabuleiro, j1, j2, this);
                painelContentor.add(painelJogoMulti, "JOGO_MULTIPLAYER");

                trocarTela("JOGO_MULTIPLAYER");
            }
        }
    }
}