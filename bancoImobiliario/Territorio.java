package bancoImobiliario;

public class Territorio {
    private int id;
    private int custo;
    private int aluguel;
    private String nome;
    private boolean comprado = false;
    private int casas = 0;
    private boolean hotel = false;


    public void setCusto( int custo ) {
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

    public int getCasas() {
        return casas;
    }

    public void setCasas( int casas ) {
        this.casas = casas;
    }

    public boolean temHotel() {
        return hotel;
    }

    public void setHotel( boolean hotel ) {
        this.hotel = hotel;
    }

    public int getAluguel() {
        return aluguel;
    }

    public void setAluguel(int aluguel) {
        this.aluguel = aluguel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
