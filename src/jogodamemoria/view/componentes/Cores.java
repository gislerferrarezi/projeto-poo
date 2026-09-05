package jogodamemoria.view.componentes;

import java.awt.Color;

public class Cores {

    // CORES PRINCIPAIS DA PALETA 
    public static final Color ROXO = new Color(91, 75, 155);
    public static final Color ROXO_HOVER = new Color(111, 91, 180);
    public static final Color ROXO_CLARO = new Color(140, 120, 210);

    // TEXTOS
    public static final Color TEXTO = new Color(26, 12, 46);
    public static final Color TEXTO_MUTED = new Color(60, 42, 88);
    public static final Color TEXTO_BRANCO = Color.WHITE;

    // VIDRO E SOMBRAS
    public static final Color VIDRO_FUNDO = new Color(255, 255, 255, 60);
    public static final Color VIDRO_BORDA = new Color(255, 255, 255, 100);
    public static final Color DIVISOR_TRANSLUCIDO = new Color(255, 255, 255, 130);
    public static final Color SOMBRA = new Color(40, 25, 80, 45);

    // TEMA DE VITÓRIA
    public static final Color TEXTO_VITORIA = new Color(40, 20, 75);
    public static final Color SUBTITULO_VITORIA = new Color(65, 35, 105);

    // CARDS DE PLACAR (MULTIPLAYER E SINGLEPLAYER)   
    public static final Color CARD_VENCEDOR_FUNDO = new Color(255, 205, 60, 180); 
    public static final Color CARD_VENCEDOR_BORDA = new Color(255, 230, 110); 
    public static final Color CARD_VENCEDOR_TEXTO_NOME = new Color(25, 12, 0);
    public static final Color CARD_VENCEDOR_TEXTO_PLACAR = new Color(35, 18, 0); 

    public static final Color CARD_PERDEDOR_FUNDO = new Color(255, 255, 255, 45);
    public static final Color CARD_PERDEDOR_BORDA = new Color(255, 255, 255, 90);
    public static final Color CARD_PERDEDOR_TEXTO_NOME = TEXTO;
    public static final Color CARD_PERDEDOR_TEXTO_PLACAR = TEXTO_MUTED;

    // BOTÕES DE VITÓRIA
    public static final Color BOTAO_VITORIA_PRIMARIO = new Color(230, 160, 20);
    public static final Color BOTAO_VITORIA_PRIMARIO_HOVER = new Color(250, 180, 40);

    public static final Color BOTAO_VITORIA_SECUNDARIO = ROXO;
    public static final Color BOTAO_VITORIA_SECUNDARIO_HOVER = ROXO_HOVER;

    private Cores() {
    }
}