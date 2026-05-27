package src.jogodamemoria.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Font;

import javax.swing.*;
import src.jogodamemoria.model.Tabuleiro;
import src.jogodamemoria.model.Jogador;
import src.jogodamemoria.controller.JogoController;
import src.jogodamemoria.controller.NavegacaoController;

public class JanelaMultiplayer extends JFrame implements ActionListener {

    private Tabuleiro tabuleiro;
    private Jogador jogador1;
    private Jogador jogador2;
    
    private JogoController gerenciador;
    private NavegacaoController navegacaoController;
    private boolean tabuleiroBloqueado = false;
    private int indexPrimeiraCarta = -1;
    
    private JPanel painelJogador1;
    private JPanel painelJogador2;
    private JLabel lblPontuacaoJ1;
    private JLabel lblPontuacaoJ2;
    private JPanel painelTabuleiro;
    private ArrayList<JButton> botoesCartas = new ArrayList<>();

    public JanelaMultiplayer(Tabuleiro tabuleiro, Jogador jogador1, Jogador jogador2, NavegacaoController navegacaoController) {
        this.tabuleiro = tabuleiro;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.navegacaoController = navegacaoController;        
        
        this.gerenciador = new JogoController(tabuleiro, jogador1, jogador2);

        setTitle("Jogo da Memória - Modo Multiplayer");
        setSize(1280, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Configuração de linhas e colunas (12 cartas = 3x4 | 24 cartas = 4x6)
        int lines = (tabuleiro.getTamanho() == 12) ? 3 : 4;
        int colunas = (tabuleiro.getTamanho() == 12) ? 4 : 6;

        // NORTE - Jogador 1
        painelJogador1 = new JPanel(new FlowLayout());
        JLabel lblNomeJ1 = new JLabel("Jogador 1: " + jogador1.getNome() + "  |  ");
        lblPontuacaoJ1 = new JLabel("Pares: " + jogador1.getPontuacao());
        
        lblNomeJ1.setFont(new Font("Arial", Font.BOLD, 18));
        lblPontuacaoJ1.setFont(new Font("Arial", Font.BOLD, 18));

        painelJogador1.add(lblNomeJ1);
        painelJogador1.add(lblPontuacaoJ1);
        add(painelJogador1, BorderLayout.NORTH); 

        // CENTRO - Tabuleiro
        painelTabuleiro = new JPanel(new GridLayout(lines, colunas, 15, 15));

        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            JButton botao = new JButton("[ ? ]");
            botao.setFont(new Font("Arial", Font.BOLD, 24));

            botoesCartas.add(botao);
            painelTabuleiro.add(botao);
            botao.addActionListener(this);
        }

        add(painelTabuleiro, BorderLayout.CENTER);

        // SUL - Jogador 2
        painelJogador2 = new JPanel(new FlowLayout());
        JLabel lblNomeJ2 = new JLabel("Jogador 2: " + jogador2.getNome() + "  |  ");
        lblPontuacaoJ2 = new JLabel("Pares: " + jogador2.getPontuacao());

        lblNomeJ2.setFont(new Font("Arial", Font.BOLD, 18));
        lblPontuacaoJ2.setFont(new Font("Arial", Font.BOLD, 18));

        painelJogador2.add(lblNomeJ2);
        painelJogador2.add(lblPontuacaoJ2);
        add(painelJogador2, BorderLayout.SOUTH); 
        
        atualizarHUD();
    }
    
    private void atualizarHUD() {
        lblPontuacaoJ1.setText("Pares: " + jogador1.getPontuacao());
        lblPontuacaoJ2.setText("Pares: " + jogador2.getPontuacao());

        if (gerenciador.getJogadorAtual() == 0) {
            painelJogador1.setBackground(new Color(173, 216, 230)); // Azul Claro para J1
            painelJogador2.setBackground(null); // Remove o fundo do J2
        } else {
            painelJogador1.setBackground(null); // Remove o fundo do J1
            painelJogador2.setBackground(new Color(255, 182, 193)); // Rosa/Vermelho Claro para J2
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (tabuleiroBloqueado) return;

        for (int i = 0; i < botoesCartas.size(); i++) {
            if (e.getSource() == botoesCartas.get(i)) {

                JogoController.ResultadoJogada resultado = gerenciador.processarCliqueCarta(i);

                switch (resultado) {
                    case PRIMEIRA_CARTA_VIRADA:
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                        indexPrimeiraCarta = i;
                        break;

                    case ACERTOU_PAR:
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                        atualizarHUD(); 
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
                            atualizarHUD(); 
                        });
                        timer.setRepeats(false);
                        timer.start();
                        break;

                    case VITORIA:
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                        atualizarHUD();                        
                       
                        Jogador vencedor = gerenciador.compararPontos(jogador1, jogador2);                        
                        
                        navegacaoController.exibirVitoriaMultiplayer(this, vencedor, jogador1, jogador2);
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