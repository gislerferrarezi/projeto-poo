package jogodamemoria.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.*;

import jogodamemoria.model.Jogador;
import jogodamemoria.model.Tabuleiro;

public class JanelaVitoriaMultiplayer extends JDialog {

    private JButton btnMenu;
    private JButton btnJogarNovamente;
    private JFrame janelaPrincipalJogo;

    private Jogador jogador1;
    private Jogador jogador2;
    private Tabuleiro tabuleiroAntigo;
   
    public JanelaVitoriaMultiplayer(JFrame janelaPai, Jogador vencedor, Jogador jogador1, Jogador jogador2, Tabuleiro tabuleiroAntigo) {
        super(janelaPai, "Fim de Jogo!", true);
        this.janelaPrincipalJogo = janelaPai;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.tabuleiroAntigo = tabuleiroAntigo;

        setSize(450, 300);
        setLocationRelativeTo(janelaPai);
        setLayout(new BorderLayout(20, 20));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // --- NORTE (Título dinâmico dependendo se teve ganhador ou empate) ---
        String textoTitulo;
        if (vencedor != null) {
            textoTitulo = vencedor.getNome().toUpperCase() + " VENCEU!";
        } else {
            textoTitulo = "DEU EMPATE!";
        }
        
        JLabel lblTitulo = new JLabel(textoTitulo, JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        // Dá uma cor diferente se for empate
        if (vencedor == null) lblTitulo.setForeground(Color.GRAY);
        else lblTitulo.setForeground(new Color(0, 150, 0)); // Verde escuro
        
        add(lblTitulo, BorderLayout.NORTH);

        // --- CENTRO (Placar Final) ---
        JPanel painelStatus = new JPanel(new GridLayout(3, 1, 5, 5));
        
        JLabel lblSubtitulo = new JLabel("Placar Final:", JLabel.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel lblPontosJ1 = new JLabel(jogador1.getNome() + ": " + jogador1.getPontuacao() + " pares", JLabel.CENTER);
        JLabel lblPontosJ2 = new JLabel(jogador2.getNome() + ": " + jogador2.getPontuacao() + " pares", JLabel.CENTER);
        
        lblPontosJ1.setFont(new Font("Arial", Font.PLAIN, 16));
        lblPontosJ2.setFont(new Font("Arial", Font.PLAIN, 16));

        painelStatus.add(lblSubtitulo);
        painelStatus.add(lblPontosJ1);
        painelStatus.add(lblPontosJ2);
        add(painelStatus, BorderLayout.CENTER);

        // --- SUL (Botões) ---
        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 15, 0));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        
        btnMenu = new JButton("Voltar ao Menu");
        btnJogarNovamente = new JButton("Revanche"); 

        painelBotoes.add(btnMenu);
        painelBotoes.add(btnJogarNovamente);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    // --- GETTERS para o Controlador ---
    public JButton getBtnMenu() { return btnMenu; }
    public JButton getBtnJogarNovamente() { return btnJogarNovamente; }
    public JFrame getJanelaPrincipalJogo() { return janelaPrincipalJogo; }
    public Jogador getJogador1() { return jogador1; }
    public Jogador getJogador2() { return jogador2; }
    public Tabuleiro getTabuleiroAntigo() { return tabuleiroAntigo; }
}