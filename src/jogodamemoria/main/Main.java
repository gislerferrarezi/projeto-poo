package src.jogodamemoria.main;

import src.jogodamemoria.controller.NavegacaoController;
import src.jogodamemoria.view.JanelaMenuPrincipal;

public class Main {
    public static void main(String[] args) {
        JanelaMenuPrincipal menu = new JanelaMenuPrincipal();   

        new NavegacaoController(menu);
    
        menu.setVisible(true);
    }   
}