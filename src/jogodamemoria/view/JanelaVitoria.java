package src.jogodamemoria.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import src.jogodamemoria.model.Jogador;
import src.jogodamemoria.model.Tabuleiro;

public class JanelaVitoria extends JDialog implements ActionListener {

    private JButton btnMenu;
    private JButton btnJogarNovamente;
    private JFrame janelaPrincipalJogo;

    private Jogador jogador;
    private Tabuleiro tabuleiroAntigo;

    public JanelaVitoria(JFrame janelaPai, int tentativas, String tempoFinal, Jogador jogador,
            Tabuleiro tabuleiroAntigo) {
        super(janelaPai, "Fim de Jogo!", true);
        this.janelaPrincipalJogo = janelaPai;
        this.jogador = jogador;
        this.tabuleiroAntigo = tabuleiroAntigo;

        setSize(450, 300);
        setLocationRelativeTo(janelaPai);
        setLayout(new BorderLayout(20, 20));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // NORTE: Label de Parabéns
        JLabel lblParabens = new JLabel("PARABÉNS! VOCÊ VENCEU!", JLabel.CENTER);
        lblParabens.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblParabens, BorderLayout.NORTH);

        // CENTRO: Painel com as Estatísticas (Tentativas e Tempo)
        JPanel painelStatus = new JPanel(new GridLayout(2, 1, 10, 10));
        JLabel lblTentativas = new JLabel("Tentativas: " + tentativas, JLabel.CENTER);
        JLabel lbltempoFinal = new JLabel("Tempo: " + tempoFinal, JLabel.CENTER);

        painelStatus.add(lblTentativas);
        painelStatus.add(lbltempoFinal);

        add(painelStatus, BorderLayout.CENTER);

        // SUL: Painel com os botões [Voltar ao Menu] e [Jogar Novamente]
        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 15, 0));

        btnMenu = new JButton("Voltar ao Menu");
        btnJogarNovamente = new JButton("Jogar Novamente");

        btnMenu.addActionListener(this);
        btnJogarNovamente.addActionListener(this);

        painelBotoes.add(btnMenu);
        painelBotoes.add(btnJogarNovamente);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnMenu) {
            this.dispose();
            janelaPrincipalJogo.dispose();

            new JanelaMenu().setVisible(true);

        } else if (e.getSource() == btnJogarNovamente) {
            this.dispose();            

            int paresNecessarios = tabuleiroAntigo.getTamanho() / 2;
            Tabuleiro tabuleiroNovo = new Tabuleiro(paresNecessarios);
            jogador.resetarPontos();

            if (janelaPrincipalJogo instanceof JanelaSinglePlayer) {
                ((JanelaSinglePlayer) janelaPrincipalJogo).reiniciarJogo(tabuleiroNovo);
            }
        }
    }
}