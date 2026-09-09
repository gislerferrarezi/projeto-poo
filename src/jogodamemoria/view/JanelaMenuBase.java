package jogodamemoria.view;

import javax.swing.*;
import java.awt.*;

import jogodamemoria.view.componentes.BotaoArredondado;
import jogodamemoria.view.componentes.Cores;
import jogodamemoria.view.componentes.GerenciadorFontes;
import jogodamemoria.view.componentes.PainelComFundo;
import jogodamemoria.view.componentes.PainelVidro;

public abstract class JanelaMenuBase extends PainelComFundo {

    protected final PainelVidro cardBotoes;
    protected final PainelVidro cardRegras;
    protected final BotaoArredondado btnVoltar;

    public JanelaMenuBase(String titulo) {
        super("/jogodamemoria/recursos/imagens/fundo.png");

        setLayout(new BorderLayout(0, 15));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // TÍTULO
        PainelVidro painelTitulo = new PainelVidro();
        painelTitulo.setLayout(new BorderLayout());
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(GerenciadorFontes.obterFonte(Font.BOLD, 38f));
        lblTitulo.setForeground(Cores.TEXTO_BRANCO);
        painelTitulo.add(lblTitulo, BorderLayout.CENTER);

        JPanel wrapperTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapperTitulo.setOpaque(false);
        wrapperTitulo.add(painelTitulo);
        add(wrapperTitulo, BorderLayout.NORTH);

        // PAINEL CENTRAL
        JPanel painelConteudo = new JPanel(new GridLayout(1, 2, 35, 0));
        painelConteudo.setOpaque(false);

        // Lado Esquerdo: Card de Botões
        cardBotoes = new PainelVidro();
        cardBotoes.setLayout(new GridBagLayout());
        cardBotoes.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        // Lado Direito: Card de Regras
        cardRegras = new PainelVidro();

        painelConteudo.add(cardBotoes);
        painelConteudo.add(cardRegras);

        JPanel wrapperConteudo = new JPanel(new GridBagLayout());
        wrapperConteudo.setOpaque(false);
        GridBagConstraints gbcWrap = new GridBagConstraints();
        gbcWrap.weightx = 1.0;
        gbcWrap.weighty = 1.0;
        gbcWrap.fill = GridBagConstraints.BOTH;
        gbcWrap.insets = new Insets(10, 20, 10, 20);
        wrapperConteudo.add(painelConteudo, gbcWrap);

        add(wrapperConteudo, BorderLayout.CENTER);

        // RODAPÉ: BOTÃO VOLTAR
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        painelRodape.setOpaque(false);

        btnVoltar = new BotaoArredondado("Voltar", new Dimension(150, 48));
        btnVoltar.setFont(GerenciadorFontes.obterFonte(Font.BOLD, 17f));

        painelRodape.add(btnVoltar);
        add(painelRodape, BorderLayout.SOUTH);
    }

    protected void adicionarBotaoOpcao(BotaoArredondado botao, int indiceY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = indiceY;
        gbc.insets = new Insets(12, 0, 12, 0);
        gbc.anchor = GridBagConstraints.CENTER; 
        cardBotoes.add(botao, gbc);
    }

    public JButton getBtnVoltar() {
        return btnVoltar;
    }
}