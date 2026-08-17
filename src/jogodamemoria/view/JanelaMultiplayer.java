package jogodamemoria.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.*;
import jogodamemoria.model.Tabuleiro;
import jogodamemoria.model.Carta;
import jogodamemoria.model.Jogador;
import jogodamemoria.controller.AudioController;
import jogodamemoria.controller.JogoController;
import jogodamemoria.controller.NavegacaoController;

public class JanelaMultiplayer extends JPanel implements ActionListener {

    private Tabuleiro tabuleiro;
    private Jogador jogador1;
    private Jogador jogador2;

    private JogoController gerenciador;
    private NavegacaoController navegacaoController;
    private boolean tabuleiroBloqueado = false;

    private JPanel painelJogador1;
    private JPanel painelJogador2;
    private JLabel lblPontuacaoJ1;
    private JLabel lblPontuacaoJ2;
    private JLabel lblTempoJ1;
    private JLabel lblTempoJ2;
    private JPanel painelTabuleiro;
    private ArrayList<JButton> botoesCartas = new ArrayList<>();

    public JanelaMultiplayer(Tabuleiro tabuleiro, Jogador jogador1, Jogador jogador2,
            NavegacaoController navegacaoController) {
        this.tabuleiro = tabuleiro;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.navegacaoController = navegacaoController;

        this.gerenciador = new JogoController(tabuleiro, jogador1, jogador2);

        // 2. ALTERAÇÃO AQUI: Métodos setTitle, setSize, setDefaultCloseOperation e
        // setLocationRelativeTo removidos!

        setLayout(new BorderLayout(10, 10));

        // Configuração de linhas e colunas
        int lines = 5;
        int colunas = 6;

        // --- NORTE - Jogador 1 ---
        painelJogador1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblNomeJ1 = new JLabel("Jogador 1: " + jogador1.getNome() + "  |  ");
        lblPontuacaoJ1 = new JLabel("Pontos: " + jogador1.getPontuacao() + "  |  ");
        lblTempoJ1 = new JLabel("Tempo: 30s");

        lblNomeJ1.setFont(new Font("Arial", Font.BOLD, 18));
        lblPontuacaoJ1.setFont(new Font("Arial", Font.BOLD, 18));
        lblTempoJ1.setFont(new Font("Arial", Font.BOLD, 18));
        lblTempoJ1.setForeground(Color.RED);

        painelJogador1.add(lblNomeJ1);
        painelJogador1.add(lblPontuacaoJ1);
        painelJogador1.add(lblTempoJ1);

        add(painelJogador1, BorderLayout.NORTH);

        // --- CENTRO - Tabuleiro ---
        painelTabuleiro = new JPanel(new GridLayout(lines, colunas, 15, 15));

        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            JButton botao = new JButton("[ ? ]");
            botao.setFont(new Font("Arial", Font.BOLD, 24));

            botoesCartas.add(botao);
            painelTabuleiro.add(botao);
            botao.addActionListener(this);
        }

        add(painelTabuleiro, BorderLayout.CENTER);

        // --- SUL - Jogador 2 ---
        painelJogador2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblNomeJ2 = new JLabel("Jogador 2: " + jogador2.getNome() + "  |  ");
        lblPontuacaoJ2 = new JLabel("Pontos: " + jogador2.getPontuacao() + "  |  ");
        lblTempoJ2 = new JLabel("Tempo: 30s");

        lblNomeJ2.setFont(new Font("Arial", Font.BOLD, 18));
        lblPontuacaoJ2.setFont(new Font("Arial", Font.BOLD, 18));
        lblTempoJ2.setFont(new Font("Arial", Font.BOLD, 18));
        lblTempoJ2.setForeground(Color.RED);

        painelJogador2.add(lblNomeJ2);
        painelJogador2.add(lblPontuacaoJ2);
        painelJogador2.add(lblTempoJ2);

        add(painelJogador2, BorderLayout.SOUTH);

        gerenciador.configurarCallbacksCronometro(
                () -> SwingUtilities.invokeLater(() -> {
                    lblTempoJ1.setText("Tempo: " + gerenciador.getTempoRestanteJ1() + "s");
                    lblTempoJ2.setText("Tempo: " + gerenciador.getTempoRestanteJ2() + "s");
                }),
                () -> SwingUtilities.invokeLater(() -> {
                    // Força imediatamente o texto para 0s na interface antes de travar o tabuleiro
                    lblTempoJ1.setText("Tempo: 0s");
                    lblTempoJ2.setText("Tempo: 0s");

                    tabuleiroBloqueado = true;

                    // Um pequeno atraso (200ms) para o painel atualizar visualmente e mostrar o
                    // "0s" para o usuário
                    Timer delayVisual = new Timer(200, evento -> {
                        JOptionPane.showMessageDialog(JanelaMultiplayer.this,
                                "Tempo esgotado! Sua vez passou.",
                                "Atenção",
                                JOptionPane.WARNING_MESSAGE);

                        sincronizarCartasVisuais();
                        atualizarHUD();
                        tabuleiroBloqueado = false;
                        gerenciador.iniciarCronometro();
                    });
                    delayVisual.setRepeats(false);
                    delayVisual.start();
                }));
        atualizarHUD();
        gerenciador.iniciarCronometro();

        configurarBotaoEsc();
    }

    private void atualizarHUD() {
        lblPontuacaoJ1.setText("Pontos: " + jogador1.getPontuacao());
        lblPontuacaoJ2.setText("Pontos: " + jogador2.getPontuacao());
        lblTempoJ1.setText("Tempo: " + gerenciador.getTempoRestanteJ1() + "s");
        lblTempoJ2.setText("Tempo: " + gerenciador.getTempoRestanteJ2() + "s");

        if (gerenciador.getJogadorAtual() == 0) {
            painelJogador1.setBackground(new Color(173, 216, 230)); // Azul Claro para J1
            painelJogador2.setBackground(null);
        } else {
            painelJogador1.setBackground(null);
            painelJogador2.setBackground(new Color(255, 182, 193)); // Rosa Claro para J2
        }
    }

    private void sincronizarCartasVisuais() {
        for (int i = 0; i < botoesCartas.size(); i++) {
            Carta carta = tabuleiro.getCarta(i);
            if (carta.isDescoberta() || carta.isVirada()) {
                botoesCartas.get(i).setText(carta.getValor());
            } else {
                botoesCartas.get(i).setText("[ ? ]");
            }
        }
    }

    private void configurarBotaoEsc() {
        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "acaoEsc");

        actionMap.put("acaoEsc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navegacaoController.solicitarVoltarAoMenu(
                        JanelaMultiplayer.this,
                        () -> gerenciador.pararCronometro(), // Como pausar
                        () -> gerenciador.iniciarCronometro() // Como retomar
                );
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (tabuleiroBloqueado)
            return;

        for (int i = 0; i < botoesCartas.size(); i++) {
            if (e.getSource() == botoesCartas.get(i)) {

                JogoController.ResultadoJogada resultado = gerenciador.processarCliqueCarta(i);

                switch (resultado) {
                    case PRIMEIRA_CARTA_VIRADA:
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                        break;

                    case ACERTOU_PAR:
                        AudioController.tocarEfeito("/jogodamemoria/recursos/sons/acerto.wav");
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                        gerenciador.resetarCronometro();
                        atualizarHUD();
                        break;

                    case ERROU_PAR:
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                        tabuleiroBloqueado = true;

                        Timer timer = new Timer(1000, evento -> {
                            sincronizarCartasVisuais();
                            tabuleiroBloqueado = false;
                            gerenciador.resetarCronometro();
                            atualizarHUD();
                        });
                        timer.setRepeats(false);
                        timer.start();
                        break;

                    case PERDEU_A_VEZ:
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                        tabuleiroBloqueado = true;

                        JOptionPane.showMessageDialog(this,
                                "Oops! Carta de Punição: Você perdeu a vez!",
                                "Efeito Especial", JOptionPane.ERROR_MESSAGE);

                        sincronizarCartasVisuais();
                        atualizarHUD();
                        tabuleiroBloqueado = false;
                        gerenciador.resetarCronometro();
                        break;

                    case JOGUE_DE_NOVO_ATIVADO:
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                        tabuleiroBloqueado = true;

                        JOptionPane.showMessageDialog(this,
                                "Boa! Carta Bônus: Jogue de novo!",
                                "Efeito Especial", JOptionPane.INFORMATION_MESSAGE);

                        tabuleiroBloqueado = false;
                        // Mantém o turno e o cronômetro rodando para o mesmo jogador
                        break;

                    case DOBRO_PONTOS_ATIVADO:
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                        tabuleiroBloqueado = true;

                        JOptionPane.showMessageDialog(this,
                                "Incrível! Carta de Pontuação Dobrada neste turno!",
                                "Efeito Especial", JOptionPane.INFORMATION_MESSAGE);

                        tabuleiroBloqueado = false;
                        break;

                    case VITORIA:
                        gerenciador.pararCronometro();
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                        atualizarHUD();
                        AudioController.tocarEfeito("/jogodamemoria/recursos/sons/vitoria.wav");
                        Jogador vencedor = gerenciador.compararPontos(jogador1, jogador2);
                        navegacaoController.exibirVitoriaMultiplayer(this, vencedor, jogador1, jogador2);
                        break;

                    case IGNORAR:
                    default:
                        break;
                }
                break;
            }
        }
    }
}