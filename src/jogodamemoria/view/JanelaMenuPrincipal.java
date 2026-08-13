package jogodamemoria.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class JanelaMenuPrincipal extends JFrame {

    private JButton btnUmJogador;
    private JButton btnDoisJogadores;
    private JButton btnCreditos;
    private JButton btnSair;

    private final Color COR_TITULO = new Color(0x2D1B4E); // Roxo escuro
    private final Color COR_SUBTITULO = new Color(0x3F2B68); // Roxo médio
    private final Color COR_BOTAO_PADRAO = new Color(0x5B4B9B); // Roxo do botão
    private final Color COR_BOTAO_HOVER = new Color(0x6D5CAE); // Roxo ao passar o mouse
    private final Color COR_BORDA_BOTAO = new Color(0x7F6FC0); // Borda do botão

    // Variáveis de escala calculadas para 1280x720
    private int larguraBotao = 350;
    private int alturaBotao = 55;
    private int tamanhoFonteBotao = 20;

    public JanelaMenuPrincipal() {
        setTitle("JOGO DA MEMÓRIA - UNESP");
        this.setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        if (tela.width >= 1440 && tela.height >= 900) {
            setSize(1440, 900); 
        } else {
            setSize(1280, 720); 
        }
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);

        // Painel de fundo
        PainelFundo painelPrincipal = new PainelFundo();
        painelPrincipal.setLayout(new GridBagLayout());
        setContentPane(painelPrincipal);

        // ---------------- PAINEL DO MENU ----------------
        JPanel painelMenu = new JPanel();
        painelMenu.setOpaque(false);
        painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));

        // 1. TÍTULOS
        JLabel lblTitulo = new JLabel("Campus.find()");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 65));
        lblTitulo.setForeground(COR_TITULO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("");
        lblSubtitulo.setFont(new Font("Consolas", Font.BOLD, 26));
        lblSubtitulo.setForeground(COR_SUBTITULO);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        String[] textos = { "UNESP · Câmpus Bauru", "Desafie a sua mente!" };
        new EfeitoDigitacao(lblSubtitulo, textos).iniciar();

        JLabel lblLinhaDecorativa = new JLabel("─────────  ✦  ─────────");
        lblLinhaDecorativa.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 30));
        lblLinhaDecorativa.setForeground(COR_SUBTITULO);
        lblLinhaDecorativa.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adiciona títulos com espaçamento fixo proporcional
        painelMenu.add(lblTitulo);
        painelMenu.add(Box.createVerticalStrut(10));
        painelMenu.add(lblLinhaDecorativa);
        painelMenu.add(Box.createVerticalStrut(15));
        painelMenu.add(lblSubtitulo);
        painelMenu.add(Box.createVerticalStrut(30));

        // 2. BOTÕES ARREDONDADOS
        btnUmJogador = criarBotaoArredondado("Um Jogador");
        btnDoisJogadores = criarBotaoArredondado("Dois Jogadores");
        btnCreditos = criarBotaoArredondado("Créditos");
        btnSair = criarBotaoArredondado("Sair");

        // Ação padrão para sair do jogo
        btnSair.addActionListener(e -> System.exit(0));

        int espacoEntreBotoes = 15;
        painelMenu.add(btnUmJogador);
        painelMenu.add(Box.createVerticalStrut(espacoEntreBotoes));
        painelMenu.add(btnDoisJogadores);
        painelMenu.add(Box.createVerticalStrut(espacoEntreBotoes));
        painelMenu.add(btnCreditos);
        painelMenu.add(Box.createVerticalStrut(espacoEntreBotoes));
        painelMenu.add(btnSair);

        painelPrincipal.add(painelMenu);
    }

    // Criador de botões com renderização suavizada
    private JButton criarBotaoArredondado(String texto) {
        JButton botao = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int raioArc = (int) (getHeight() * 0.35);

                // Fundo arredondado
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), raioArc, raioArc));

                // Borda
                g2.setColor(COR_BORDA_BOTAO);
                g2.setStroke(new BasicStroke(2));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, raioArc, raioArc));

                g2.dispose();
                super.paintComponent(g);
            }
        };

        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setOpaque(false);

        botao.setFont(new Font("Segoe UI", Font.BOLD, tamanhoFonteBotao));
        botao.setForeground(Color.WHITE);
        botao.setHorizontalAlignment(SwingConstants.CENTER);
        botao.setBackground(COR_BOTAO_PADRAO);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Dimension tamanho = new Dimension(larguraBotao, alturaBotao);
        botao.setPreferredSize(tamanho);
        botao.setMaximumSize(tamanho);
        botao.setMinimumSize(tamanho);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);

        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(COR_BOTAO_HOVER);
                botao.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(COR_BOTAO_PADRAO);
                botao.repaint();
            }
        });

        return botao;
    }

    class PainelFundo extends JPanel {
        private Image imagem;

        public PainelFundo() {
            java.net.URL url = JanelaMenuPrincipal.class.getResource("/jogodamemoria/recursos/imagens/fundo1.png");
            if (url != null) {
                imagem = new ImageIcon(url).getImage();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagem != null) {
                g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    // --- GETTERS ---
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
}