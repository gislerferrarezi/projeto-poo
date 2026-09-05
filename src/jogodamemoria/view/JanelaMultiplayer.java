package jogodamemoria.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import jogodamemoria.controller.*;
import jogodamemoria.model.*;

public class JanelaMultiplayer extends JPanel implements ActionListener {

    private Tabuleiro tabuleiro;
    private Jogador jogador1;
    private Jogador jogador2;
    private JogoController gerenciador;
    private NavegacaoController navegacaoController;
    private boolean tabuleiroBloqueado = false;

    // Componentes de Tela
    private JPanel painelJogador1, painelJogador2, painelTabuleiro;
    private JLabel lblNomeJ1, lblNomeJ2, lblPontuacaoJ1, lblPontuacaoJ2;
    private JLabel lblTempoJ1, lblTempoJ2;
    private ArrayList<JButton> botoesCartas = new ArrayList<>();

    public JanelaMultiplayer(Tabuleiro tabuleiro, Jogador jogador1, Jogador jogador2, NavegacaoController nav) {
        this.tabuleiro = tabuleiro;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.navegacaoController = nav;

        if (tabuleiro != null) {
            this.gerenciador = new JogoController(tabuleiro, jogador1, jogador2);
        }

        setLayout(new BorderLayout(10, 10));

        //  NORTE - Jogador 1 
        painelJogador1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblNomeJ1 = criarLabel("Jogador 1: " + (jogador1 != null ? jogador1.getNome() : "") + "  |  ", 18, Font.BOLD,
                Color.BLACK);
        lblPontuacaoJ1 = criarLabel("Pontos: 0  |  ", 18, Font.BOLD, Color.BLACK);
        lblTempoJ1 = criarLabel("Tempo: 30s", 18, Font.BOLD, Color.RED);
        painelJogador1.add(lblNomeJ1);
        painelJogador1.add(lblPontuacaoJ1);
        painelJogador1.add(lblTempoJ1);
        add(painelJogador1, BorderLayout.NORTH);

        // CENTRO - Tabuleiro 
        painelTabuleiro = new JPanel();
        if (tabuleiro != null)
            montarTabuleiro(tabuleiro.getTamanho());
        add(painelTabuleiro, BorderLayout.CENTER);

        // SUL - Jogador 2 
        painelJogador2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblNomeJ2 = criarLabel("Jogador 2: " + (jogador2 != null ? jogador2.getNome() : "") + "  |  ", 18, Font.BOLD,
                Color.BLACK);
        lblPontuacaoJ2 = criarLabel("Pontos: 0  |  ", 18, Font.BOLD, Color.BLACK);
        lblTempoJ2 = criarLabel("Tempo: 30s", 18, Font.BOLD, Color.RED);
        painelJogador2.add(lblNomeJ2);
        painelJogador2.add(lblPontuacaoJ2);
        painelJogador2.add(lblTempoJ2);
        add(painelJogador2, BorderLayout.SOUTH);

        if (gerenciador != null)
            configurarModoLocal();
        configurarBotaoEsc();
    }

    private JLabel criarLabel(String txt, int tamanho, int estilo, Color cor) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Arial", estilo, tamanho));
        l.setForeground(cor);
        return l;
    }

    private void montarTabuleiro(int totalCartas) {
        painelTabuleiro.removeAll();
        botoesCartas.clear();

        int colunas = 6;
        int linhas = (int) Math.ceil((double) totalCartas / colunas);
        painelTabuleiro.setLayout(new GridLayout(linhas, colunas, 10, 10));

        for (int i = 0; i < totalCartas; i++) {
            JButton botao = new JButton("[ ? ]");
            botao.setFont(new Font("Arial", Font.BOLD, 22));
            botao.setFocusPainted(false);
            botoesCartas.add(botao);
            painelTabuleiro.add(botao);
            botao.addActionListener(this);
        }
        painelTabuleiro.revalidate();
        painelTabuleiro.repaint();
    }

    private void configurarModoLocal() {
        gerenciador.configurarCallbacksCronometro(
                () -> SwingUtilities.invokeLater(() -> {
                    lblTempoJ1.setText("Tempo: " + gerenciador.getTempoRestanteJ1() + "s");
                    lblTempoJ2.setText("Tempo: " + gerenciador.getTempoRestanteJ2() + "s");
                }),
                () -> SwingUtilities.invokeLater(() -> {
                    lblTempoJ1.setText("Tempo: 0s");
                    lblTempoJ2.setText("Tempo: 0s");
                    tabuleiroBloqueado = true;

                    Timer delayVisual = new Timer(200, evento -> {
                        JOptionPane.showMessageDialog(this, "Tempo esgotado! Sua vez passou.", "Atenção",
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
    }

    private void atualizarHUD() {
        if (jogador1 != null && jogador2 != null && gerenciador != null) {
            lblPontuacaoJ1.setText("Pontos: " + jogador1.getPontuacao() + "  |  ");
            lblPontuacaoJ2.setText("Pontos: " + jogador2.getPontuacao() + "  |  ");
            lblTempoJ1.setText("Tempo: " + gerenciador.getTempoRestanteJ1() + "s");
            lblTempoJ2.setText("Tempo: " + gerenciador.getTempoRestanteJ2() + "s");

            painelJogador1.setBackground(gerenciador.getJogadorAtual() == 0 ? new Color(173, 216, 230) : null);
            painelJogador2.setBackground(gerenciador.getJogadorAtual() != 0 ? new Color(255, 182, 193) : null);
        }
    }

    private void sincronizarCartasVisuais() {
        if (tabuleiro == null)
            return;
        for (int i = 0; i < botoesCartas.size(); i++) {
            Carta carta = tabuleiro.getCarta(i);
            botoesCartas.get(i).setText((carta.isDescoberta() || carta.isVirada()) ? carta.getValor() : "[ ? ]");
        }
    }

    private void configurarBotaoEsc() {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "acaoEsc");
        getActionMap().put("acaoEsc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (navegacaoController != null) {
                    navegacaoController.solicitarVoltarAoMenu(JanelaMultiplayer.this,
                            () -> {
                                if (gerenciador != null)
                                    gerenciador.pararCronometro();
                            },
                            () -> {
                                if (gerenciador != null)
                                    gerenciador.iniciarCronometro();
                            });
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (tabuleiroBloqueado)
            return;

        for (int i = 0; i < botoesCartas.size(); i++) {
            if (e.getSource() == botoesCartas.get(i)) {

                // Ignora o clique se a carta já estiver virada/revelada
                if (!botoesCartas.get(i).getText().equals("[ ? ]"))
                    return;

                if (gerenciador != null) {
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
                            JOptionPane.showMessageDialog(this, "Oops! Carta de Punição: Você perdeu a vez!",
                                    "Efeito Especial", JOptionPane.ERROR_MESSAGE);
                            sincronizarCartasVisuais();
                            atualizarHUD();
                            tabuleiroBloqueado = false;
                            gerenciador.resetarCronometro();
                            break;

                        case JOGUE_DE_NOVO_ATIVADO:
                            botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                            JOptionPane.showMessageDialog(this, "Boa! Carta Bônus: Jogue de novo!", "Efeito Especial",
                                    JOptionPane.INFORMATION_MESSAGE);
                            break;

                        case DOBRO_PONTOS_ATIVADO:
                            botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                            JOptionPane.showMessageDialog(this, "Incrível! Carta de Pontuação Dobrada neste turno!",
                                    "Efeito Especial", JOptionPane.INFORMATION_MESSAGE);
                            break;

                        case VITORIA:
                            gerenciador.pararCronometro();
                            botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                            atualizarHUD();
                            AudioController.tocarEfeito("/jogodamemoria/recursos/sons/vitoria.wav");
                            Jogador vencedor = gerenciador.compararPontos(jogador1, jogador2);
                            navegacaoController.exibirVitoriaMultiplayer(this, vencedor, jogador1, jogador2,
                                    jogador1.getPontuacao(), jogador2.getPontuacao(), tabuleiro);
                            break;

                        default:
                            break;
                    }
                }
                break;
            }
        }
    }
}