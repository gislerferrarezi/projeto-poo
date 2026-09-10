package jogodamemoria.view.componentes;

import jogodamemoria.model.Carta;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BotaoCarta extends JButton {

    private final Carta cartaModel;

    // Cache na memória RAM para carregar cada PNG apenas uma vez
    private static Image imagemVerso;
    private static final Map<String, Image> cacheFrentes = new HashMap<>();

    public BotaoCarta(Carta cartaModel) {
        this.cartaModel = cartaModel;

        // Remove fundos e bordas nativas do Swing
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setBorder(null);

        // Carrega o verso padrão na primeira inicialização
        if (imagemVerso == null) {
            imagemVerso = carregarImagem("/jogodamemoria/recursos/imagens/cartas/cardBack_blue.png");
        }
    }

    public Carta getCartaModel() {
        return cartaModel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Ativa renderização de alta qualidade
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int w = getWidth();
        int h = getHeight();

        // 1. Determina a imagem (Frente ou Verso)
        Image imgParaDesenhar;
        if (!cartaModel.isVirada() && !cartaModel.isDescoberta()) {
            imgParaDesenhar = imagemVerso;
        } else {
            imgParaDesenhar = obterImagemFrente(cartaModel);
        }

        // 2. Renderiza de forma proporcional no centro do botão
        if (imgParaDesenhar != null) {
            int imgW = imgParaDesenhar.getWidth(null);
            int imgH = imgParaDesenhar.getHeight(null);

            if (imgW > 0 && imgH > 0) {
                double scale = Math.min((double) w / imgW, (double) h / imgH);
                int drawW = (int) (imgW * scale);
                int drawH = (int) (imgH * scale);

                int x = (w - drawW) / 2;
                int y = (h - drawH) / 2;

                g2.drawImage(imgParaDesenhar, x, y, drawW, drawH, null);
            }
        } else {
            // Indicador visual em caso de imagem não encontrada
            g2.setColor(Color.RED);
            g2.drawString("PNG?", 5, h / 2);
        }

        g2.dispose();
    }

    private Image obterImagemFrente(Carta carta) {
        String nomeArquivo = definirNomeDoArquivo(carta);

        if (cacheFrentes.containsKey(nomeArquivo)) {
            return cacheFrentes.get(nomeArquivo);
        }

        Image novaImagem = carregarImagem("/jogodamemoria/recursos/imagens/cartas/" + nomeArquivo);
        cacheFrentes.put(nomeArquivo, novaImagem);
        return novaImagem;
    }

    private String definirNomeDoArquivo(Carta carta) {
        // 1. Mapeamento de Cartas Especiais (Multiplayer)
        if (carta.getTipo() == Carta.Tipo_Carta.PERDEU_A_VEZ) {
            return "cardJoker.png";
        } else if (carta.getTipo() == Carta.Tipo_Carta.JOGUE_DE_NOVO) {
            return "cardHeartsK.png";
        } else if (carta.getTipo() == Carta.Tipo_Carta.DOBRO_PONTOS) {
            return "cardDiamondsA.png";
        }

        // 2. Mapeamento de Cartas Normais (Espadas)
        String[] valoresSpades = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        int id = carta.getId();

        if (id >= 0 && id < valoresSpades.length) {
            return "cardSpades" + valoresSpades[id] + ".png";
        }

        // Caso o ID seja maior do que os 12 pares padrão
        return "cardSpadesA.png";
    }

    private Image carregarImagem(String caminho) {
        try {
            URL url = getClass().getResource(caminho);
            if (url != null) {
                return new ImageIcon(url).getImage();
            }
            System.err.println("Imagem não encontrada: " + caminho);
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + caminho);
        }
        return null;
    }
}