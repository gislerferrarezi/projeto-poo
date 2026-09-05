package jogodamemoria.model;

public class Jogador {
    
    private String nome;
    private int pontuacao;
    private int paresEncontrados;

    public Jogador(String nome) {
        this.nome = nome;
        this.pontuacao = 0;
        this.paresEncontrados = 0;
    }
  
    public void ganharPonto() {
        ganharPontos(1);
    }
   
    public void ganharPontos(int pontos) {
        this.pontuacao += pontos;
        this.paresEncontrados++; 
    }
    
    public void resetarPontos() {
        this.pontuacao = 0;
        this.paresEncontrados = 0;
    }

    public String getNome() { return nome; }
    public int getPontuacao() { return pontuacao; }
    public int getParesEncontrados() { return paresEncontrados; }
}