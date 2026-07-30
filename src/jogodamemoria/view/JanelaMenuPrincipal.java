package jogodamemoria.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class JanelaMenuPrincipal extends JFrame {

    private JButton btnUmJogador;
    private JButton btnDoisJogadores;
    private JButton btnCreditos;
    private JButton btnSair;

    // Cores extraídas do seu design
    private final Color COR_TITULO = new Color(0x2D1B4E); // Roxo escuro
    private final Color COR_SUBTITULO = new Color(0x3F2B68); // Roxo médio
    private final Color COR_BOTAO_PADRAO = new Color(0x5B4B9B); // Roxo do botão
    private final Color COR_BOTAO_HOVER = new Color(0x6D5CAE); // Roxo ao passar o mouse
    private final Color COR_BORDA_BOTAO = new Color(0x7F6FC0); // Borda do botão

    public JanelaMenuPrincipal() {
        setTitle("JOGO DA MEMÓRIA - UNESP");
        setSize(1280, 800);
        this.setUndecorated(true);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel de fundo com a imagem
        PainelFundo painelPrincipal = new PainelFundo();
        painelPrincipal.setLayout(new GridBagLayout()); // Centraliza o painel do menu de forma simples
        setContentPane(painelPrincipal);

        // ---------------- PAINEL DO MENU ----------------
        JPanel painelMenu = new JPanel();
        painelMenu.setOpaque(false); // Deixa transparente para mostrar a foto do fundo
        painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));

        // 1. TÍTULOS
        JLabel lblTitulo = new JLabel("Câmpus.find()");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 72));
        lblTitulo.setForeground(COR_TITULO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtítulo principal centralizado com efeito de digitação
        JLabel lblSubtitulo = new JLabel("");
        // 'Segoe UI Symbol' suporta tanto as letras da Segoe UI quanto o caractere '✦'
        lblSubtitulo.setFont(new Font("Consolas", Font.BOLD, 32));
        lblSubtitulo.setForeground(COR_SUBTITULO);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Texto puro (SEM HTML) para o efeito de digitação não pirar
        String[] textos = {"UNESP · Campus Bauru", "Desafie a sua mente!"};

        new EfeitoDigitacao(lblSubtitulo, textos).iniciar();

        JLabel lblLinhaDecorativa = new JLabel("─────────  ✦  ─────────");
        lblLinhaDecorativa.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 36));
        lblLinhaDecorativa.setForeground(COR_SUBTITULO);
        lblLinhaDecorativa.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adiciona os títulos no painel com espaçamento simples
        painelMenu.add(lblTitulo);
        painelMenu.add(Box.createVerticalStrut(10));
        painelMenu.add(lblLinhaDecorativa);
        painelMenu.add(Box.createVerticalStrut(20)); // Espaço até os botões
        painelMenu.add(lblSubtitulo);
        painelMenu.add(Box.createVerticalStrut(20)); // Espaço até os botões

        ImageIcon iconUmJogador = new ImageIcon(getClass().getResource("/jogodamemoria/recursos/imagens/user.png"));
        ImageIcon iconDoisJogadores = new ImageIcon(
                getClass().getResource("/jogodamemoria/recursos/imagens/users.png"));
        ImageIcon iconCreditos = new ImageIcon(getClass().getResource("/jogodamemoria/recursos/imagens/star.png"));
        ImageIcon iconSair = new ImageIcon(getClass().getResource("/jogodamemoria/recursos/imagens/exit-door.png"));

        btnUmJogador = criarBotaoBasico(iconUmJogador, "Um Jogador");
        btnDoisJogadores = criarBotaoBasico(iconDoisJogadores, "Dois Jogadores");
        btnCreditos = criarBotaoBasico(iconCreditos, "Créditos");
        btnSair = criarBotaoBasico(iconSair, "Sair");

        painelMenu.add(btnUmJogador);
        painelMenu.add(Box.createVerticalStrut(20));
        painelMenu.add(btnDoisJogadores);
        painelMenu.add(Box.createVerticalStrut(20));
        painelMenu.add(btnCreditos);
        painelMenu.add(Box.createVerticalStrut(20));
        painelMenu.add(btnSair);

        // Adiciona todo o menu dentro da tela
        painelPrincipal.add(painelMenu);
    }

    // Método Final: Ícones redimensionados (26px), alinhados em coluna e texto
    // centralizado
    private JButton criarBotaoBasico(ImageIcon iconeOriginal, String texto) {
        JButton botao = new JButton();
        botao.setLayout(new BorderLayout());

        // Área fixa reservada para a coluna de ícones (mantém o alinhamento vertical
        // entre botões)
        int LARGURA_AREA_ICONE = 60;

        // 1. ESQUERDA: Ícone Redimensionado e Centralizado na sua caixinha
        if (iconeOriginal != null) {
            int tamanhoIcone = 26; // Tamanho ideal mantido
            ImageIcon iconeAjustado = new ImageIcon(
                    iconeOriginal.getImage().getScaledInstance(tamanhoIcone, tamanhoIcone, Image.SCALE_SMOOTH));

            JLabel lblIcone = new JLabel(iconeAjustado, SwingConstants.CENTER);
            lblIcone.setPreferredSize(new Dimension(LARGURA_AREA_ICONE, 65));
            botao.add(lblIcone, BorderLayout.WEST);
        }

        // 2. CENTRO: Texto do Botão (Centralizado no meio geométrico)
        JLabel lblTexto = new JLabel(texto, SwingConstants.CENTER);
        lblTexto.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTexto.setForeground(Color.WHITE);
        botao.add(lblTexto, BorderLayout.CENTER);

        // 3. DIREITA: Espaçador Invisível de Compensação
        // Equilibra o peso do ícone da esquerda para o texto ficar 100% no meio
        JPanel espacadorDireita = new JPanel();
        espacadorDireita.setOpaque(false);
        espacadorDireita.setPreferredSize(new Dimension(LARGURA_AREA_ICONE, 65));
        botao.add(espacadorDireita, BorderLayout.EAST);

        // --- Estilo e Cores ---
        botao.setBackground(COR_BOTAO_PADRAO);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Tamanho do Botão
        Dimension tamanho = new Dimension(420, 65);
        botao.setPreferredSize(tamanho);
        botao.setMaximumSize(tamanho);
        botao.setMinimumSize(tamanho);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Borda
        botao.setBorder(BorderFactory.createLineBorder(COR_BORDA_BOTAO, 2));

        // Hover
        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(COR_BOTAO_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(COR_BOTAO_PADRAO);
            }
        });

        return botao;
    }

    // Painel básico para desenhar a imagem de fundo
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