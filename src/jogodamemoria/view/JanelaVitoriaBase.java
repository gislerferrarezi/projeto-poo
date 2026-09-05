package jogodamemoria.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import jogodamemoria.model.Tabuleiro;
import jogodamemoria.view.componentes.BotaoArredondado;
import jogodamemoria.view.componentes.Cores;
import jogodamemoria.view.componentes.PainelComFundo;
import jogodamemoria.view.componentes.PainelVidro;

public abstract class JanelaVitoriaBase extends JDialog {

    protected final BotaoArredondado btnMenu;
    protected final BotaoArredondado btnJogarNovamente;
    protected final JFrame janelaPrincipalJogo;
    protected final Tabuleiro tabuleiroAntigo;

    public JanelaVitoriaBase(JFrame janelaPai, String titulo, String textoBotaoJogarNovamente,
            JComponent conteudo, Tabuleiro tabuleiroAntigo) {
        super(janelaPai, true);
        this.janelaPrincipalJogo = janelaPai;
        this.tabuleiroAntigo = tabuleiroAntigo;

        setUndecorated(true);
        setSize(580, 420);
        setLocationRelativeTo(janelaPai);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 28, 28));

        // Fundo roxo padrão do jogo
        PainelComFundo painelFundo = new PainelComFundo("/jogodamemoria/recursos/imagens/fundo.png");
        painelFundo.setLayout(new GridBagLayout());

        PainelVidro cardCentral = new PainelVidro();
        cardCentral.setLayout(new BoxLayout(cardCentral, BoxLayout.Y_AXIS));
        cardCentral.setBorder(BorderFactory.createEmptyBorder(38, 40, 28, 40));

        // Título de Vitória 
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Cores.TEXTO_VITORIA);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardCentral.add(lblTitulo);
        cardCentral.add(Box.createVerticalStrut(16));

        // Divisor Translúcido
        JSeparator divisor = new JSeparator(SwingConstants.HORIZONTAL);
        divisor.setMaximumSize(new Dimension(440, 1));
        divisor.setForeground(Cores.DIVISOR_TRANSLUCIDO);
        divisor.setBackground(Cores.DIVISOR_TRANSLUCIDO);
        divisor.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardCentral.add(divisor);
        cardCentral.add(Box.createVerticalStrut(20));

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("PLACAR FINAL", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSubtitulo.setForeground(Cores.SUBTITULO_VITORIA);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardCentral.add(lblSubtitulo);
        cardCentral.add(Box.createVerticalStrut(14));

        // Conteúdo customizado
        conteudo.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardCentral.add(conteudo);
        cardCentral.add(Box.createVerticalStrut(22));

        // Botões no rodapé
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        painelBotoes.setOpaque(false);
        painelBotoes.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnMenu = criarBotao("Menu", Cores.BOTAO_VITORIA_SECUNDARIO, Cores.BOTAO_VITORIA_SECUNDARIO_HOVER);
        btnJogarNovamente = criarBotao(textoBotaoJogarNovamente, Cores.BOTAO_VITORIA_PRIMARIO,
                Cores.BOTAO_VITORIA_PRIMARIO_HOVER);

        painelBotoes.add(btnMenu);
        painelBotoes.add(btnJogarNovamente);
        cardCentral.add(painelBotoes);

        painelFundo.add(cardCentral);
        add(painelFundo);
    }

    protected static BotaoArredondado criarBotao(String texto, Color corBase, Color corHover) {
        BotaoArredondado botao = new BotaoArredondado(texto, new Dimension(175, 46), corBase, corHover);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 15));
        return botao;
    }

    public JButton getBtnMenu() {
        return btnMenu;
    }

    public JButton getBtnJogarNovamente() {
        return btnJogarNovamente;
    }

    public JFrame getJanelaPrincipalJogo() {
        return janelaPrincipalJogo;
    }

    public Tabuleiro getTabuleiroAntigo() {
        return tabuleiroAntigo;
    }
}