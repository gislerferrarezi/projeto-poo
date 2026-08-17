package jogodamemoria.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JanelaMenuMultiplayer extends JPanel {

    private JButton btnMultiplayerLocal;
    private JButton btnMultiplayerOnline;
    private JButton btnVoltar;
    private Image imagemFundo;

    // Paleta de cores alinhada ao Menu Principal
    private final Color ROXO = new Color(91, 75, 155);
    private final Color ROXO_HOVER = new Color(111, 91, 180);
    private final Color TEXTO = new Color(45, 27, 78);
    private final Color FUNDO_TRANSPARENTE_ROXO = new Color(91, 75, 155, 110);
    private final Color FUNDO_INTERNO = new Color(255, 255, 255, 35);

    public JanelaMenuMultiplayer() {
        var url = JanelaMenuPrincipal.class.getResource("/jogodamemoria/recursos/imagens/fundo.png");
        if (url != null) {
            imagemFundo = new ImageIcon(url).getImage();
        }

        setLayout(new BorderLayout(0, 25));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // --- TÍTULO EM DESTAQUE ---
        JPanel painelTitulo = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(255, 255, 255, 160));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);

                g2.setColor(new Color(255, 255, 255, 220));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 35, 35);

                g2.dispose();
                super.paintComponent(g);
            }
        };
        painelTitulo.setOpaque(false);
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(18, 60, 18, 60));

        JLabel lblTitulo = new JLabel("MODO MULTIPLAYER", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 46));
        lblTitulo.setForeground(TEXTO);
        painelTitulo.add(lblTitulo, BorderLayout.CENTER);

        JPanel wrapperTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapperTitulo.setOpaque(false);
        wrapperTitulo.add(painelTitulo);

        add(wrapperTitulo, BorderLayout.NORTH);

        // --- PAINEL CENTRAL (DIVIDIDO EM ESQUERDA E DIREITA) ---
        JPanel painelConteudo = new JPanel(new GridLayout(1, 2, 40, 0));
        painelConteudo.setOpaque(false);

        // LADO ESQUERDO: Botões de modo (Local / Online)
        JPanel painelBotoes = new JPanel(new GridBagLayout());
        painelBotoes.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(25, 0, 25, 0);

        btnMultiplayerLocal = criarBotaoMenu("Multiplayer Local");
        btnMultiplayerOnline = criarBotaoMenu("Multiplayer Online");

        gbc.gridy = 0;
        painelBotoes.add(btnMultiplayerLocal, gbc);
        gbc.gridy = 1;
        painelBotoes.add(btnMultiplayerOnline, gbc);

        // LADO DIREITO: Card Principal Organizado em Sub-blocos
        JPanel cardRegras = new JPanel();
        cardRegras.setOpaque(false);
        cardRegras.setLayout(new BoxLayout(cardRegras, BoxLayout.Y_AXIS));
        cardRegras.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Título do Card
        JLabel lblHeaderRegras = new JLabel("REGRAS ESPECIAIS");
        lblHeaderRegras.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHeaderRegras.setForeground(Color.WHITE);
        lblHeaderRegras.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardRegras.add(lblHeaderRegras);
        cardRegras.add(Box.createRigidArea(new Dimension(0, 12)));

        // Sub-bloco 1: Tempo e Objetivo (Amarelo)
        JPanel blocoInfo = criarSubBloco(
            "<html><div style='font-family: Segoe UI; font-size: 14px; color: #FFF8DC;'>"
            + "<span style='color: #FFD700;'>⏱️ <b>Tempo:</b></span> 30s por jogada<br>"
            + "<span style='color: #FFD700;'>🎯 <b>Objetivo:</b></span> Somar mais pontos alternando turnos"
            + "</div></html>"
        );
        cardRegras.add(blocoInfo);
        cardRegras.add(Box.createRigidArea(new Dimension(0, 10)));

        // Sub-bloco 2: Perdeu a Vez (Vermelho)
        JPanel blocoPerdeu = criarSubBloco(
            "<html><div style='font-family: Segoe UI; font-size: 14px; color: #FFF8DC;'>"
            + "<span style='color: #FF6B6B;'>🚫 <b>Perdeu a Vez:</b></span> Passa o turno para o oponente imediatamente."
            + "</div></html>"
        );
        cardRegras.add(blocoPerdeu);
        cardRegras.add(Box.createRigidArea(new Dimension(0, 10)));

        // Sub-bloco 3: Jogue de Novo (Azul)
        JPanel blocoJogue = criarSubBloco(
            "<html><div style='font-family: Segoe UI; font-size: 14px; color: #FFF8DC;'>"
            + "<span style='color: #38B6FF;'>🔄 <b>Jogue de Novo:</b></span> Ganhe uma tentativa extra na mesma rodada."
            + "</div></html>"
        );
        cardRegras.add(blocoJogue);
        cardRegras.add(Box.createRigidArea(new Dimension(0, 10)));

        // Sub-bloco 4: Rodada 2x (Verde)
        JPanel blocoRodada = criarSubBloco(
            "<html><div style='font-family: Segoe UI; font-size: 14px; color: #FFF8DC;'>"
            + "<span style='color: #51CF66;'>⚡ <b>Rodada 2x:</b></span> O próximo par acertado valerá o dobro de pontos!"
            + "</div></html>"
        );
        cardRegras.add(blocoRodada);

        // Painel envelopador do card direito para aplicar o fundo translúcido arredondado perfeitamente
        JPanel wrapperCard = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(FUNDO_TRANSPARENTE_ROXO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

                g2.setColor(new Color(255, 255, 255, 90));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 40, 40);

                g2.dispose();
                super.paintComponent(g);
            }
        };
        wrapperCard.setOpaque(false);
        wrapperCard.add(cardRegras, BorderLayout.CENTER);

        painelConteudo.add(painelBotoes);
        painelConteudo.add(wrapperCard);
        add(painelConteudo, BorderLayout.CENTER);

        // --- RODAPÉ: BOTÃO VOLTAR ---
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelRodape.setOpaque(false);

        btnVoltar = criarBotaoMenu("Voltar");
        Dimension tamanhoVoltar = new Dimension(220, 55);
        btnVoltar.setPreferredSize(tamanhoVoltar);
        btnVoltar.setMaximumSize(tamanhoVoltar);
        btnVoltar.setMinimumSize(tamanhoVoltar);

        painelRodape.add(btnVoltar);
        add(painelRodape, BorderLayout.SOUTH);
    }

    private JPanel criarSubBloco(String conteudoHtml) {
        JPanel painel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(FUNDO_INTERNO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        painel.setOpaque(false);
        painel.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        JLabel label = new JLabel(conteudoHtml);
        painel.add(label, BorderLayout.CENTER);

        return painel;
    }

    private JButton criarBotaoMenu(String texto) {
        JButton botao = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(40, 25, 80, 45));
                g2.fillRoundRect(3, 4, getWidth() - 6, getHeight() - 6, 18, 18);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 6, getHeight() - 5, 18, 18);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        botao.setFont(new Font("Segoe UI", Font.BOLD, 22));
        botao.setForeground(Color.WHITE);
        botao.setBackground(ROXO);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setContentAreaFilled(false);
        botao.setOpaque(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Dimension tamanho = new Dimension(420, 70);
        botao.setPreferredSize(tamanho);
        botao.setMaximumSize(tamanho);
        botao.setMinimumSize(tamanho);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemFundo != null) {
            g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public JButton getBtnMultiplayerLocal() {
        return btnMultiplayerLocal;
    }

    public JButton getBtnMultiplayerOnline() {
        return btnMultiplayerOnline;
    }

    public JButton getBtnVoltar() {
        return btnVoltar;
    }
}