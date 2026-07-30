package jogodamemoria.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JanelaCreditos extends JPanel {

    private JButton btnVoltar;

    public JanelaCreditos() {        
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- NORTE: Título principal ---
        JLabel lblTitulo = new JLabel("JOGO DA MEMÓRIA", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        add(lblTitulo, BorderLayout.NORTH);

        // --- CENTRO: Informações dos desenvolvedores e da matéria ---
        JPanel painelTexto = new JPanel(new GridLayout(3, 1, 10, 10));
        
        JLabel lblDesenvolvedores = new JLabel("Desenvolvido por: Gisler e Manu", JLabel.CENTER);
        JLabel lblDisciplina = new JLabel("Disciplina: Programação Orientada a Objetos", JLabel.CENTER);
        JLabel lblProfessor = new JLabel("Professor: Paiola", JLabel.CENTER);

        Font fonteTexto = new Font("Arial", Font.PLAIN, 18);
        lblDesenvolvedores.setFont(fonteTexto);
        lblDisciplina.setFont(fonteTexto);
        lblProfessor.setFont(fonteTexto);

        painelTexto.add(lblDesenvolvedores);
        painelTexto.add(lblDisciplina);
        painelTexto.add(lblProfessor);
        
        add(painelTexto, BorderLayout.CENTER);

        // --- SUL: Botão de Voltar ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        
        painelBotoes.add(btnVoltar);
        add(painelBotoes, BorderLayout.SOUTH);
    }
   
    public JButton getBtnVoltar() {
        return btnVoltar;
    }
}