package jogodamemoria.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import jogodamemoria.model.Jogador;
import jogodamemoria.model.Tabuleiro;

public class JanelaVitoriaSingle extends JDialog {

    private JButton btnMenu;
    private JButton btnJogarNovamente;
    private JFrame janelaPrincipalJogo;

    private Jogador jogador;
    private Tabuleiro tabuleiroAntigo;

    public JanelaVitoriaSingle(JFrame janelaPai, int tentativas, String tempoFinal,
            Jogador jogador, Tabuleiro tabuleiroAntigo) {

        super(janelaPai, true);

        this.janelaPrincipalJogo = janelaPai;
        this.jogador = jogador;
        this.tabuleiroAntigo = tabuleiroAntigo;

        setUndecorated(true);
        setModal(true);
        setSize(500, 300);
        setResizable(false);
        setLocationRelativeTo(janelaPai);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // CORES
        Color corFundoPrincipal = Color.decode("#CDB4F6");
        Color corFundoSecundario = Color.decode("#B794F4");
        Color corPainel = Color.decode("#A06CD5");
        Color corBotoes = Color.decode("#6D4AFF");
        Color corHover = Color.decode("#7C5CFF");
        Color textoClaro = Color.WHITE;

        // Painel Externo
        JPanel painelExterno = new JPanel(new BorderLayout());
        painelExterno.setBackground(corFundoPrincipal);
        painelExterno.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Painel Principal
        JPanel painelJanela = new JPanel(new BorderLayout());
        painelJanela.setBackground(corPainel);

        // Cabeçalho
        JPanel painelCabecalho = new JPanel(new BorderLayout());
        painelCabecalho.setPreferredSize(new Dimension(0, 50));
        painelCabecalho.setBackground(corFundoSecundario);

        JLabel lblTitulo = new JLabel("PARABÉNS! VOCÊ VENCEU!");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(textoClaro);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));

        painelCabecalho.add(lblTitulo, BorderLayout.CENTER);

        // Centro
        JPanel painelCentro = new JPanel(new GridLayout(2, 1, 0, 10));
        painelCentro.setBackground(corPainel);
        painelCentro.setBorder(new EmptyBorder(25, 20, 15, 20));

        JLabel lblTentativas = new JLabel("Tentativas: " + tentativas);
        lblTentativas.setHorizontalAlignment(SwingConstants.CENTER);
        lblTentativas.setForeground(textoClaro);
        lblTentativas.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblTempo = new JLabel("Tempo: " + tempoFinal);
        lblTempo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTempo.setForeground(textoClaro);
        lblTempo.setFont(new Font("Arial", Font.BOLD, 18));

        painelCentro.add(lblTentativas);
        painelCentro.add(lblTempo);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        painelBotoes.setBackground(corPainel);

        btnMenu = criarBotao("Voltar ao Menu", corBotoes, corHover, textoClaro);
        btnJogarNovamente = criarBotao("Jogar Novamente", corBotoes, corHover, textoClaro);

        painelBotoes.add(btnMenu);
        painelBotoes.add(btnJogarNovamente);

        // Montagem
        painelJanela.add(painelCabecalho, BorderLayout.NORTH);
        painelJanela.add(painelCentro, BorderLayout.CENTER);
        painelJanela.add(painelBotoes, BorderLayout.SOUTH);

        painelExterno.add(painelJanela, BorderLayout.CENTER);

        add(painelExterno);
    }

    private JButton criarBotao(String texto, Color bg, Color hover, Color fg) {

        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(180, 45));
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(fg, 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
            }
        });

        return btn;
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

    public Jogador getJogador() {
        return jogador;
    }

    public Tabuleiro getTabuleiroAntigo() {
        return tabuleiroAntigo;
    }
}