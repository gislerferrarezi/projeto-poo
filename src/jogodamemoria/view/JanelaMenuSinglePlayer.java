package jogodamemoria.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JanelaMenuSinglePlayer extends JPanel {

    private JButton btnJogarFacil;
    private JButton btnJogarPadrao;
    private JButton btnVoltar;
    private JLabel lblRegrasConteudo;
    private Image imagemFundo;

    // Paleta de cores alinhada ao Menu Principal
    private final Color ROXO = new Color(91, 75, 155);
    private final Color ROXO_HOVER = new Color(111, 91, 180);
    private final Color TEXTO = new Color(45, 27, 78);
    private final Color FUNDO_TRANSPARENTE_ROXO = new Color(91, 75, 155, 120);

    private static final String REGRAS_PADRAO_INICIAL = 
        "<html><div style='text-align: center; color: white; font-family: Segoe UI;'>"
        + "<span style='font-size: 34px; font-weight: bold;'>INSTRUÇÕES</span><br><br><br>"
        + "<span style='font-size: 22px;'>Passe o mouse sobre um dos modos ao lado para visualizar as regras e detalhes da partida.</span>"
        + "</div></html>";

    private static final String REGRAS_FACIL = 
        "<html><div style='text-align: center; color: white; font-family: Segoe UI;'>"
        + "<span style='font-size: 34px; font-weight: bold;'>MODO FÁCIL</span><br><br><br>"
        + "<span style='font-size: 20px;'>O objetivo é encontrar todos os pares o mais rápido possível, competindo contra o tempo e você mesmo.<br><br><br>"
        + "<b>Regras:</b> Este nível conta com <b>6 pares</b> (12 cartas no total), tentativas ilimitadas e sem limite de tempo.</span>"
        + "</div></html>";

    private static final String REGRAS_PADRAO = 
        "<html><div style='text-align: center; color: white; font-family: Segoe UI;'>"
        + "<span style='font-size: 34px; font-weight: bold;'>MODO PADRÃO</span><br><br><br>"
        + "<span style='font-size: 20px;'>As mesmas regras do modo fácil se aplicam aqui, porém com um desafio muito maior para a sua mente.<br><br><br>"
        + "<b>Regras:</b> São <b>12 pares</b> (24 cartas no total). Prepare-se para testar sua concentração ao máximo!</span>"
        + "</div></html>";

    public JanelaMenuSinglePlayer() {
        var url = JanelaMenuPrincipal.class.getResource("/jogodamemoria/recursos/imagens/fundo1.png");
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

                // Fundo esbranquiçado transparente para dar contraste no texto escuro
                g2.setColor(new Color(255, 255, 255, 140));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);

                g2.setColor(new Color(255, 255, 255, 200));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 35, 35);

                g2.dispose();
                super.paintComponent(g);
            }
        };
        painelTitulo.setOpaque(false);
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(18, 50, 18, 50));

        JLabel lblTitulo = new JLabel("SELECIONE A DIFICULDADE", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 52)); // Fonte maior
        lblTitulo.setForeground(TEXTO); // Cor escura do menu principal para dar leitura
        painelTitulo.add(lblTitulo, BorderLayout.CENTER);

        JPanel wrapperTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapperTitulo.setOpaque(false);
        wrapperTitulo.add(painelTitulo);
        
        add(wrapperTitulo, BorderLayout.NORTH);

        // --- PAINEL CENTRAL (DIVIDIDO EM ESQUERDA E DIREITA) ---
        JPanel painelConteudo = new JPanel(new GridLayout(1, 2, 40, 0));
        painelConteudo.setOpaque(false);

        // LADO ESQUERDO: Botões com melhor espaçamento
        JPanel painelBotoes = new JPanel(new GridBagLayout());
        painelBotoes.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(25, 0, 25, 0);

        btnJogarFacil = criarBotao("Jogar Modo Fácil");
        btnJogarPadrao = criarBotao("Jogar Modo Padrão");

        gbc.gridy = 0;
        painelBotoes.add(btnJogarFacil, gbc);
        gbc.gridy = 1;
        painelBotoes.add(btnJogarPadrao, gbc);

        // LADO DIREITO: Card de Regras
        JPanel cardRegras = new JPanel(new BorderLayout()) {
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
        cardRegras.setOpaque(false);
        cardRegras.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        lblRegrasConteudo = new JLabel(REGRAS_PADRAO_INICIAL, JLabel.CENTER);
        cardRegras.add(lblRegrasConteudo, BorderLayout.CENTER);

        painelConteudo.add(painelBotoes);
        painelConteudo.add(cardRegras);
        add(painelConteudo, BorderLayout.CENTER);

        // --- RODAPÉ: BOTÃO VOLTAR ---
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelRodape.setOpaque(false);

        btnVoltar = criarBotao("Voltar");
        Dimension tamanhoVoltar = new Dimension(220, 55);
        btnVoltar.setPreferredSize(tamanhoVoltar);
        btnVoltar.setMaximumSize(tamanhoVoltar);
        btnVoltar.setMinimumSize(tamanhoVoltar);
        
        painelRodape.add(btnVoltar);
        add(painelRodape, BorderLayout.SOUTH);

        // --- EVENTOS DE HOVER ---
        adicionarEfeitoHover(btnJogarFacil, REGRAS_FACIL);
        adicionarEfeitoHover(btnJogarPadrao, REGRAS_PADRAO);
    }

    private JButton criarBotao(String texto) {
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

    private void adicionarEfeitoHover(JButton botao, String textoRegras) {
        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblRegrasConteudo.setText(textoRegras);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lblRegrasConteudo.setText(REGRAS_PADRAO_INICIAL);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemFundo != null) {
            g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public JButton getBtnJogarFacil() {
        return btnJogarFacil;
    }

    public JButton getBtnJogarPadrao() {
        return btnJogarPadrao;
    }

    public JButton getBtnVoltar() {
        return btnVoltar;
    }
}