package jogodamemoria.view.componentes;

import javax.swing.*;
import java.awt.*;

public class PainelVidro extends JPanel {

    public PainelVidro() {
        super(new GridBagLayout());
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Preenchimento translúcido
        g2.setColor(new Color(255, 255, 255, 60));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

        // Borda suave
        g2.setColor(new Color(255, 255, 255, 100));
        g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 40, 40);

        g2.dispose();
        super.paintComponent(g);
    }
}
