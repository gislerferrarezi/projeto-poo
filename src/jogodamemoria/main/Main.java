package jogodamemoria.main;

import jogodamemoria.controller.NavegacaoController;
import jogodamemoria.view.JanelaMenuPrincipal;

public class Main {
    public static void main(String[] args) {
        JanelaMenuPrincipal menu = new JanelaMenuPrincipal();      

        new NavegacaoController(menu);

        menu.setVisible(true);
    }
}