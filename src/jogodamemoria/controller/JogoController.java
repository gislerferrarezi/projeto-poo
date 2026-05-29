package src.jogodamemoria.controller;

import src.jogodamemoria.model.Carta;
import src.jogodamemoria.model.Tabuleiro;
import src.jogodamemoria.model.Carta.Tipo_Carta;
import src.jogodamemoria.model.Jogador;
import javax.swing.Timer;

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

    // --- VARIÁVEIS DO CRONÔMETRO E BÔNUS ---
    private Timer cronometro;
    private int tempoRestanteJ1 = 30;
    private int tempoRestanteJ2 = 30;
    private boolean jogadorGanhouBonusTurno = false;
    private Runnable onTickCallback; // Permite que a View atualize o texto do contador de segundos
    private Runnable onTimeoutCallback; // Permite que a View atualize a tela quando o tempo esgotar

    public enum ResultadoJogada {
        IGNORAR,
        PRIMEIRA_CARTA_VIRADA,
        ACERTOU_PAR,
        ERROU_PAR,
        PERDEU_A_VEZ,
        EFEITO_ESPECIAL_ATIVADO,
        VITORIA
    }

    // Construtor Modo Solo
    public JogoController(Tabuleiro tabuleiro, Jogador jogador) {
        this.tabuleiro = tabuleiro;
        this.jogador = jogador;
        this.totalParesObjetivo = tabuleiro.getTamanho() / 2;
    }

    // Construtor Modo Multiplayer
    public JogoController(Tabuleiro tabuleiro, Jogador jogador1, Jogador jogador2) {
        this.tabuleiro = tabuleiro;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        int cartasNormais = 0;
        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            // Conta APENAS as cartas normais para formar os pares
            if (tabuleiro.getCarta(i).getTipo() == Tipo_Carta.NORMAL) {
                cartasNormais++;
            }
        }
        this.totalParesObjetivo = cartasNormais / 2;
        inicializarCronometro();
    }

    // --- LÓGICA PRINCIPAL DO CLIQUE DA CARTA ---
    public ResultadoJogada processarCliqueCarta(int indice) {
        Carta cartaClicada = tabuleiro.getCarta(indice);

        if (cartaClicada.isDescoberta() || cartaClicada.isVirada()) {
            return ResultadoJogada.IGNORAR;
        }

        // --- INTERCEPTA TODAS AS CARTAS ESPECIAIS (Ação Instantânea) ---
        if (cartaClicada.getTipo() != Tipo_Carta.NORMAL) {
            cartaClicada.virar();
            cartaClicada.setDescoberta(true);
            
            // Aplica os efeitos na hora
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
                    return ResultadoJogada.EFEITO_ESPECIAL_ATIVADO;

                case DOBRO_PONTOS:
                    multiplicadorPontos = multiplicadorPontos * 2;
                    return ResultadoJogada.EFEITO_ESPECIAL_ATIVADO;

                default:
                    return ResultadoJogada.IGNORAR;
            }
        }

        // --- CONTROLE DO PRIMEIRO CLIQUE (Apenas Cartas Normais chegam aqui) ---
        if (primeiraCarta == null) {
            primeiraCarta = cartaClicada;
            primeiraCarta.virar();
            return ResultadoJogada.PRIMEIRA_CARTA_VIRADA;
        }

        // --- CONTROLE DO SEGUNDO CLIQUE (Apenas Cartas Normais) ---
        if (segundaCarta == null && cartaClicada != primeiraCarta) {
            segundaCarta = cartaClicada;
            segundaCarta.virar();
            tentativas++;

            pararCronometro();

            // Verifica se formou par
            if (primeiraCarta.getId() == segundaCarta.getId()) {
                primeiraCarta.setDescoberta(true);
                segundaCarta.setDescoberta(true);

                int pontosGanhos = 1 * multiplicadorPontos;

                if (jogador1 != null) {
                    if (jogadorAtual == 0) {
                        for (int k = 0; k < pontosGanhos; k++)
                            jogador1.ganharPonto();
                    } else {
                        for (int k = 0; k < pontosGanhos; k++)
                            jogador2.ganharPonto();
                    }
                } else {
                    for (int k = 0; k < pontosGanhos; k++)
                        jogador.ganharPonto();
                }
                multiplicadorPontos = 1;

                totalParesFormados++;

                primeiraCarta = null;
                segundaCarta = null;

                // Verifica vitória
                if (totalParesFormados == totalParesObjetivo) {
                    return ResultadoJogada.VITORIA;
                }

                return ResultadoJogada.ACERTOU_PAR;

            } else {
                // Errou o par
                primeiraCarta.esconder();
                segundaCarta.esconder();

                multiplicadorPontos = 1;

                if (jogador1 != null) {
                    if (jogadorGanhouBonusTurno) {
                        jogadorGanhouBonusTurno = false;
                    } else {
                        alternarTurnoPorPunicao();
                    }
                }

                primeiraCarta = null;
                segundaCarta = null;

                return ResultadoJogada.ERROU_PAR;
            }
        }
        return ResultadoJogada.IGNORAR;
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
        if (primeiraCarta != null) {
            primeiraCarta.esconder();
            primeiraCarta = null;
        }
        if (segundaCarta != null) {
            segundaCarta.esconder();
            segundaCarta = null;
        }

        // Alterna o jogador
        jogadorAtual = (jogadorAtual == 0) ? 1 : 0;

        // Reseta os dois relógios internos para 30
        tempoRestanteJ1 = 30;
        tempoRestanteJ2 = 30;

        if (onTimeoutCallback != null) {
            onTimeoutCallback.run();
        }
    }

    private void alternarTurnoPorPunicao() {
        if (jogador1 != null) {
            if (jogadorAtual == 0) {
                jogadorAtual = 1;
            } else {
                jogadorAtual = 0;
            }
        }
    }

    public void iniciarCronometro() {
        if (cronometro != null)
            cronometro.start();
    }

    public void pararCronometro() {
        if (cronometro != null)
            cronometro.stop();
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