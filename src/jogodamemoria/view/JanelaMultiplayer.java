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
import src.jogodamemoria.model.Carta;
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

    private JPanel painelJogador1;
    private JPanel painelJogador2;
    private JLabel lblPontuacaoJ1;
    private JLabel lblPontuacaoJ2;
    private JLabel lblTempo; // Novo Label para o Cronômetro
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

        // Configuração de linhas e colunas    
        int lines = 5;
        int colunas = 6;

        // --- PAINEL TOPO (Contém Jogador 1 e Cronômetro) ---
        JPanel painelTopo = new JPanel(new BorderLayout());
        
        painelJogador1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblNomeJ1 = new JLabel("Jogador 1: " + jogador1.getNome() + "  |  ");
        lblPontuacaoJ1 = new JLabel("Pares: " + jogador1.getPontuacao());
        lblNomeJ1.setFont(new Font("Arial", Font.BOLD, 18));
        lblPontuacaoJ1.setFont(new Font("Arial", Font.BOLD, 18));
        painelJogador1.add(lblNomeJ1);
        painelJogador1.add(lblPontuacaoJ1);

        lblTempo = new JLabel("Tempo: 30s", SwingConstants.CENTER);
        lblTempo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTempo.setForeground(Color.RED);

        painelTopo.add(painelJogador1, BorderLayout.WEST);
        painelTopo.add(lblTempo, BorderLayout.CENTER);
        
        add(painelTopo, BorderLayout.NORTH); 

        // --- CENTRO - Tabuleiro ---
        painelTabuleiro = new JPanel(new GridLayout(lines, colunas, 15, 15));

        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            JButton botao = new JButton("[ ? ]");
            botao.setFont(new Font("Arial", Font.BOLD, 24));

            botoesCartas.add(botao);
            painelTabuleiro.add(botao);
            botao.addActionListener(this);
        }

        add(painelTabuleiro, BorderLayout.CENTER);

        // --- SUL - Jogador 2 ---
        painelJogador2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblNomeJ2 = new JLabel("Jogador 2: " + jogador2.getNome() + "  |  ");
        lblPontuacaoJ2 = new JLabel("Pares: " + jogador2.getPontuacao());

        lblNomeJ2.setFont(new Font("Arial", Font.BOLD, 18));
        lblPontuacaoJ2.setFont(new Font("Arial", Font.BOLD, 18));

        painelJogador2.add(lblNomeJ2);
        painelJogador2.add(lblPontuacaoJ2);
        add(painelJogador2, BorderLayout.SOUTH); 
        
        // --- CONFIGURAÇÃO DO CRONÔMETRO ---
        gerenciador.configurarCallbacksCronometro(
            () -> { // Ação que roda a cada segundo (Tick)
                lblTempo.setText("Tempo: " + gerenciador.getTempoRestante() + "s");
            },
            () -> { // Ação que roda quando o tempo acaba (Timeout)
                tabuleiroBloqueado = true;
                JOptionPane.showMessageDialog(this, "Tempo esgotado! Passou a vez.", "Alerta", JOptionPane.WARNING_MESSAGE);
                sincronizarCartasVisuais();
                atualizarHUD();               
                tabuleiroBloqueado = false;
            }
        );

        atualizarHUD();
        gerenciador.iniciarCronometro(); // Dispara o relógio assim que a tela abre
    }
    
    private void atualizarHUD() {
        lblPontuacaoJ1.setText("Pares: " + jogador1.getPontuacao());
        lblPontuacaoJ2.setText("Pares: " + jogador2.getPontuacao());
        lblTempo.setText("Tempo: " + gerenciador.getTempoRestante() + "s");

        if (gerenciador.getJogadorAtual() == 0) {
            painelJogador1.setBackground(new Color(173, 216, 230)); // Azul Claro para J1
            painelJogador2.setBackground(null); 
        } else {
            painelJogador1.setBackground(null); 
            painelJogador2.setBackground(new Color(255, 182, 193)); // Rosa/Vermelho Claro para J2
        }
    }
    
    private void sincronizarCartasVisuais() {
        for (int i = 0; i < botoesCartas.size(); i++) {
            Carta carta = tabuleiro.getCarta(i);
            if (carta.isDescoberta() || carta.isVirada()) {
                botoesCartas.get(i).setText(carta.getValor());
            } else {
                botoesCartas.get(i).setText("[ ? ]");
            }
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
                        break;

                    case ACERTOU_PAR:
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                        atualizarHUD();                         
                        break;

                    case ERROU_PAR:
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor());
                        tabuleiroBloqueado = true;

                        Timer timer = new Timer(1000, evento -> {
                            sincronizarCartasVisuais();
                            tabuleiroBloqueado = false;
                            atualizarHUD(); 
                        });
                        timer.setRepeats(false);
                        timer.start();                       
                        break;

                    case PERDEU_A_VEZ:
                        botoesCartas.get(i).setText(tabuleiro.getCarta(i).getValor()); 
                        tabuleiroBloqueado = true;
                        
                        Timer timerPunicao = new Timer(1500, evento -> {
                            sincronizarCartasVisuais(); 
                            tabuleiroBloqueado = false;
                            atualizarHUD();
                        });
                        timerPunicao.setRepeats(false);
                        timerPunicao.start();                      
                        break;

                    case VITORIA:
                        gerenciador.pararCronometro();
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