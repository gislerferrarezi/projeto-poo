package jogodamemoria.view;

import javax.swing.*;
import java.awt.*;

import jogodamemoria.model.Jogador;
import jogodamemoria.model.Tabuleiro;
import jogodamemoria.view.componentes.Cores;

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
        card.setBackground(eVencedor ? Cores.CARD_VENCEDOR_FUNDO : Cores.CARD_PERDEDOR_FUNDO);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        eVencedor ? Cores.CARD_VENCEDOR_BORDA : Cores.CARD_PERDEDOR_BORDA,
                        eVencedor ? 2 : 1,
                        true),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)));

        // Esquerda: Nome do jogador
        JLabel lblNome = new JLabel(nome);
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, eVencedor ? 18 : 16));
        lblNome.setForeground(eVencedor ? Cores.CARD_VENCEDOR_TEXTO_NOME : Cores.CARD_PERDEDOR_TEXTO_NOME);

        // Direita: Pontuação e contagem de pares
        String textPontos = pontos == 1 ? "1 pt" : pontos + " pts";
        String textPares = pares == 1 ? "1 par" : pares + " pares";

        JLabel lblPlacar = new JLabel(textPontos + "  •  " + textPares);
        lblPlacar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblPlacar.setForeground(eVencedor ? Cores.CARD_VENCEDOR_TEXTO_PLACAR : Cores.CARD_PERDEDOR_TEXTO_PLACAR);

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