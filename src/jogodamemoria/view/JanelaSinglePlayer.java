package src.jogodamemoria.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import src.jogodamemoria.model.Tabuleiro;
import src.jogodamemoria.model.Jogador;
import src.jogodamemoria.controller.JogoController;
import src.jogodamemoria.controller.JogoController.ResultadoJogada;
import src.jogodamemoria.controller.NavegacaoController;

public class JanelaSinglePlayer extends JFrame implements ActionListener {

    private Tabuleiro tabuleiro;
    private Jogador jogador;

    private NavegacaoController navegacaoController;
    private JogoController gerenciador;
    private int indexPrimeiraCarta = -1;

    private JLabel lblPontos;
    private boolean tabuleiroBloqueado = false;

    private JLabel lblTempo;
    private int segundosDecorridos = 0;
    private Timer cronometro;

    private JPanel painelTabuleiro;
    ArrayList<JButton> botoesCartas = new ArrayList<>();

    public JanelaSinglePlayer(Tabuleiro tabuleiro, Jogador jogador, NavegacaoController navegacaoController) {
        this.tabuleiro = tabuleiro;
        this.jogador = jogador;
        this.navegacaoController = navegacaoController;
        this.gerenciador = new JogoController(tabuleiro, jogador);

        setTitle("Jogo da Memória - Modo Solo");
        setSize(1280, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(20, 20));

        String textoTitulo = "";
        int colunas = 0;
        int lines = 0;

        if (tabuleiro.getTamanho() == 12) {
            textoTitulo = "UM JOGADOR - MODO FÁCIL";
            colunas = 4;
            lines = 3;
        } else {
            textoTitulo = "UM JOGADOR - MODO PADRÃO";
            colunas = 5;
            lines = 4;
        }

        // SUPERIOR
        JLabel labelTitulo = new JLabel(textoTitulo, JLabel.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel superior = new JPanel(new GridLayout(1, 1));
        superior.add(labelTitulo);
        add(superior, BorderLayout.NORTH);

        // CENTRO
        painelTabuleiro = new JPanel(new GridLayout(lines, colunas, 15, 15));

        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            JButton botao = new JButton("[ ? ]");
            botao.setFont(new Font("Arial", Font.BOLD, 24));

            botoesCartas.add(botao);
            painelTabuleiro.add(botao);
            botao.addActionListener(this);
        }

        add(painelTabuleiro, BorderLayout.CENTER);

        // INFERIOR
        JLabel lblNome = new JLabel("Jogador: " + jogador.getNome(), JLabel.CENTER);

        lblPontos = new JLabel("Pares Feitos: 0", JLabel.CENTER);
        lblTempo = new JLabel("Tempo: 00:00", JLabel.CENTER);

        Font fonteHUD = new Font("Arial", Font.PLAIN, 18);
        lblNome.setFont(fonteHUD);
        lblPontos.setFont(fonteHUD);
        lblTempo.setFont(fonteHUD);

        JPanel inferior = new JPanel(new GridLayout(1, 3));
        inferior.add(lblNome);
        inferior.add(lblPontos);
        inferior.add(lblTempo);

        add(inferior, BorderLayout.SOUTH);

        cronometro = new Timer(1000, evento -> {
            segundosDecorridos++;

            int minutos = segundosDecorridos / 60;
            int segundos = segundosDecorridos % 60;
            String tempoFormatado = String.format("Tempo: %02d:%02d", minutos, segundos);

            lblTempo.setText(tempoFormatado);
        });

        cronometro.start();

        System.out.println("--- GABARITO DO TABULEIRO ---");
        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            System.out.println("Botão " + i + ": " + tabuleiro.getCarta(i).getValor());
        }
        System.out.println("-----------------------------");

        setVisible(true);
    }

    public void reiniciarJogo() { 
        this.tabuleiro = new Tabuleiro(this.tabuleiro.getTamanho() / 2);
       
        this.jogador.resetarPontos();
       
        this.gerenciador = new JogoController(this.tabuleiro, this.jogador);
        this.indexPrimeiraCarta = -1;
        this.tabuleiroBloqueado = false;

        cronometro.stop();
        segundosDecorridos = 0;
        lblTempo.setText("Tempo: 00:00");
        lblPontos.setText("Pares Feitos: 0");

        painelTabuleiro.removeAll();
        botoesCartas.clear();

        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            JButton botao = new JButton("[ ? ]");
            botao.setFont(new Font("Arial", Font.BOLD, 24));

            botoesCartas.add(botao);
            painelTabuleiro.add(botao);
            botao.addActionListener(this);
        }

        painelTabuleiro.revalidate();
        painelTabuleiro.repaint();

        cronometro.start();        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (tabuleiroBloqueado)
            return;

        for (int i = 0; i < botoesCartas.size(); i++) {
            if (e.getSource() == botoesCartas.get(i)) {

                ResultadoJogada resultado = gerenciador.processarCliqueCarta(i);

                switch (resultado) {
                    case PRIMEIRA_CARTA_VIRADA:
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                        indexPrimeiraCarta = i;
                        break;

                    case ACERTOU_PAR:
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                        lblPontos.setText("Pares Feitos: " + jogador.getPontuacao());
                        indexPrimeiraCarta = -1;
                        break;

                    case ERROU_PAR:
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                        tabuleiroBloqueado = true;

                        int pBotao = indexPrimeiraCarta;
                        int sBotao = i;

                        Timer timer = new Timer(1000, evento -> {
                            botoesCartas.get(pBotao).setText("[ ? ]");
                            botoesCartas.get(sBotao).setText("[ ? ]");
                            tabuleiroBloqueado = false;
                        });
                        timer.setRepeats(false);
                        timer.start();
                        break;

                    case VITORIA:
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                        lblPontos.setText("Pares Feitos: " + jogador.getPontuacao());
                        cronometro.stop();

                        navegacaoController.exibirVitoria(this, gerenciador.getTentativas(), lblTempo.getText(),
                                jogador, tabuleiro);
                        break;

                    case IGNORAR:
                    default:
                        break;
                }
                break;
            }
        }
    }
}