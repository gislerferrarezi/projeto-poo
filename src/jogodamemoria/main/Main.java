package jogodamemoria.main;

import javax.swing.SwingUtilities;
import jogodamemoria.controller.NavegacaoController;
import jogodamemoria.view.JanelaMenuPrincipal;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JanelaMenuPrincipal menu = new JanelaMenuPrincipal();      
                NavegacaoController controller = new NavegacaoController(menu);
                controller.iniciar(); 
            }
        });
    }
}