package jogodamemoria.view;

import javax.swing.*;
import java.awt.*;

import jogodamemoria.model.Jogador;
import jogodamemoria.model.Tabuleiro;
import jogodamemoria.view.componentes.Cores;
import jogodamemoria.view.componentes.GerenciadorFontes;

public class JanelaVitoriaMultiplayer extends JanelaVitoriaBase {

    private final Jogador jogador1;
    private final Jogador jogador2;

    public JanelaVitoriaMultiplayer(JFrame janelaPai, Jogador vencedor, Jogador jogador1, Jogador jogador2, Tabuleiro tabuleiroAntigo) {
        super(janelaPai,
                vencedor != null ? vencedor.getNome().toUpperCase() + " VENCEU!" : "DEU EMPATE!",
                "Revanche",
                criarConteudoPlacar(vencedor, jogador1, jogador2),
                tabuleiroAntigo);
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
    }

    private static JComponent criarConteudoPlacar(Jogador vencedor, Jogador j1, Jogador j2) {
        JPanel painelPlacar = new JPanel();
        painelPlacar.setLayout(new BoxLayout(painelPlacar, BoxLayout.Y_AXIS));
        painelPlacar.setOpaque(false);

        boolean j1Venceu = (vencedor == j1);
        boolean j2Venceu = (vencedor == j2);

        // Garante que o Vencedor apareça sempre no primeiro card
        if (j2Venceu) {
            painelPlacar.add(criarCardJogador(j2.getNome(), j2.getPontuacao(), j2.getParesEncontrados(), true));
            painelPlacar.add(Box.createVerticalStrut(10));
            painelPlacar.add(criarCardJogador(j1.getNome(), j1.getPontuacao(), j1.getParesEncontrados(), false));
        } else {
            painelPlacar.add(criarCardJogador(j1.getNome(), j1.getPontuacao(), j1.getParesEncontrados(), j1Venceu));
            painelPlacar.add(Box.createVerticalStrut(10));
            painelPlacar.add(criarCardJogador(j2.getNome(), j2.getPontuacao(), j2.getParesEncontrados(), j2Venceu));
        }

        return painelPlacar;
    }

    private static JPanel criarCardJogador(String nome, int pontos, int pares, boolean eVencedor) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setMaximumSize(new Dimension(420, 50));
        card.setPreferredSize(new Dimension(420, 50));

        // Fundo e bordas baseados no resultado
        card.setBackground(Cores.VIDRO_FUNDO);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        Cores.VIDRO_BORDA,
                        eVencedor ? 2 : 1,
                        true),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)));

        // Esquerda: Nome do jogador
        JLabel lblNome = new JLabel(nome);
        lblNome.setFont(GerenciadorFontes.obterFonte(Font.BOLD, eVencedor ? 18f : 16f));
        lblNome.setForeground(eVencedor ? Cores.TEXTO_CIANO : Cores.TEXTO_BRANCO);

        // Direita: Pontuação e contagem de pares
        String textPontos = pontos == 1 ? "1 pt" : pontos + " pts";
        String textPares = pares == 1 ? "1 par" : pares + " pares";

        JLabel lblPlacar = new JLabel(textPontos + "  •  " + textPares);
        lblPlacar.setFont(GerenciadorFontes.obterFonte(Font.BOLD, 15f));
        lblPlacar.setForeground(Cores.TEXTO_BRANCO);

        card.add(lblNome, BorderLayout.WEST);
        card.add(lblPlacar, BorderLayout.EAST);

        return card;
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }
}