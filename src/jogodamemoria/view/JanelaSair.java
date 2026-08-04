package jogodamemoria.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JanelaSair extends JDialog {

    private JButton btnSair;
    private JButton btnCancelar;

    public JanelaSair() {
        // Remove a barra nativa do sistema para o visual ficar 100% customizado
        setUndecorated(true);
        setModal(true);
        setSize(420, 240);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // --- PALETA DE CORES ---
        Color corFundoPrincipal = Color.decode("#CDB4F6");
        Color corFundoSecundario = Color.decode("#B794F4");
        Color corPainel = Color.decode("#A06CD5");
        Color corBotoes = Color.decode("#6D4AFF");
        Color corHover = Color.decode("#7C5CFF");
        Color textoClaro = Color.decode("#FFFFFF");

        // --- PAINEL EXTERNO (Fundo Principal) ---
        JPanel painelExterno = new JPanel(new BorderLayout());
        painelExterno.setBackground(corFundoPrincipal);
        painelExterno.setBorder(new EmptyBorder(15, 15, 15, 15));

        // --- PAINEL DA JANELA ---
        JPanel painelJanela = new JPanel(new BorderLayout());
        painelJanela.setBackground(corPainel);        

        // --- CABEÇALHO ---
        JPanel painelCabecalho = new JPanel(new BorderLayout());
        painelCabecalho.setPreferredSize(new Dimension(0, 45));
        painelCabecalho.setBackground(corFundoSecundario);        

        JLabel lblTitulo = new JLabel("CONFIRMAR SAÍDA");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(textoClaro);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        painelCabecalho.add(lblTitulo, BorderLayout.CENTER);

        // --- MENSAGEM (Centro) ---
        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.setBackground(corPainel);
        painelCentro.setBorder(new EmptyBorder(20, 20, 10, 20));

        JLabel lblMensagem = new JLabel("<html><center>Tem certeza de que deseja<br>sair do jogo?</center></html>");
        lblMensagem.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensagem.setForeground(textoClaro);
        lblMensagem.setFont(new Font("Arial", Font.BOLD, 18));
        painelCentro.add(lblMensagem, BorderLayout.CENTER);

        // --- BOTÕES (Sul) ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        painelBotoes.setBackground(corPainel);

        // Instanciando os botões com as variáveis e textos originais
        btnCancelar = criarBotao("Cancelar", corBotoes, corHover, textoClaro);
        btnSair = criarBotao("Sair", corBotoes, corHover, textoClaro);

        painelBotoes.add(btnSair);
        painelBotoes.add(btnCancelar);        

        // --- MONTAGEM ---
        painelJanela.add(painelCabecalho, BorderLayout.NORTH);
        painelJanela.add(painelCentro, BorderLayout.CENTER);
        painelJanela.add(painelBotoes, BorderLayout.SOUTH);

        painelExterno.add(painelJanela, BorderLayout.CENTER);
        add(painelExterno);
    }

    // Método auxiliar para criar botões com design e efeito Hover
    private JButton criarBotao(String texto, Color bg, Color hover, Color fg) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(130, 45));
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(fg, 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Aplicando o Hover
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hover); // Muda para a cor de Hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bg); // Volta para a cor original
            }
        });

        return btn;
    }

    public JButton getBtnSair() {
        return btnSair;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }
}