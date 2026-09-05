package jogodamemoria.view;

import javax.swing.*;
import java.awt.*;

import jogodamemoria.view.componentes.BotaoArredondado;
import jogodamemoria.view.componentes.Cores;

public class JanelaMenuMultiplayer extends JanelaMenuBase {

    private final BotaoArredondado btnMultiplayerLocal;

    public JanelaMenuMultiplayer() {
        super("MODO MULTIPLAYER");

        // Botão no Card Esquerdo
        btnMultiplayerLocal = new BotaoArredondado("Iniciar Jogo", new Dimension(320, 65));

        adicionarBotaoOpcao(btnMultiplayerLocal, 0);

        // Regras no Card Direito
        cardRegras.setLayout(new BoxLayout(cardRegras, BoxLayout.Y_AXIS));
        cardRegras.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lblHeaderRegras = new JLabel("REGRAS ESPECIAIS");
        lblHeaderRegras.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHeaderRegras.setForeground(Cores.TEXTO);
        lblHeaderRegras.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardRegras.add(lblHeaderRegras);
        cardRegras.add(Box.createRigidArea(new Dimension(0, 12)));

        cardRegras.add(criarSubBloco(
                "<html><div style='font-family: Segoe UI; font-size: 13px; color: #2D1B4E;'>"
                        + "<span style='color: #bba733;'><b>Tempo:</b></span> 30s por jogada<br>"
                        + "<span style='color: #bba733;'><b>Objetivo:</b></span> Somar mais pontos alternando turnos"
                        + "</div></html>"));
        cardRegras.add(Box.createRigidArea(new Dimension(0, 10)));

        cardRegras.add(criarSubBloco(
                "<html><div style='font-family: Segoe UI; font-size: 13px; color: #2D1B4E;'>"
                        + "<span style='color: #B22222;'><b>Perdeu a Vez:</b></span> Passa o turno para o oponente imediatamente."
                        + "</div></html>"));
        cardRegras.add(Box.createRigidArea(new Dimension(0, 10)));

        cardRegras.add(criarSubBloco(
                "<html><div style='font-family: Segoe UI; font-size: 13px; color: #2D1B4E;'>"
                        + "<span style='color: #00509E;'><b>Jogue de Novo:</b></span> Ganhe uma tentativa extra na mesma rodada."
                        + "</div></html>"));
        cardRegras.add(Box.createRigidArea(new Dimension(0, 10)));

        cardRegras.add(criarSubBloco(
                "<html><div style='font-family: Segoe UI; font-size: 13px; color: #2D1B4E;'>"
                        + "<span style='color: #228B22;'><b>Rodada 2x:</b></span> O próximo par acertado valerá o dobro de pontos!"
                        + "</div></html>"));
    }

    private JPanel criarSubBloco(String conteudoHtml) {
        JPanel painel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 85));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        painel.setOpaque(false);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        JLabel label = new JLabel(conteudoHtml);
        painel.add(label, BorderLayout.CENTER);

        return painel;
    }

    public JButton getBtnMultiplayerLocal() {
        return btnMultiplayerLocal;
    }
}