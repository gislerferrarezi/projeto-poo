package jogodamemoria.view.componentes;

import javax.swing.JLabel;
import javax.swing.Timer;

public class EfeitoDigitacao {

    private JLabel label;
    private String[] frases;

    private int fraseAtual = 0;
    private int caractereAtual = 0;

    private boolean digitando = true;
    private int pausa = 0;

    private Timer timer;

    public EfeitoDigitacao(JLabel label, String[] frases) {
        this.label = label;
        this.frases = frases;

        // Quanto maior, mais lenta a animação
        timer = new Timer(100, e -> animar());
    }

    public void iniciar() {
        timer.start();
    }

    public void parar() {
        timer.stop();
    }

    private void animar() {

        String texto = frases[fraseAtual];

        if (pausa > 0) {
            pausa--;
            return;
        }

        if (digitando) {

            if (caractereAtual < texto.length()) {
                caractereAtual++;
                label.setText(texto.substring(0, caractereAtual) + " |");
            } else {
                label.setText(texto);
                digitando = false;
                pausa = 20; // espera antes de apagar
            }

        } else {

            if (caractereAtual > 0) {
                caractereAtual--;
                label.setText(texto.substring(0, caractereAtual) + " |");
            } else {
                fraseAtual = (fraseAtual + 1) % frases.length;
                digitando = true;
                pausa = 8; // espera antes da próxima frase
            }

        }
    }
}