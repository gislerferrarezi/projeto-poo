package jogodamemoria.view.componentes;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GerenciadorImagens {

    private GerenciadorImagens() {}

    public static Image carregarImagem(String caminho) {
        URL url = GerenciadorImagens.class.getResource(caminho);
        return (url != null) ? new ImageIcon(url).getImage() : null;
    }

    public static JLabel criarLogoRedimensionada(String caminho, int alturaDesejada) {
        JLabel label = new JLabel();
        URL url = GerenciadorImagens.class.getResource(caminho);

        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            int largOrig = original.getIconWidth();
            int altOrig = original.getIconHeight();

            if (altOrig > 0) {
                int largura = (largOrig * alturaDesejada) / altOrig;
                Image imagem = original.getImage().getScaledInstance(largura, alturaDesejada, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(imagem));
            }
        }
        return label;
    }
}