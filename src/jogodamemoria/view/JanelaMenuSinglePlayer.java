package jogodamemoria.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import jogodamemoria.view.componentes.BotaoArredondado;

public class JanelaMenuSinglePlayer extends JanelaMenuBase {

    private final BotaoArredondado btnJogarFacil;
    private final BotaoArredondado btnJogarPadrao;
    private final JLabel lblRegrasConteudo;

    private static final String REGRAS_PADRAO_INICIAL = "<html><div style='text-align: center; color: #2D1B4E; font-family: Segoe UI;'>"
            + "<span style='font-size: 32px; font-weight: bold;'>INSTRUÇÕES</span><br><br><br>"
            + "<span style='font-size: 20px;'>Passe o mouse sobre um dos modos ao lado para visualizar as regras e detalhes da partida.</span>"
            + "</div></html>";

    private static final String REGRAS_FACIL = "<html><div style='text-align: center; color: #2D1B4E; font-family: Segoe UI;'>"
            + "<span style='font-size: 32px; font-weight: bold;'>MODO FÁCIL</span><br><br><br>"
            + "<span style='font-size: 19px;'>O objetivo é encontrar todos os pares o mais rápido possível, competindo contra o tempo e você mesmo.<br><br><br>"
            + "<b>Regras:</b> Este nível conta com <b>6 pares</b> (12 cartas no total), tentativas ilimitadas e sem limite de tempo.</span>"
            + "</div></html>";

    private static final String REGRAS_PADRAO = "<html><div style='text-align: center; color: #2D1B4E; font-family: Segoe UI;'>"
            + "<span style='font-size: 32px; font-weight: bold;'>MODO PADRÃO</span><br><br><br>"
            + "<span style='font-size: 19px;'>As mesmas regras do modo fácil se aplicam aqui, porém com um desafio muito maior para a sua mente.<br><br><br>"
            + "<b>Regras:</b> São <b>12 pares</b> (24 cartas no total). Prepare-se para testar sua concentração ao máximo!</span>"
            + "</div></html>";

    public JanelaMenuSinglePlayer() {
        super("SELECIONE A DIFICULDADE");

        // Botões no Card Esquerdo
        btnJogarFacil = new BotaoArredondado("Modo Fácil", new Dimension(320, 65));
        btnJogarPadrao = new BotaoArredondado("Modo Padrão", new Dimension(320, 65));

        adicionarBotaoOpcao(btnJogarFacil, 0);
        adicionarBotaoOpcao(btnJogarPadrao, 1);

        // Regras no Card Direito
        cardRegras.setLayout(new BorderLayout());
        cardRegras.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        lblRegrasConteudo = new JLabel(REGRAS_PADRAO_INICIAL, SwingConstants.CENTER);
        cardRegras.add(lblRegrasConteudo, BorderLayout.CENTER);

        // Efeito Hover
        adicionarEfeitoHover(btnJogarFacil, REGRAS_FACIL);
        adicionarEfeitoHover(btnJogarPadrao, REGRAS_PADRAO);
    }

    private void adicionarEfeitoHover(JButton botao, String textoRegras) {
        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblRegrasConteudo.setText(textoRegras);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblRegrasConteudo.setText(REGRAS_PADRAO_INICIAL);
            }
        });
    }

    public JButton getBtnJogarFacil() {
        return btnJogarFacil;
    }

    public JButton getBtnJogarPadrao() {
        return btnJogarPadrao;
    }
}