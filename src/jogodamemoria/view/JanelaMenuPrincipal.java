package jogodamemoria.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import jogodamemoria.controller.AudioController;
import jogodamemoria.view.componentes.BotaoArredondado;
import jogodamemoria.view.componentes.Cores;
import jogodamemoria.view.componentes.EfeitoDigitacao;
import jogodamemoria.view.componentes.GerenciadorImagens;
import jogodamemoria.view.componentes.PainelComFundo;
import jogodamemoria.view.componentes.PainelVidro;

public class JanelaMenuPrincipal extends JFrame {

    private final BotaoArredondado btnUmJogador;
    private final BotaoArredondado btnDoisJogadores;
    private final BotaoArredondado btnCreditos;
    private final BotaoArredondado btnSair;
    private final JButton btnSom;

    public JanelaMenuPrincipal() {
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(tela.width >= 1440 ? 1440 : 1280,
                tela.height >= 900 ? 900 : 720);
        setLocationRelativeTo(null);

        setShape(new java.awt.geom.RoundRectangle2D.Double(
                0, 0, getWidth(), getHeight(), 30, 30));

        // Painel Principal 
        PainelComFundo fundo = new PainelComFundo("/jogodamemoria/recursos/imagens/fundo.png");
        fundo.setLayout(new OverlayLayout(fundo));
        setContentPane(fundo);

        // CAMADA SUPERIOR: BOTÃO DE SOM 
        JPanel painelSomContainer = new JPanel(new BorderLayout());
        painelSomContainer.setOpaque(false);

        JPanel painelInferiorDireito = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 30));
        painelInferiorDireito.setOpaque(false);

        btnSom = new JButton();
        btnSom.setPreferredSize(new Dimension(54, 54));
        btnSom.setBackground(Cores.ROXO);
        btnSom.setBorderPainted(false);
        btnSom.setFocusPainted(false);
        btnSom.setContentAreaFilled(false);
        btnSom.setOpaque(false);
        btnSom.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSom.setBackground(Cores.ROXO_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSom.setBackground(Cores.ROXO);
            }
        });

        atualizarIconeSomBotao(AudioController.isSomAtivado());

        painelInferiorDireito.add(btnSom);
        painelSomContainer.add(painelInferiorDireito, BorderLayout.SOUTH);

        // CAMADA INFERIOR: CARD CENTRALIZADO 
        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setOpaque(false);

        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

        // TÍTULO
        JLabel titulo = new JLabel("Campus.find()");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 54));
        titulo.setForeground(Cores.TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // LINHA DIVISORA  
        JSeparator divisor = new JSeparator(SwingConstants.HORIZONTAL);
        divisor.setMaximumSize(new Dimension(360, 1));
        divisor.setForeground(Cores.DIVISOR_TRANSLUCIDO);
        divisor.setBackground(Cores.DIVISOR_TRANSLUCIDO);
        divisor.setAlignmentX(Component.CENTER_ALIGNMENT);

        // SUBTÍTULO COM DIGITAÇÃO
        JLabel subtitulo = new JLabel();
        subtitulo.setFont(new Font("Consolas", Font.BOLD, 20));
        subtitulo.setForeground(Cores.TEXTO_MUTED);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] textos = {
                "UNESP · Câmpus Bauru",
                "Desafie a sua mente!"
        };

        new EfeitoDigitacao(subtitulo, textos).iniciar();

        // BOTÕES DO MENU
        Dimension tamanhoBotao = new Dimension(360, 56);

        btnUmJogador = new BotaoArredondado("Um Jogador", tamanhoBotao);
        btnDoisJogadores = new BotaoArredondado("Dois Jogadores", tamanhoBotao);
        btnCreditos = new BotaoArredondado("Créditos", tamanhoBotao);
        btnSair = new BotaoArredondado("Sair", tamanhoBotao);

        btnUmJogador.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDoisJogadores.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCreditos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSair.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSair.addActionListener(e -> System.exit(0));

        // MONTAGEM DA ESTRUTURA
        menu.add(titulo);
        menu.add(Box.createVerticalStrut(12));
        menu.add(divisor);
        menu.add(Box.createVerticalStrut(16));
        menu.add(subtitulo);
        menu.add(Box.createVerticalStrut(32));

        menu.add(btnUmJogador);
        menu.add(Box.createVerticalStrut(12));
        menu.add(btnDoisJogadores);
        menu.add(Box.createVerticalStrut(12));
        menu.add(btnCreditos);
        menu.add(Box.createVerticalStrut(12));
        menu.add(btnSair);

        // CARD EM PAINEL VIDRO
        PainelVidro card = new PainelVidro();
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createEmptyBorder(45, 55, 45, 55));
        card.add(menu);

        painelCentral.add(card);

        // ADICIONA AS CAMADAS
        fundo.add(painelSomContainer);
        fundo.add(painelCentral);
    }

    public void atualizarIconeSomBotao(boolean ativado) {
        String caminhoIcone = ativado
                ? "/jogodamemoria/recursos/imagens/som_on.png"
                : "/jogodamemoria/recursos/imagens/som_off.png";

        JLabel lblIcone = GerenciadorImagens.criarLogoRedimensionada(caminhoIcone, 50);

        if (lblIcone.getIcon() != null) {
            btnSom.setIcon(lblIcone.getIcon());
            btnSom.setText("");
        } else {
            btnSom.setIcon(null);
            btnSom.setText(ativado ? "🔊" : "🔇");
            btnSom.setForeground(Cores.TEXTO_BRANCO);
        }
    }

    public JButton getBtnUmJogador() {
        return btnUmJogador;
    }

    public JButton getBtnDoisJogadores() {
        return btnDoisJogadores;
    }

    public JButton getBtnCreditos() {
        return btnCreditos;
    }

    public JButton getBtnSair() {
        return btnSair;
    }

    public JButton getBtnSom() {
        return btnSom;
    }
}