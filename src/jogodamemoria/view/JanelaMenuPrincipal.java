package jogodamemoria.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import jogodamemoria.controller.AudioController;

public class JanelaMenuPrincipal extends JFrame {

    private JButton btnUmJogador, btnDoisJogadores, btnCreditos, btnSair, btnSom;

    private final Color ROXO = new Color(91, 75, 155);
    private final Color ROXO_HOVER = new Color(111, 91, 180);
    private final Color TEXTO = new Color(45, 27, 78);

    public JanelaMenuPrincipal() {

        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(tela.width >= 1440 ? 1440 : 1280,
                tela.height >= 900 ? 900 : 720);
        setLocationRelativeTo(null);

        PainelFundo fundo = new PainelFundo();
        fundo.setLayout(new BorderLayout());
        setContentPane(fundo);

        // PAINEL CENTRAL (Card e Menu)
        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setOpaque(false);

        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

        // TÍTULO
        JLabel titulo = new JLabel("Campus.find()");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 60));
        titulo.setForeground(TEXTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // LINHA
        JLabel linha = new JLabel("✦  ────────────  ✦  ────────────  ✦");
        linha.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 23));
        linha.setForeground(TEXTO);
        linha.setAlignmentX(Component.CENTER_ALIGNMENT);

        // SUBTÍTULO COM DIGITAÇÃO
        JLabel subtitulo = new JLabel();
        subtitulo.setFont(new Font("Consolas", Font.BOLD, 23));
        subtitulo.setForeground(new Color(63, 43, 104));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] textos = {
                "UNESP · Câmpus Bauru",
                "Desafie a sua mente!"
        };

        new EfeitoDigitacao(subtitulo, textos).iniciar();

        // BOTÕES
        btnUmJogador = criarBotao("Um Jogador");
        btnDoisJogadores = criarBotao("Dois Jogadores");
        btnCreditos = criarBotao("Créditos");
        btnSair = criarBotao("Sair");

        btnSair.addActionListener(e -> System.exit(0));

        // MONTAGEM DO MENU
        menu.add(titulo);
        menu.add(Box.createVerticalStrut(5));
        menu.add(linha);
        menu.add(Box.createVerticalStrut(12));
        menu.add(subtitulo);
        menu.add(Box.createVerticalStrut(35));

        menu.add(btnUmJogador);
        menu.add(Box.createVerticalStrut(12));
        menu.add(btnDoisJogadores);
        menu.add(Box.createVerticalStrut(12));
        menu.add(btnCreditos);
        menu.add(Box.createVerticalStrut(12));
        menu.add(btnSair);

        // CARD
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(255, 255, 255, 55));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

                g2.setColor(new Color(255, 255, 255, 90));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 40, 40);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        card.add(menu);

        painelCentral.add(card);
        fundo.add(painelCentral, BorderLayout.CENTER);

        // BOTÃO DE SOM - CANTO INFERIOR DIREITO
        JPanel painelInferiorDireito = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 30));
        painelInferiorDireito.setOpaque(false);

        btnSom = new JButton();
        btnSom.setPreferredSize(new Dimension(54, 54));
        btnSom.setBackground(ROXO);
        btnSom.setBorderPainted(false);
        btnSom.setFocusPainted(false);
        btnSom.setContentAreaFilled(false);
        btnSom.setOpaque(false);
        btnSom.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Carrega o ícone inicial com base no estado do AudioController
        atualizarIconeSomBotao(AudioController.isSomAtivado());

        btnSom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSom.setBackground(ROXO_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSom.setBackground(ROXO);
            }
        });

        painelInferiorDireito.add(btnSom);
        fundo.add(painelInferiorDireito, BorderLayout.SOUTH);
    }

    // Método para alternar as imagens som_on.png e som_off.png
    public void atualizarIconeSomBotao(boolean ativado) {        

        String caminhoIcone = ativado
                ? "/jogodamemoria/recursos/imagens/som_on.png"
                : "/jogodamemoria/recursos/imagens/som_off.png";

        var url = JanelaMenuPrincipal.class.getResource(caminhoIcone);

        if (url != null) {
            ImageIcon iconeOriginal = new ImageIcon(url);
            Image imgRedimensionada = iconeOriginal.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            btnSom.setIcon(new ImageIcon(imgRedimensionada));
            btnSom.setText("");
        } else {
            // Fallback texto caso a imagem ainda não exista na pasta
            btnSom.setText(ativado ? "🔊" : "🔇");
            btnSom.setForeground(Color.WHITE);
        }
    }

    private JButton criarBotao(String texto) {

        JButton botao = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                // SOMBRA
                g2.setColor(new Color(40, 25, 80, 45));
                g2.fillRoundRect(3, 4, getWidth() - 6, getHeight() - 6, 18, 18);

                // BOTÃO
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 6, getHeight() - 5, 18, 18);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        botao.setFont(new Font("Segoe UI", Font.BOLD, 19));
        botao.setForeground(Color.WHITE);
        botao.setBackground(ROXO);

        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setContentAreaFilled(false);
        botao.setOpaque(false);

        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Dimension tamanho = new Dimension(380, 58);
        botao.setPreferredSize(tamanho);
        botao.setMaximumSize(tamanho);
        botao.setMinimumSize(tamanho);

        botao.setAlignmentX(Component.CENTER_ALIGNMENT);

        // HOVER
        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(ROXO_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(ROXO);
            }
        });

        return botao;
    }

    // FUNDO
    class PainelFundo extends JPanel {

        private Image imagem;

        public PainelFundo() {
            var url = JanelaMenuPrincipal.class.getResource("/jogodamemoria/recursos/imagens/fundo.png");

            if (url != null)
                imagem = new ImageIcon(url).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (imagem != null) {
                g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
            }
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