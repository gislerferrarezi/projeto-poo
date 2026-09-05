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
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 17));
        painelInferior.add(btnVoltar);
        painelTop.add(painelInferior, BorderLayout.SOUTH);

        // CONTEÚDO PRINCIPAL 
        JPanel conteudo = new JPanel();
        conteudo.setOpaque(false);
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));

        // 1. Títulos
        conteudo.add(criarRotulo("Campus.find()", new Font("Segoe UI", Font.BOLD, 48), Cores.TEXTO));
        conteudo.add(Box.createVerticalStrut(4));
        conteudo.add(criarRotulo("PROGRAMAÇÃO ORIENTADA A OBJETOS", new Font("Segoe UI", Font.BOLD, 13), Cores.ROXO));
        conteudo.add(Box.createVerticalStrut(20));

        // 2. Descrição / Objetivo 
        conteudo.add(criarRotulo("<html><div style='text-align: center; width: 320px; line-height: 1.3;'>"
                + "Jogo da memória interativo e temático desenvolvido com elementos visuais inspirados no Câmpus da<br><b>UNESP Bauru</b>."
                + "</div></html>", new Font("Segoe UI", Font.PLAIN, 15), Cores.TEXTO_MUTED));
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
        divisor.setForeground(Cores.DIVISOR_TRANSLUCIDO);
        divisor.setBackground(Cores.DIVISOR_TRANSLUCIDO);
        conteudo.add(divisor);
        conteudo.add(Box.createVerticalStrut(18));

        // 5. Apoio Institucional
        conteudo.add(criarRotulo("DESENVOLVIMENTO E APOIO", new Font("Segoe UI", Font.BOLD, 12), Cores.ROXO));
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
                g2.setColor(new Color(255, 255, 255, 125));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(new Color(255, 255, 255, 200));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        painel.setOpaque(false);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        painel.add(criarRotulo(titulo, new Font("Segoe UI", Font.BOLD, 11), Cores.ROXO), BorderLayout.NORTH);
        painel.add(
                criarRotulo("<html><div style='text-align: center; color: #1A0C2E;'>" + conteudoHtml + "</div></html>",
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
                g2.setColor(new Color(255, 255, 255, 100));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
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