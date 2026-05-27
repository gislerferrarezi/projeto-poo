package src.jogodamemoria.controller;

import src.jogodamemoria.model.Carta;
import src.jogodamemoria.model.Tabuleiro;
import src.jogodamemoria.model.Jogador;

public class JogoController {

    private Tabuleiro tabuleiro;
    private Jogador jogador;
    private Jogador jogador1;
    private Jogador jogador2;

    private Carta primeiraCarta = null;
    private Carta segundaCarta = null;

    private int totalParesObjetivo;
    private int totalParesFormados = 0;
    private int tentativas = 0;
    private int jogadorAtual = 0; // 0 = Jogador 1, 1 = Jogador 2

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

    public JogoController(Tabuleiro tabuleiro, Jogador jogador1, Jogador jogador2) {
        this.tabuleiro = tabuleiro;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;        
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

                if (jogador1 != null) {
                    if (jogadorAtual == 0) {
                        jogador1.ganharPonto();
                    } else {
                        jogador2.ganharPonto();
                    }
                } else {
                    jogador.ganharPonto(); 
                }                
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
                
                if (jogador1 != null) { 
                    if (jogadorAtual == 0) {
                        jogadorAtual++;
                    } else {
                        jogadorAtual--;
                    }
                }

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
    
    public int getJogadorAtual() {
        return jogadorAtual;
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public Jogador compararPontos(Jogador j1, Jogador j2) {
        if (j1.getPontuacao() > j2.getPontuacao()) {
            return j1;
        } else if (j2.getPontuacao() > j1.getPontuacao()) {
            return j2;
        } else {
            return null; 
        }
    }
}