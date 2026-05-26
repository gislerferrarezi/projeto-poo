package src.jogodamemoria.controller;

import src.jogodamemoria.model.Carta;
import src.jogodamemoria.model.Tabuleiro;
import src.jogodamemoria.model.Jogador;

public class JogoController {

    private Tabuleiro tabuleiro;
    private Jogador jogador;
    
    private Carta primeiraCarta = null;
    private Carta segundaCarta = null;

    private int totalParesObjetivo;
    private int totalParesFormados = 0;
    private int tentativas = 0;

    public enum ResultadoJogada {
        IGNORAR,
        PRIMEIRA_CARTA_VIRADA,
        ACERTOU_PAR,
        ERROU_PAR,
        VITORIA
    }    

    public JogoController(Tabuleiro tabuleiro, Jogador jogador) {
        this.tabuleiro = tabuleiro;
        this.jogador = jogador;
        this.totalParesObjetivo = tabuleiro.getTamanho() / 2;
    }

    public ResultadoJogada processarCliqueCarta(int indice) {
        Carta cartaClicada = tabuleiro.getCarta(indice);

        if (cartaClicada.isDescoberta() || cartaClicada.isVirada()) {
            return ResultadoJogada.IGNORAR;
        }

        if (primeiraCarta == null) {
            primeiraCarta = cartaClicada;
            primeiraCarta.virar();
            return ResultadoJogada.PRIMEIRA_CARTA_VIRADA;
        }
      
        if (segundaCarta == null && cartaClicada != primeiraCarta) {
            segundaCarta = cartaClicada;
            segundaCarta.virar();
            tentativas++;

            if (primeiraCarta.getId() == segundaCarta.getId()) {
                primeiraCarta.setDescoberta(true);
                segundaCarta.setDescoberta(true);
                jogador.ganharPonto();
                totalParesFormados++;

                primeiraCarta = null;
                segundaCarta = null;

                if (totalParesFormados == totalParesObjetivo) {
                    return ResultadoJogada.VITORIA;
                }
                return ResultadoJogada.ACERTOU_PAR;

            } else {
                primeiraCarta.esconder();
                segundaCarta.esconder();

                primeiraCarta = null;
                segundaCarta = null;
                return ResultadoJogada.ERROU_PAR;
            }
        }
        return ResultadoJogada.IGNORAR;
    }

    public int getTentativas() {
        return tentativas;
    }

    public int getTotalParesFormados() {
        return totalParesFormados;
    }
}