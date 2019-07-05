package bancoImobiliario;

public class Territorio {
    private int custo;
    private String nome;
    private boolean comprado = false;


    public void setCusto(int custo) {
        this.custo = custo;
    }

    public int getCusto() {
        return custo;
    }

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado( boolean comprado ) {
        this.comprado = comprado;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
