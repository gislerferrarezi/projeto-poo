package jogodamemoria.view.componentes;

import java.awt.*;
import javax.swing.*;

public class CaixasDeDialogo {

    // DIÁLOGO PARA SINGLEPLAYER 
    public static String pedirNomeSingle(Component parent) {
        JDialog dialog = criarDialogoBase(parent);
        PainelVidro painelFundo = criarPainelBase();

        JTextField txtNome = estilizarTextField();

        painelFundo.add(criarLabelTitulo("Identificação do Jogador"));
        painelFundo.add(Box.createRigidArea(new Dimension(0, 15)));

        painelFundo.add(criarLabelSubtitulo("Digite seu nome para começar:"));
        painelFundo.add(Box.createRigidArea(new Dimension(0, 6))); // Padronizado em 6px
        painelFundo.add(txtNome);

        painelFundo.add(Box.createRigidArea(new Dimension(0, 20)));

        final String[] resultado = { null };
        painelFundo.add(criarPainelBotoes(dialog, "Começar", "Cancelar", () -> resultado[0] = txtNome.getText()));

        exibirDialogo(dialog, painelFundo, parent);
        return resultado[0];
    }

    // DIÁLOGO PARA MULTIPLAYER
    public static String[] pedirNomesMulti(Component parent) {
        JDialog dialog = criarDialogoBase(parent);
        PainelVidro painelFundo = criarPainelBase();

        JTextField txtP1 = estilizarTextField();
        JTextField txtP2 = estilizarTextField();

        painelFundo.add(criarLabelTitulo("Jogadores"));
        painelFundo.add(Box.createRigidArea(new Dimension(0, 15)));

        // Bloco Jogador 1
        painelFundo.add(criarLabelSubtitulo("Nome do Jogador 1:"));
        painelFundo.add(Box.createRigidArea(new Dimension(0, 6))); // Padronizado em 6px
        painelFundo.add(txtP1);

        painelFundo.add(Box.createRigidArea(new Dimension(0, 15)));

        // Bloco Jogador 2
        painelFundo.add(criarLabelSubtitulo("Nome do Jogador 2:"));
        painelFundo.add(Box.createRigidArea(new Dimension(0, 6))); // Padronizado em 6px
        painelFundo.add(txtP2);

        painelFundo.add(Box.createRigidArea(new Dimension(0, 20)));

        final String[][] resultado = { null };
        painelFundo.add(criarPainelBotoes(dialog, "Começar", "Cancelar",
                () -> resultado[0] = new String[] { txtP1.getText(), txtP2.getText() }));

        exibirDialogo(dialog, painelFundo, parent);
        return resultado[0];
    }

    // DIÁLOGO DE CONFIRMAÇÃO (Sim ou Não)
    public static boolean confirmarAcao(Component parent, String mensagem, String titulo) {
        JDialog dialog = criarDialogoBase(parent);
        PainelVidro painelFundo = criarPainelBase();

        JLabel lblMsg = new JLabel(mensagem);
        lblMsg.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblMsg.setForeground(Cores.TEXTO_BRANCO);
        lblMsg.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelFundo.add(criarLabelTitulo(titulo));
        painelFundo.add(Box.createRigidArea(new Dimension(0, 15)));
        painelFundo.add(lblMsg);
        painelFundo.add(Box.createRigidArea(new Dimension(0, 25)));

        final boolean[] confirmou = { false };
        painelFundo.add(criarPainelBotoes(dialog, "Sim", "Não", () -> confirmou[0] = true));

        exibirDialogo(dialog, painelFundo, parent);
        return confirmou[0];
    }

    // MÉTODOS AUXILIARES REUTILIZÁVEIS
    private static JDialog criarDialogoBase(Component parent) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        JDialog dialog = new JDialog(window, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 0));
        return dialog;
    }

    private static PainelVidro criarPainelBase() {
        PainelVidro painel = new PainelVidro();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        return painel;
    }

    private static JLabel criarLabelTitulo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lbl.setForeground(Cores.TEXTO_CIANO);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    private static JLabel criarLabelSubtitulo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setForeground(Cores.TEXTO_DESABILITADO);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    private static JPanel criarPainelBotoes(JDialog dialog, String textoEsq, String textoDir, Runnable acaoEsq) {
        BotaoArredondado btnEsq = new BotaoArredondado(textoEsq, new Dimension(130, 40));
        BotaoArredondado btnDir = new BotaoArredondado(textoDir, new Dimension(130, 40));

        // Ação da esquerda (Sim/Começar) executa a função e fecha
        btnEsq.addActionListener(e -> {
            if (acaoEsq != null)
                acaoEsq.run();
            dialog.dispose();
        });

        // Ação da direita (Não/Cancelar) apenas fecha
        btnDir.addActionListener(e -> dialog.dispose());

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        painel.setOpaque(false);
        painel.add(btnEsq);
        painel.add(btnDir);
        return painel;
    }

    private static JTextField estilizarTextField() {
        JTextField txt = new JTextField();
        txt.setMaximumSize(new Dimension(250, 35));
        txt.setPreferredSize(new Dimension(250, 35));
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txt.setBackground(new Color(8, 28, 48));
        txt.setForeground(Cores.TEXTO_BRANCO);
        txt.setCaretColor(Cores.TEXTO_BRANCO);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Cores.VIDRO_BORDA, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return txt;
    }

    private static void exibirDialogo(JDialog dialog, PainelVidro painel, Component parent) {
        dialog.add(painel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true); // Pausa a execução aqui até fechar
    }
}