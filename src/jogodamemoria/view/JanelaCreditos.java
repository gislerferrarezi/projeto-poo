package jogodamemoria.view;

import javax.swing.*;
import java.awt.*;
import jogodamemoria.view.componentes.*;

public class JanelaCreditos extends PainelComFundo {

    private final BotaoArredondado btnVoltar;

    public JanelaCreditos() {
        super("/jogodamemoria/recursos/imagens/fundo.png");
        setLayout(new OverlayLayout(this));

        // CAMADA SUPERIOR: BOTÃO VOLTAR
        JPanel painelTop = new JPanel(new BorderLayout());
        painelTop.setOpaque(false);

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT, 45, 35));
        painelInferior.setOpaque(false);

        btnVoltar = new BotaoArredondado("Voltar", new Dimension(150, 48));
        btnVoltar.setFont(GerenciadorFontes.obterFonte(Font.BOLD, 17f));
        painelInferior.add(btnVoltar);
        painelTop.add(painelInferior, BorderLayout.SOUTH);

        // CONTEÚDO PRINCIPAL
        JPanel conteudo = new JPanel();
        conteudo.setOpaque(false);
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));

        // 1. Títulos
        conteudo.add(criarRotulo("UNESP MEMORY", GerenciadorFontes.obterFonte(Font.BOLD, 38f), Cores.TEXTO_BRANCO));
        conteudo.add(Box.createVerticalStrut(4));
        conteudo.add(criarRotulo("PROGRAMAÇÃO ORIENTADA A OBJETOS", GerenciadorFontes.obterFonte(Font.BOLD, 11f),
                Cores.TEXTO_CIANO));
        conteudo.add(Box.createVerticalStrut(18));

        // 2. Descrição / Objetivo (Quebra automática otimizada)
        conteudo.add(
                criarRotulo("<html><div style='text-align: center; width: 360px; line-height: 1.3; font-family: Segoe UI, sans-serif; font-size: 13px; color: #FFFFFF;'>"
                        + "Jogo da memória interativo e temático desenvolvido com elementos visuais inspirados no Câmpus da <b>UNESP Bauru</b>."
                        + "</div></html>", new Font("Segoe UI", Font.PLAIN, 13), Cores.TEXTO_BRANCO));
        conteudo.add(Box.createVerticalStrut(18));

        // 3. Cards Integrantes
        JPanel cardOrientador = criarCardInterno("ORIENTADOR", "Prof. Me. Pedro Henrique Paiola");
        JPanel cardDevs = criarCardInterno("DESENVOLVEDORES",
                "Emanuele Bellarosa G. Moraes<br>Gisler Antonio Ferrarezi Jr.");

        cardOrientador.setMaximumSize(new Dimension(380, 68));
        cardDevs.setMaximumSize(new Dimension(380, 88));

        conteudo.add(cardOrientador);
        conteudo.add(Box.createVerticalStrut(10));
        conteudo.add(cardDevs);
        conteudo.add(Box.createVerticalStrut(18));

        // 4. Divisor Transparente
        JSeparator divisor = new JSeparator(SwingConstants.HORIZONTAL);
        divisor.setMaximumSize(new Dimension(380, 1));
        divisor.setForeground(Cores.VIDRO_BORDA);
        divisor.setBackground(Cores.VIDRO_BORDA);
        conteudo.add(divisor);
        conteudo.add(Box.createVerticalStrut(14));

        // 5. Apoio Institucional
        conteudo.add(criarRotulo("DESENVOLVIMENTO E APOIO", GerenciadorFontes.obterFonte(Font.BOLD, 10f),
                Cores.TEXTO_CIANO));
        conteudo.add(Box.createVerticalStrut(10));

        JPanel logos = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        logos.setOpaque(false);
        logos.add(criarLogoContainer("/jogodamemoria/recursos/imagens/unesp.png", 45));
        logos.add(criarLogoContainer("/jogodamemoria/recursos/imagens/fc.png", 45));
        conteudo.add(logos);

        // MONTAGEM DO CARD CENTRAL (Bordas laterais reduzidas de 70 para 45)
        PainelVidro card = new PainelVidro();
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createEmptyBorder(30, 45, 30, 45));
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
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);

                g2.setColor(Cores.VIDRO_BORDA);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);

                g2.dispose();
                super.paintComponent(g);
            }
        };
        painel.setOpaque(false);
        painel.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        // Título do card (Fonte pixelada ciano)
        painel.add(criarRotulo(titulo, GerenciadorFontes.obterFonte(Font.BOLD, 10f), Cores.TEXTO_CIANO),
                BorderLayout.NORTH);

        // Nomes (Segoe UI limpa, sem negrito pesado)
        painel.add(
                criarRotulo("<html><div style='text-align: center; color: #FFFFFF; font-family: Segoe UI, sans-serif; font-size: 13px;'>" + conteudoHtml + "</div></html>",
                        new Font("Segoe UI", Font.PLAIN, 13), null),
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
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

                g2.setColor(Cores.VIDRO_BORDA);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);

                g2.dispose();
                super.paintComponent(g);
            }
        };
        painelLogo.setOpaque(false);
        painelLogo.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));

        JLabel lblLogo = GerenciadorImagens.criarLogoRedimensionada(caminho, altura);
        painelLogo.add(lblLogo);

        return painelLogo;
    }

    public JButton getBtnVoltar() {
        return btnVoltar;
    }
}