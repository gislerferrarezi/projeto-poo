package jogodamemoria.model;

public class Carta {

    private int id;
    private String valor;
    private boolean virada;
    private boolean descoberta;

    public Carta(int id, String valor) {
        this.id = id;
        this.valor = valor;
        this.virada = false;
        this.descoberta = false;
    }

    public void virar() {
        this.virada = true;
    }

    public void esconder() {
        if (!descoberta) {
            this.virada = false;
        }
    }

    public int getId() { return id; }
    public String getValor() { return valor; }
    public boolean isVirada() { return virada; }
    public boolean isDescoberta() { return descoberta; }
    public void setDescoberta(boolean descoberta) { this.descoberta = descoberta; }    
}