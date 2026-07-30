package jogodamemoria.model;

public class Carta {

    private int id;
    private String valor;
    private boolean virada;
    private boolean descoberta;
    private Tipo_Carta tipo;

    public enum Tipo_Carta {
        NORMAL,
        PERDEU_A_VEZ,
        JOGUE_DE_NOVO,
        DOBRO_PONTOS
    }

    public Carta(int id, String valor) {
        this.id = id;
        this.valor = valor;
        this.tipo = Tipo_Carta.NORMAL;
        this.virada = false;
        this.descoberta = false;
    }

    public Carta(int id, String valor, Tipo_Carta tipo_carta) {
        this.id = id;
        this.valor = valor;
        this.tipo = tipo_carta;
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
    public Tipo_Carta getTipo() { return this.tipo; }
    public boolean isVirada() { return virada; }
    public boolean isDescoberta() { return descoberta; }
    public void setDescoberta(boolean descoberta) { this.descoberta = descoberta; }    
}