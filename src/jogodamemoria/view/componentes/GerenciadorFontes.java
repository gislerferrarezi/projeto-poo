package jogodamemoria.view.componentes;

import java.awt.Font;
import java.io.InputStream;

public class GerenciadorFontes {

    // Guarda a fonte na memória para não precisar ler o arquivo de novo toda hora
    private static Font fontePrincipal;

    public static Font obterFonte(int estilo, float tamanho) {
        if (fontePrincipal == null) {
            try {
                InputStream is = GerenciadorFontes.class
                        .getResourceAsStream("/jogodamemoria/recursos/fontes/font_pixeladona.ttf");
                fontePrincipal = Font.createFont(Font.TRUETYPE_FONT, is);
            } catch (Exception e) {
                System.out.println("Erro ao carregar a fonte customizada. Usando fonte padrão.");
                // Fonte de segurança caso dê erro
                fontePrincipal = new Font("Segoe UI", Font.PLAIN, 12);
            }
        }
        return fontePrincipal.deriveFont(estilo, tamanho);
    }
}