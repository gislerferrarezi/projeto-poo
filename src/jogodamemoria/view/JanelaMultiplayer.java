package jogodamemoria.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import jogodamemoria.controller.AudioController;
import jogodamemoria.controller.JogoController;
import jogodamemoria.controller.NavegacaoController;
import jogodamemoria.model.Jogador;
import jogodamemoria.model.Tabuleiro;
import jogodamemoria.view.componentes.Cores;
import jogodamemoria.view.componentes.PainelVidro;

public class JanelaMultiplayer extends JanelaJogoBase {

    private Jogador jogador1;
    private Jogador jogador2;

    private JLabel lblNomeJ1, lblPontuacaoJ1, lblTempoJ1;
    private JLabel lblNomeJ2, lblPontuacaoJ2, lblTempoJ2;

    public JanelaMultiplayer(Tabuleiro tabuleiro, Jogador jogador1, Jogador jogador2, NavegacaoController nav) {
        super(tabuleiro, nav);
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;

        if (tabuleiro != null) {
            this.gerenciador = new JogoController(tabuleiro, jogador1, jogador2);
        }

        inicializarInterface();
    }

    private void inicializarInterface() {
        // Topo HUD (Jogador 1)
        PainelVidro superior = new PainelVidro();
        superior.setLayout(new GridLayout(1, 3));
        superior.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        lblNomeJ1 = criarLabelHUD("Jogador 1: " + (jogador1 != null ? jogador1.getNome() : ""), JLabel.LEFT);
        lblPontuacaoJ1 = criarLabelHUD("Pares Feitos: 0", JLabel.CENTER);
        lblTempoJ1 = criarLabelHUD("Tempo: 30s", JLabel.RIGHT);

        superior.add(lblNomeJ1);
        superior.add(lblPontuacaoJ1);
        superior.add(lblTempoJ1);

        JPanel containerSuperior = new JPanel(new BorderLayout());
        containerSuperior.setOpaque(false);
        containerSuperior.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));
        containerSuperior.add(superior, BorderLayout.CENTER);
        add(containerSuperior, BorderLayout.NORTH);

        // Tabuleiro - Grid fixo de 8 colunas x 4 linhas para 32 cartas
        if (tabuleiro != null) {
            montarTabuleiro(8, 8, 8);
        }
        add(painelTabuleiro, BorderLayout.CENTER);

        // Base HUD (Jogador 2)
        PainelVidro inferior = new PainelVidro();
        inferior.setLayout(new GridLayout(1, 3));
        inferior.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        lblNomeJ2 = criarLabelHUD("Jogador 2: " + (jogador2 != null ? jogador2.getNome() : ""), JLabel.LEFT);
        lblPontuacaoJ2 = criarLabelHUD("Pares Feitos: 0", JLabel.CENTER);
        lblTempoJ2 = criarLabelHUD("Tempo: 30s", JLabel.RIGHT);

        inferior.add(lblNomeJ2);
        inferior.add(lblPontuacaoJ2);
        inferior.add(lblTempoJ2);

        JPanel containerInferior = new JPanel(new BorderLayout());
        containerInferior.setOpaque(false);
        containerInferior.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));
        containerInferior.add(inferior, BorderLayout.CENTER);
        add(containerInferior, BorderLayout.SOUTH);

        if (gerenciador != null) {
            configurarModoLocal();
        }
        configurarBotaoEsc();
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

    @Override
    protected void processarCliqueIndice(int i) {
        if (gerenciador == null)
            return;

        JogoController.ResultadoJogada resultado = gerenciador.processarCliqueCarta(i);

        switch (resultado) {
            case PRIMEIRA_CARTA_VIRADA:
                botoesCartas.get(i).repaint();
                break;

            case ACERTOU_PAR:
                AudioController.tocarEfeito("/jogodamemoria/recursos/sons/acerto.wav");
                botoesCartas.get(i).repaint();
                gerenciador.resetarCronometro();
                atualizarHUD();
                break;

            case ERROU_PAR:
                botoesCartas.get(i).repaint();
                tabuleiroBloqueado = true;

                Timer timer = new Timer(1500, evento -> {
                    gerenciador.finalizarTurnoErrado();
                    sincronizarCartasVisuais();
                    tabuleiroBloqueado = false;
                    gerenciador.resetarCronometro();
                    atualizarHUD();
                });
                timer.setRepeats(false);
                timer.start();
                break;

            case PERDEU_A_VEZ:
                botoesCartas.get(i).repaint();
                tabuleiroBloqueado = true;
                Timer timerPunicao = new Timer(600, evento -> {
                    JOptionPane.showMessageDialog(this, "Oops! Carta de Punição: Você perdeu a vez!", "Efeito Especial",
                            JOptionPane.ERROR_MESSAGE);
                    sincronizarCartasVisuais();
                    atualizarHUD();
                    tabuleiroBloqueado = false;
                    gerenciador.resetarCronometro();
                });
                timerPunicao.setRepeats(false);
                timerPunicao.start();
                break;

            case JOGUE_DE_NOVO_ATIVADO:
                botoesCartas.get(i).repaint();
                JOptionPane.showMessageDialog(this, "Boa! Carta Bônus: Jogue de novo!", "Efeito Especial",
                        JOptionPane.INFORMATION_MESSAGE);
                break;

            case DOBRO_PONTOS_ATIVADO:
                botoesCartas.get(i).repaint();
                JOptionPane.showMessageDialog(this, "Incrível! Carta de Pontuação Dobrada neste turno!",
                        "Efeito Especial", JOptionPane.INFORMATION_MESSAGE);
                break;

            case VITORIA:
                gerenciador.pararCronometro();
                botoesCartas.get(i).repaint();
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

    @Override
    protected void atualizarHUD() {
        if (jogador1 != null && jogador2 != null && gerenciador != null) {
            lblPontuacaoJ1.setText("Pares Feitos: " + jogador1.getPontuacao());
            lblPontuacaoJ2.setText("Pares Feitos: " + jogador2.getPontuacao());
            lblTempoJ1.setText("Tempo: " + gerenciador.getTempoRestanteJ1() + "s");
            lblTempoJ2.setText("Tempo: " + gerenciador.getTempoRestanteJ2() + "s");

            boolean p1Ativo = gerenciador.getJogadorAtual() == 0;

            lblNomeJ1.setForeground(p1Ativo ? Cores.TEXTO_CIANO : Color.GRAY);
            lblTempoJ1.setForeground(p1Ativo ? Color.WHITE : Color.GRAY);
            lblPontuacaoJ1.setForeground(p1Ativo ? Color.WHITE : Color.GRAY);

            lblNomeJ2.setForeground(!p1Ativo ? Cores.TEXTO_CIANO : Color.GRAY);
            lblTempoJ2.setForeground(!p1Ativo ? Color.WHITE : Color.GRAY);
            lblPontuacaoJ2.setForeground(!p1Ativo ? Color.WHITE : Color.GRAY);
        }
    }

    @Override
    protected void pausarJogo() {
        if (gerenciador != null)
            gerenciador.pararCronometro();
    }

    @Override
    protected void retomarJogo() {
        if (gerenciador != null)
            gerenciador.iniciarCronometro();
    }
}