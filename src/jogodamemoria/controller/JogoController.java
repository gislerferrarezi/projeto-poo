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
        this.totalParesObjetivo = tabuleiro.getTamanho() / 2;
        inicializarCronometro();
    }

    // --- LÓGICA PRINCIPAL DO CLIQUE DA CARTA ---
    public ResultadoJogada processarCliqueCarta(int indice) {
        Carta cartaClicada = tabuleiro.getCarta(indice);

        if (cartaClicada.isDescoberta() || cartaClicada.isVirada()) {
            return ResultadoJogada.IGNORAR;
        }

        if (cartaClicada.getTipo() == Tipo_Carta.PERDEU_A_VEZ) {
            cartaClicada.virar();

            if (primeiraCarta != null) {
                primeiraCarta.esconder();
                primeiraCarta = null;
            }

            alternarTurnoPorPunicao();
            return ResultadoJogada.PERDEU_A_VEZ;
        }

        // --- CONTROLE DO PRIMEIRO CLIQUE ---
        if (primeiraCarta == null) {
            primeiraCarta = cartaClicada;
            primeiraCarta.virar();
            return ResultadoJogada.PRIMEIRA_CARTA_VIRADA;
        }

        // --- CONTROLE DO SEGUNDO CLIQUE ---
        if (segundaCarta == null && cartaClicada != primeiraCarta) {
            segundaCarta = cartaClicada;
            segundaCarta.virar();
            tentativas++;

            pararCronometro();

            if (primeiraCarta.getId() == segundaCarta.getId()) {
                primeiraCarta.setDescoberta(true);
                segundaCarta.setDescoberta(true);

                int pontosDaJogada = 1;
                if (primeiraCarta.getTipo() == Tipo_Carta.DOBRO_PONTOS) {
                    pontosDaJogada = 2;
                }

                if (jogador1 != null) {
                    if (jogadorAtual == 0) {
                        for (int k = 0; k < pontosDaJogada; k++)
                            jogador1.ganharPonto();
                    } else {
                        for (int k = 0; k < pontosDaJogada; k++)
                            jogador2.ganharPonto();
                    }
                } else {
                    for (int k = 0; k < pontosDaJogada; k++)
                        jogador.ganharPonto();
                }

                totalParesFormados++;

                if (primeiraCarta.getTipo() == Tipo_Carta.JOGUE_DE_NOVO) {
                    jogadorGanhouBonusTurno = true;
                }

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
                    if (jogadorGanhouBonusTurno) {
                        jogadorGanhouBonusTurno = false;
                    } else {
                        // Passa o turno normalmente
                        if (jogadorAtual == 0) {
                            jogadorAtual++;
                        } else {
                            jogadorAtual--;
                        }
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

    public int getTempoRestanteJ1() { return tempoRestanteJ1; }
    public int getTempoRestanteJ2() { return tempoRestanteJ2; }

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