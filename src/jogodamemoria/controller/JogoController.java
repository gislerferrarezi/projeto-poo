package jogodamemoria.controller;

import javax.swing.Timer;
import jogodamemoria.model.Carta;
import jogodamemoria.model.Carta.Tipo_Carta;
import jogodamemoria.model.Jogador;
import jogodamemoria.model.Tabuleiro;

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
    private int multiplicadorPontos = 1;

    // VARIÁVEIS DO CRONÔMETRO E BÔNUS
    private Timer cronometro;
    private int tempoRestanteJ1 = 30;
    private int tempoRestanteJ2 = 30;
    private boolean jogadorGanhouBonusTurno = false;
    private Runnable onTickCallback;
    private Runnable onTimeoutCallback;

    public enum ResultadoJogada {
        IGNORAR,
        PRIMEIRA_CARTA_VIRADA,
        ACERTOU_PAR,
        ERROU_PAR,
        PERDEU_A_VEZ,
        JOGUE_DE_NOVO_ATIVADO,
        DOBRO_PONTOS_ATIVADO,
        VITORIA
    }

    // Construtor Modo Solo
    public JogoController(Tabuleiro tabuleiro, Jogador jogador) {
        this.tabuleiro = tabuleiro;
        this.jogador = jogador;
        this.totalParesObjetivo = calcularParesObjetivo(tabuleiro);
    }

    // Construtor Modo Multiplayer
    public JogoController(Tabuleiro tabuleiro, Jogador jogador1, Jogador jogador2) {
        this.tabuleiro = tabuleiro;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.totalParesObjetivo = calcularParesObjetivo(tabuleiro);
        inicializarCronometro();
    }

    private int calcularParesObjetivo(Tabuleiro tab) {
        int cartasNormais = 0;
        for (int i = 0; i < tab.getTamanho(); i++) {
            if (tab.getCarta(i).getTipo() == Tipo_Carta.NORMAL) {
                cartasNormais++;
            }
        }
        return cartasNormais / 2;
    }

    // --- LÓGICA PRINCIPAL DO CLIQUE DA CARTA ---
    public ResultadoJogada processarCliqueCarta(int indice) {
        Carta cartaClicada = tabuleiro.getCarta(indice);

        if (cartaClicada.isDescoberta() || cartaClicada.isVirada()) {
            return ResultadoJogada.IGNORAR;
        }

        // INTERCEPTA TODAS AS CARTAS ESPECIAIS (Ação Instantânea)
        if (cartaClicada.getTipo() != Tipo_Carta.NORMAL) {
            cartaClicada.virar();
            cartaClicada.setDescoberta(true);

            switch (cartaClicada.getTipo()) {
                case PERDEU_A_VEZ:
                    if (primeiraCarta != null) {
                        primeiraCarta.esconder();
                        primeiraCarta = null;
                    }
                    multiplicadorPontos = 1;
                    alternarTurnoPorPunicao();
                    return ResultadoJogada.PERDEU_A_VEZ;

                case JOGUE_DE_NOVO:
                    jogadorGanhouBonusTurno = true;
                    return ResultadoJogada.JOGUE_DE_NOVO_ATIVADO;

                case DOBRO_PONTOS:
                    multiplicadorPontos = multiplicadorPontos * 2;
                    return ResultadoJogada.DOBRO_PONTOS_ATIVADO;

                default:
                    return ResultadoJogada.IGNORAR;
            }
        }

        // CONTROLE DO PRIMEIRO CLIQUE (Cartas Normais)
        if (primeiraCarta == null) {
            primeiraCarta = cartaClicada;
            primeiraCarta.virar();
            return ResultadoJogada.PRIMEIRA_CARTA_VIRADA;
        }

        // CONTROLE DO SEGUNDO CLIQUE (Cartas Normais)
        if (segundaCarta == null && cartaClicada != primeiraCarta) {
            segundaCarta = cartaClicada;
            segundaCarta.virar();
            tentativas++;

            pararCronometro();

            if (primeiraCarta.getId() == segundaCarta.getId()) {
                primeiraCarta.setDescoberta(true);
                segundaCarta.setDescoberta(true);

                int pontosGanhos = 1 * multiplicadorPontos;

                if (jogador1 != null) {
                    if (jogadorAtual == 0) {
                        jogador1.ganharPontos(pontosGanhos);
                    } else {
                        jogador2.ganharPontos(pontosGanhos);
                    }
                } else if (jogador != null) {
                    jogador.ganharPontos(pontosGanhos);
                }

                multiplicadorPontos = 1;
                totalParesFormados++;

                primeiraCarta = null;
                segundaCarta = null;

                if (totalParesFormados == totalParesObjetivo) {
                    return ResultadoJogada.VITORIA;
                }

                return ResultadoJogada.ACERTOU_PAR;

            } else {
                // ERROU O PAR: Mantém viradas na memória para o Swing desenhar primeiro
                multiplicadorPontos = 1;

                if (jogador1 != null) {
                    if (jogadorGanhouBonusTurno) {
                        jogadorGanhouBonusTurno = false;
                    } else {
                        alternarTurnoPorPunicao();
                    }
                }

                return ResultadoJogada.ERROU_PAR;
            }
        }
        return ResultadoJogada.IGNORAR;
    }

    // --- FINALIZA O TURNO COM ERRO (Chamado pelo Timer da Janela de ambos os
    // modos) ---
    public void finalizarTurnoErrado() {
        if (primeiraCarta != null) {
            primeiraCarta.esconder();
            primeiraCarta = null;
        }
        if (segundaCarta != null) {
            segundaCarta.esconder();
            segundaCarta = null;
        }
    }

    // --- MÉTODOS INTERNOS DO CRONÔMETRO (SWING TIMER) ---
    private void inicializarCronometro() {
        cronometro = new Timer(1000, e -> {
            if (jogadorAtual == 0) {
                tempoRestanteJ1--;
                if (tempoRestanteJ1 <= 0) {
                    cronometro.stop();
                    lidarTempoEsgotado();
                    return;
                }
            } else {
                tempoRestanteJ2--;
                if (tempoRestanteJ2 <= 0) {
                    cronometro.stop();
                    lidarTempoEsgotado();
                    return;
                }
            }

            if (onTickCallback != null) {
                onTickCallback.run();
            }
        });
    }

    private void lidarTempoEsgotado() {
        finalizarTurnoErrado();

        jogadorAtual = (jogadorAtual == 0) ? 1 : 0;
        tempoRestanteJ1 = 30;
        tempoRestanteJ2 = 30;

        if (onTimeoutCallback != null) {
            onTimeoutCallback.run();
        }
    }

    private void alternarTurnoPorPunicao() {
        if (jogador1 != null) {
            jogadorAtual = (jogadorAtual == 0) ? 1 : 0;
        }
    }

    public void iniciarCronometro() {
        if (cronometro != null) {
            cronometro.start();
        }
    }

    public void pararCronometro() {
        if (cronometro != null) {
            cronometro.stop();
        }
    }

    public void resetarCronometro() {
        if (cronometro != null) {
            cronometro.stop();
            tempoRestanteJ1 = 30;
            tempoRestanteJ2 = 30;

            if (onTickCallback != null) {
                onTickCallback.run();
            }
            cronometro.start();
        }
    }

    public void configurarCallbacksCronometro(Runnable onTick, Runnable onTimeout) {
        this.onTickCallback = onTick;
        this.onTimeoutCallback = onTimeout;
    }

    // --- GETTERS, SETTERS E COMPARADORES ---
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

    public int getTempoRestanteJ1() {
        return tempoRestanteJ1;
    }

    public int getTempoRestanteJ2() {
        return tempoRestanteJ2;
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