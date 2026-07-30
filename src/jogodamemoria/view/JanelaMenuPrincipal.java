package jogodamemoria.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class JanelaMenuPrincipal extends JFrame {

    private JButton btnUmJogador;
    private JButton btnDoisJogadores;
    private JButton btnCreditos;
    private JButton btnSair;

    // Paleta de Cores: Botões com tom azulado e leve transparência (Alpha 200/230)
    private final Color COR_BOTAO_PADRAO = new Color(15, 55, 90, 200);
    private final Color COR_BOTAO_HOVER = new Color(0, 102, 204, 230);
    private final Color COR_TEXTO = Color.WHITE;

    public JanelaMenuPrincipal() {
        setTitle("JOGO DA MEMÓRIA - UNESP");
        setSize(1280, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 1. Definição do painel de fundo com a imagem
        PainelFundo painelPrincipal = new PainelFundo();
        setContentPane(painelPrincipal);

        // 2. Coluna central (Agora INVISÍVEL, sem o fundo cinza)
        JPanel colunaMenu = new JPanel();
        colunaMenu.setLayout(new BoxLayout(colunaMenu, BoxLayout.Y_AXIS));
        colunaMenu.setOpaque(false); // O segredo para o menu flutuar na imagem
        colunaMenu.setBorder(new EmptyBorder(60, 100, 60, 100));

        // 3. Títulos flutuantes
        JLabel lblTitulo = new JLabel("JOGO DA MEMÓRIA", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial Black", Font.BOLD, 48));
        lblTitulo.setForeground(COR_TEXTO);
        lblTitulo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Edição Especial: UNESP Campus", JLabel.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 22));
        lblSubtitulo.setForeground(new Color(230, 230, 230));
        lblSubtitulo.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        colunaMenu.add(lblTitulo);
        colunaMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        colunaMenu.add(lblSubtitulo);
        colunaMenu.add(Box.createRigidArea(new Dimension(0, 60)));

        // 4. Painel de botões
        JPanel painelBotoes = new JPanel(new GridLayout(4, 1, 0, 20));
        painelBotoes.setOpaque(false);

        btnUmJogador = criarBotaoMenu("Um Jogador (Solo)");
        btnDoisJogadores = criarBotaoMenu("Dois Jogadores (Versus)");
        btnCreditos = criarBotaoMenu("Créditos");
        btnSair = criarBotaoMenu("Sair do Jogo");

        painelBotoes.add(btnUmJogador);
        painelBotoes.add(btnDoisJogadores);
        painelBotoes.add(btnCreditos);
        painelBotoes.add(btnSair);

        colunaMenu.add(painelBotoes);
        painelPrincipal.add(colunaMenu);
    }

    // 🔥 Método de botão que suporta transparência sem criar "fantasmas" na tela
    private JButton criarBotaoMenu(String texto) {
        JButton botao = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                // Desenha o fundo do botão com cantos arredondados
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        botao.setFont(new Font("Arial", Font.BOLD, 20));
        botao.setForeground(COR_TEXTO);
        botao.setBackground(COR_BOTAO_PADRAO);

        botao.setFocusable(false);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false); // Desativa o fundo sólido padrão do Swing
        botao.setOpaque(false); // Permite que a foto apareça atrás da cor transparente
        botao.setPreferredSize(new Dimension(400, 65));

        // Efeito Hover
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

    // Painel que renderiza a foto da Unesp no fundo
    class PainelFundo extends JPanel {

        private Image imagem;

        public PainelFundo() {

            java.net.URL url = JanelaMenuPrincipal.class.getResource(
                    "/jogodamemoria/recursos/imagens/fundo_unesp.jpg");

            if (url == null) {
                System.out.println("Imagem NÃO encontrada!");
                return;
            }

            imagem = new ImageIcon(url).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (imagem != null)
                g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
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