package src.jogodamemoria.model;

public class Jogador {
    
    String nome;
    int pontuacao;

    public Jogador(String nome) {
        this.nome = nome;
        this.pontuacao = 0;
    }

    public void ganharPonto() {
        pontuacao++;
    }
    
    public void resetarPontos() {
        pontuacao = 0;
    }

    public String getNome() { return nome; }
    public int getPontuacao() { return pontuacao; }
}
