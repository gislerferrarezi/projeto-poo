package jogodamemoria.view;

import javax.swing.*;
import java.awt.*;
import jogodamemoria.view.componentes.*;

public class JanelaCreditos extends PainelComFundo {

    private final BotaoArredondado btnVoltar;

    public JanelaCreditos() {
        super("/jogodamemoria/recursos/imagens/fundo.png");
        setLayout(new OverlayLayout(this));

        // CAMADA SUPERIOR: BOTÃO VOLTAR (Alinhado exatamente igual ao JanelaMenuBase)
        JPanel painelTop = new JPanel(new BorderLayout());
        painelTop.setOpaque(false);
        painelTop.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // Mesmo padding da base

        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0)); // Mesmas margens do rodapé base
        painelRodape.setOpaque(false);

        btnVoltar = new BotaoArredondado("Voltar", new Dimension(150, 48));
        btnVoltar.setFont(GerenciadorFontes.obterFonte(Font.BOLD, 17f));
        painelRodape.add(btnVoltar);

        painelTop.add(painelRodape, BorderLayout.SOUTH);

        // CONTEÚDO PRINCIPAL
        JPanel conteudo = new JPanel();
        conteudo.setOpaque(false);
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));

        // 1. Títulos
        conteudo.add(criarRotulo("UNESP MEMORY", GerenciadorFontes.obterFonte(Font.BOLD, 48f), Cores.TEXTO_BRANCO));
        conteudo.add(Box.createVerticalStrut(4));
        conteudo.add(criarRotulo("PROGRAMAÇÃO ORIENTADA A OBJETOS", GerenciadorFontes.obterFonte(Font.BOLD, 13f),
                Cores.TEXTO_CIANO));
        conteudo.add(Box.createVerticalStrut(20));

        // 2. Descrição / Objetivo
        conteudo.add(criarRotulo("<html><div style='text-align: center; width: 320px; line-height: 1.3;'>"
                + "Jogo da memória interativo e temático desenvolvido com elementos visuais inspirados no Câmpus da<br><b>UNESP Bauru</b>."
                + "</div></html>", new Font("Segoe UI", Font.PLAIN, 15), Cores.TEXTO_BRANCO));
        conteudo.add(Box.createVerticalStrut(22));

        // 3. Cards Integrantes
        JPanel cardOrientador = criarCardInterno("ORIENTADOR", "Prof. Me. Pedro Henrique Paiola");
        JPanel cardDevs = criarCardInterno("DESENVOLVEDORES",
                "Emanuele Bellarosa G. Moraes<br><span style='margin-top:2px;'>Gisler Antonio Ferrarezi Jr.</span>");

        cardOrientador.setMaximumSize(new Dimension(400, 68));
        cardDevs.setMaximumSize(new Dimension(400, 88));

        conteudo.add(cardOrientador);
        conteudo.add(Box.createVerticalStrut(12));
        conteudo.add(cardDevs);
        conteudo.add(Box.createVerticalStrut(20));

        // 4. Divisor Transparente
        JSeparator divisor = new JSeparator(SwingConstants.HORIZONTAL);
        divisor.setMaximumSize(new Dimension(400, 1));
        divisor.setForeground(Cores.VIDRO_BORDA);
        divisor.setBackground(Cores.VIDRO_BORDA);
        conteudo.add(divisor);
        conteudo.add(Box.createVerticalStrut(18));

        // 5. Apoio Institucional
        conteudo.add(criarRotulo("DESENVOLVIMENTO E APOIO", GerenciadorFontes.obterFonte(Font.BOLD, 12f),
                Cores.TEXTO_CIANO));
        conteudo.add(Box.createVerticalStrut(12));

        JPanel logos = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
        logos.setOpaque(false);
        logos.add(criarLogoContainer("/jogodamemoria/recursos/imagens/unesp.png", 50));
        logos.add(criarLogoContainer("/jogodamemoria/recursos/imagens/fc.png", 50));
        conteudo.add(logos);

        // MONTAGEM DO CARD CENTRAL
        PainelVidro card = new PainelVidro();
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createEmptyBorder(35, 90, 35, 90));
        card.add(conteudo);

        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setOpaque(false);
        painelCentral.add(card);

        add(painelTop);
        add(painelCentral);
    }

    private JLabel criarRotulo(String texto, Font fonte, Color cor) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(fonte);
        if (cor != null) {
            lbl.setForeground(cor);
        }
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    private JPanel criarCardInterno(String titulo, String conteudoHtml) {
        JPanel painel = new JPanel(new BorderLayout(0, 4)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Cores.VIDRO_FUNDO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(Cores.VIDRO_BORDA);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        painel.setOpaque(false);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        // Rótulos dos cards em fonte pixelada
        painel.add(criarRotulo(titulo, GerenciadorFontes.obterFonte(Font.BOLD, 11f), Cores.TEXTO_CIANO),
                BorderLayout.NORTH);

        // Conteúdo dos nomes em Segoe UI original
        painel.add(
                criarRotulo("<html><div style='text-align: center; color: #FFFFFF;'>" + conteudoHtml + "</div></html>",
                        new Font("Segoe UI", Font.PLAIN, 15), null),
                BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarLogoContainer(String caminho, int altura) {
        JPanel painelLogo = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Cores.VIDRO_FUNDO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.setColor(Cores.VIDRO_BORDA);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        painelLogo.setOpaque(false);
        painelLogo.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        JLabel lblLogo = GerenciadorImagens.criarLogoRedimensionada(caminho, altura);
        painelLogo.add(lblLogo);

        return painelLogo;
    }

    public JButton getBtnVoltar() {
        return btnVoltar;
    }
}