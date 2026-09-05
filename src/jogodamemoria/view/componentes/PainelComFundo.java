package jogodamemoria.view.componentes;

import javax.swing.*;
import java.awt.*;

public class PainelComFundo extends JPanel {

    private final Image imagemFundo;

    public PainelComFundo(String caminhoImagem) {
        this.imagemFundo = GerenciadorImagens.carregarImagem(caminhoImagem);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemFundo != null) {
            g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}