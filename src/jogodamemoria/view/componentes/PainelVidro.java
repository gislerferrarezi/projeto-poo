package jogodamemoria.view.componentes;

import javax.swing.*;
import java.awt.*;

public class PainelVidro extends JPanel {

    public PainelVidro() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // 1. Sombra suave
        g2.setColor(Cores.SOMBRA);
        g2.fillRoundRect(3, 3, w - 3, h - 3, 18, 18);

        // 2. Fundo Fumê Translucido (escurece o logo atrás para dar leitura aos textos)
        g2.setColor(Cores.VIDRO_FUNDO);
        g2.fillRoundRect(0, 0, w - 3, h - 3, 18, 18);

        // 3. Borda Ciano/Neon suave
        g2.setColor(Cores.VIDRO_BORDA);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, w - 4, h - 4, 18, 18);

        g2.dispose();
        super.paintComponent(g);
    }
}