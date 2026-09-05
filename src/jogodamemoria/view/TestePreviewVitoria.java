package jogodamemoria.view;

import javax.swing.*;

import jogodamemoria.model.Jogador;

public class TestePreviewVitoria {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Jogador jogador1 = new Jogador("lea");
            for (int i = 0; i < 1; i++)
                jogador1.ganharPonto();

            Jogador jogador2 = new Jogador("gisler");
            for (int i = 0; i < 13; i++)
                jogador2.ganharPonto();        

            JFrame janelaPai = new JFrame();
            janelaPai.setSize(800, 600);
            janelaPai.setLocationRelativeTo(null);
            janelaPai.setVisible(true);

            JanelaVitoriaMultiplayer janelaVitoria = new JanelaVitoriaMultiplayer(
                    janelaPai, jogador2, jogador1, jogador2, null);

            janelaVitoria.setVisible(true);
        });
    }
}