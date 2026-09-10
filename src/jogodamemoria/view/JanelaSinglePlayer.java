package jogodamemoria.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import jogodamemoria.controller.AudioController;
import jogodamemoria.controller.JogoController;
import jogodamemoria.controller.JogoController.ResultadoJogada;
import jogodamemoria.controller.NavegacaoController;
import jogodamemoria.model.Jogador;
import jogodamemoria.model.Tabuleiro;
import jogodamemoria.view.componentes.Cores;
import jogodamemoria.view.componentes.PainelVidro;

public class JanelaSinglePlayer extends JanelaJogoBase {

    private Jogador jogador;

    private JLabel lblNome, lblPontos, lblTempo;
    private Timer cronometro;
    private int segundosDecorridos = 0;

    public JanelaSinglePlayer(Tabuleiro tabuleiro, Jogador jogador, NavegacaoController navegacaoController) {
        super(tabuleiro, navegacaoController);
        this.jogador = jogador;
        this.gerenciador = new JogoController(tabuleiro, jogador);

        inicializarInterface();
    }

    private int calcularColunasIdeais() {
        if (tabuleiro == null) return 4;
        // Se for 24 cartas usa 6 colunas (6x4), se for 12 usa 4 colunas (4x3)
        return (tabuleiro.getTamanho() > 12) ? 6 : 4;
    }

    private void inicializarInterface() {
        String textoTitulo = (tabuleiro.getTamanho() == 12) ? "UM JOGADOR - MODO FÁCIL" : "UM JOGADOR - MODO PADRÃO";
        int colunas = calcularColunasIdeais();

        // Topo HUD
        JLabel labelTitulo = new JLabel(textoTitulo, JLabel.CENTER);
        labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        labelTitulo.setForeground(Cores.TEXTO_CIANO);

        PainelVidro superior = new PainelVidro();
        superior.setLayout(new BorderLayout());
        superior.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        superior.add(labelTitulo, BorderLayout.CENTER);

        JPanel containerSuperior = new JPanel(new BorderLayout());
        containerSuperior.setOpaque(false);
        containerSuperior.setBorder(BorderFactory.createEmptyBorder(15, 20, 0, 20));
        containerSuperior.add(superior, BorderLayout.CENTER);
        add(containerSuperior, BorderLayout.NORTH);

        // Tabuleiro ajustado com gaps proporcionais
        montarTabuleiro(colunas, 10, 10);
        add(painelTabuleiro, BorderLayout.CENTER);

        // Base HUD
        lblNome = criarLabelHUD("Jogador: " + jogador.getNome(), JLabel.CENTER);
        lblNome.setForeground(Cores.TEXTO_CIANO);
        lblPontos = criarLabelHUD("Pares Feitos: 0", JLabel.CENTER);
        lblTempo = criarLabelHUD("Tempo: 00:00", JLabel.CENTER);

        PainelVidro inferior = new PainelVidro();
        inferior.setLayout(new GridLayout(1, 3));
        inferior.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        inferior.add(lblNome);
        inferior.add(lblPontos);
        inferior.add(lblTempo);

        JPanel containerInferior = new JPanel(new BorderLayout());
        containerInferior.setOpaque(false);
        containerInferior.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));
        containerInferior.add(inferior, BorderLayout.CENTER);
        add(containerInferior, BorderLayout.SOUTH);

        // Cronômetro do SinglePlayer
        cronometro = new Timer(1000, e -> {
            segundosDecorridos++;
            int min = segundosDecorridos / 60;
            int seg = segundosDecorridos % 60;
            lblTempo.setText(String.format("Tempo: %02d:%02d", min, seg));
        });
        cronometro.start();

        configurarBotaoEsc();
    }

    public void reiniciarJogo() {
        this.tabuleiro = new Tabuleiro(this.tabuleiro.getTamanho() / 2, false);
        this.jogador.resetarPontos();
        this.gerenciador = new JogoController(this.tabuleiro, this.jogador);
        this.tabuleiroBloqueado = false;

        if (cronometro != null) {
            cronometro.stop();
        }
        segundosDecorridos = 0;
        lblTempo.setText("Tempo: 00:00");
        lblPontos.setText("Pares Feitos: 0");

        int colunas = calcularColunasIdeais();
        montarTabuleiro(colunas, 10, 10);

        if (cronometro != null) {
            cronometro.start();
        }
    }

    @Override
    protected void processarCliqueIndice(int i) {
        ResultadoJogada resultado = gerenciador.processarCliqueCarta(i);

        switch (resultado) {
            case PRIMEIRA_CARTA_VIRADA:
                botoesCartas.get(i).repaint();
                break;

            case ACERTOU_PAR:
                AudioController.tocarEfeito("/jogodamemoria/recursos/sons/acerto.wav");
                botoesCartas.get(i).repaint();
                atualizarHUD();
                break;

            case ERROU_PAR:
                botoesCartas.get(i).repaint();
                tabuleiroBloqueado = true;

                Timer timer = new Timer(1500, evento -> {
                    gerenciador.finalizarTurnoErrado();
                    sincronizarCartasVisuais();
                    tabuleiroBloqueado = false;
                });
                timer.setRepeats(false);
                timer.start();
                break;

            case VITORIA:
                botoesCartas.get(i).repaint();
                atualizarHUD();
                if (cronometro != null) {
                    cronometro.stop();
                }
                AudioController.tocarEfeito("/jogodamemoria/recursos/sons/vitoria.wav");
                navegacaoController.exibirVitoria(this, gerenciador.getTentativas(), lblTempo.getText(), jogador,
                        tabuleiro);
                break;

            default:
                break;
        }
    }

    @Override
    protected void atualizarHUD() {
        lblPontos.setText("Pares Feitos: " + jogador.getPontuacao());
    }

    @Override
    protected void pausarJogo() {
        if (cronometro != null)
            cronometro.stop();
    }

    @Override
    protected void retomarJogo() {
        if (cronometro != null)
            cronometro.start();
    }
}