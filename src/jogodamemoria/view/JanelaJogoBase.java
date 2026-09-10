package jogodamemoria.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import jogodamemoria.controller.JogoController;
import jogodamemoria.controller.NavegacaoController;
import jogodamemoria.model.Carta;
import jogodamemoria.model.Tabuleiro;
import jogodamemoria.view.componentes.BotaoCarta;

public abstract class JanelaJogoBase extends JPanel implements ActionListener {

    protected Tabuleiro tabuleiro;
    protected JogoController gerenciador;
    protected NavegacaoController navegacaoController;
    protected boolean tabuleiroBloqueado = false;

    protected JPanel painelTabuleiro;
    protected List<BotaoCarta> botoesCartas = new ArrayList<>();
    protected final Font fonteHUD = new Font("Segoe UI", Font.BOLD, 16);

    public JanelaJogoBase(Tabuleiro tabuleiro, NavegacaoController navegacaoController) {
        this.tabuleiro = tabuleiro;
        this.navegacaoController = navegacaoController;

        setLayout(new BorderLayout(0, 5));
        setBackground(new Color(8, 14, 28));

        painelTabuleiro = new JPanel();
        painelTabuleiro.setOpaque(false);
    }

    protected JLabel criarLabelHUD(String texto, int alinhamento) {
        JLabel label = new JLabel(texto, alinhamento);
        label.setFont(fonteHUD);
        label.setForeground(Color.WHITE);
        return label;
    }

    protected void montarTabuleiro(int colunas, int gapH, int gapV) {
        painelTabuleiro.removeAll();
        botoesCartas.clear();

        int totalCartas = tabuleiro.getTamanho();
        int linhas = (int) Math.ceil((double) totalCartas / colunas);

        painelTabuleiro.setLayout(new GridLayout(linhas, colunas, gapH, gapV));
        painelTabuleiro.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        for (int i = 0; i < totalCartas; i++) {
            BotaoCarta botao = new BotaoCarta(tabuleiro.getCarta(i));
            botoesCartas.add(botao);
            painelTabuleiro.add(botao);
            botao.addActionListener(this);
        }

        painelTabuleiro.revalidate();
        painelTabuleiro.repaint();
    }

    protected void sincronizarCartasVisuais() {
        if (tabuleiro == null)
            return;
        for (BotaoCarta botao : botoesCartas) {
            botao.repaint();
        }
    }

    protected void configurarBotaoEsc() {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "acaoEsc");
        getActionMap().put("acaoEsc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (navegacaoController != null) {
                    navegacaoController.solicitarVoltarAoMenu(JanelaJogoBase.this,
                            () -> pausarJogo(),
                            () -> retomarJogo());
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
                Carta cartaClicada = tabuleiro.getCarta(i);
                if (cartaClicada.isDescoberta() || cartaClicada.isVirada()) {
                    return;
                }
                processarCliqueIndice(i);
                break;
            }
        }
    }

    // Métodos Abstratos que as filhas devem implementar
    protected abstract void processarCliqueIndice(int index);

    protected abstract void pausarJogo();

    protected abstract void retomarJogo();

    protected abstract void atualizarHUD();
}
