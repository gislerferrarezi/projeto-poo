package src.jogodamemoria.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;
import src.jogodamemoria.model.Jogador;
import src.jogodamemoria.model.Tabuleiro;

public class JanelaVitoriaSingle extends JDialog {

    private JButton btnMenu;
    private JButton btnJogarNovamente;
    private JFrame janelaPrincipalJogo;

    private Jogador jogador;
    private Tabuleiro tabuleiroAntigo;

    public JanelaVitoriaSingle(JFrame janelaPai, int tentativas, String tempoFinal, Jogador jogador,
            Tabuleiro tabuleiroAntigo) {
        super(janelaPai, "Fim de Jogo!", true);
        this.janelaPrincipalJogo = janelaPai;
        this.jogador = jogador;
        this.tabuleiroAntigo = tabuleiroAntigo;

        setSize(450, 300);
        setLocationRelativeTo(janelaPai);
        setLayout(new BorderLayout(20, 20));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // NORTE
        JLabel lblParabens = new JLabel("PARABÉNS! VOCÊ VENCEU!", JLabel.CENTER);
        lblParabens.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblParabens, BorderLayout.NORTH);

        // CENTRO
        JPanel painelStatus = new JPanel(new GridLayout(2, 1, 10, 10));
        JLabel lblTentativas = new JLabel("Tentativas: " + tentativas, JLabel.CENTER);
        JLabel lbltempoFinal = new JLabel("Tempo: " + tempoFinal, JLabel.CENTER);
        painelStatus.add(lblTentativas);
        painelStatus.add(lbltempoFinal);
        add(painelStatus, BorderLayout.CENTER);

        // SUL
        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 15, 0));
        btnMenu = new JButton("Voltar ao Menu");
        btnJogarNovamente = new JButton("Jogar Novamente");
        
        painelBotoes.add(btnMenu);
        painelBotoes.add(btnJogarNovamente);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    // GETTERS para o Controller alcançar os botões e os dados necessários
    public JButton getBtnMenu() { return btnMenu; }
    public JButton getBtnJogarNovamente() { return btnJogarNovamente; }
    public JFrame getJanelaPrincipalJogo() { return janelaPrincipalJogo; }
    public Jogador getJogador() { return jogador; }
    public Tabuleiro getTabuleiroAntigo() { return tabuleiroAntigo; }
}