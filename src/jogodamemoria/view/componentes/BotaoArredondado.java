package jogodamemoria.view.componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotaoArredondado extends JButton {

    private final Color corBase;
    private final Color corHover;

    public BotaoArredondado(String texto, Dimension tamanho) {
        this(texto, tamanho, Cores.AZUL_BOTAO, Cores.AZUL_BOTAO_HOVER);
    }

    public BotaoArredondado(String texto, Dimension tamanho, Color corBase, Color corHover) {
        super(texto);
        this.corBase = corBase;
        this.corHover = corHover;

        setFont(new Font("Segoe UI", Font.BOLD, 18));
        setForeground(Cores.TEXTO_BRANCO);
        setBackground(this.corBase);

        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        setPreferredSize(tamanho);
        setMaximumSize(tamanho);
        setMinimumSize(tamanho);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { setBackground(BotaoArredondado.this.corHover); }
            @Override
            public void mouseExited(MouseEvent e) { setBackground(BotaoArredondado.this.corBase); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(Cores.SOMBRA);
        g2.fillRoundRect(3, 3, w - 3, h - 3, 18, 18);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, w - 3, h - 3, 18, 18);

        g2.dispose();

        Graphics gTexto = g.create();
        gTexto.translate(-1, -1);
        super.paintComponent(gTexto);
        gTexto.dispose();
    }
}