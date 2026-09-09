package jogodamemoria.view;

import javax.swing.*;
import java.awt.*;

import jogodamemoria.model.Jogador;
import jogodamemoria.model.Tabuleiro;
import jogodamemoria.view.componentes.Cores;
import jogodamemoria.view.componentes.GerenciadorFontes;

public class JanelaVitoriaSingle extends JanelaVitoriaBase {

    private final Jogador jogador;

    public JanelaVitoriaSingle(JFrame janelaPai, int tentativas, String tempoFinal,
            Jogador jogador, Tabuleiro tabuleiroAntigo) {
        super(janelaPai, "VOCÊ VENCEU!", "Jogar de Novo",
                criarConteudoEstatisticas(tentativas, tempoFinal), tabuleiroAntigo);
        this.jogador = jogador;
    }

    private static JComponent criarConteudoEstatisticas(int tentativas, String tempoFinal) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);

        // Card Único de Destaque para estatísticas do modo Singleplayer
        JPanel cardEstatisticas = new JPanel(new GridLayout(1, 2, 20, 0));
        cardEstatisticas.setMaximumSize(new Dimension(420, 50));
        cardEstatisticas.setPreferredSize(new Dimension(420, 50));
        cardEstatisticas.setBackground(Cores.VIDRO_FUNDO);
        cardEstatisticas.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Cores.VIDRO_BORDA, 2, true),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)));

        // Bloco 1: Tentativas
        JLabel lblTentativas = new JLabel("TENTATIVAS: " + tentativas, SwingConstants.CENTER);
        lblTentativas.setFont(GerenciadorFontes.obterFonte(Font.BOLD, 15f));
        lblTentativas.setForeground(Cores.TEXTO_CIANO);

        // Bloco 2: Tempo
        String textoTempo = tempoFinal.toUpperCase().startsWith("TEMPO") ? tempoFinal.toUpperCase()
                : "TEMPO: " + tempoFinal.toUpperCase();
        JLabel lblTempo = new JLabel(textoTempo, SwingConstants.CENTER);
        lblTempo.setFont(GerenciadorFontes.obterFonte(Font.BOLD, 15f));
        lblTempo.setForeground(Cores.TEXTO_BRANCO);

        cardEstatisticas.add(lblTentativas);
        cardEstatisticas.add(lblTempo);

        container.add(cardEstatisticas);
        return container;
    }

    public Jogador getJogador() {
        return jogador;
    }
}