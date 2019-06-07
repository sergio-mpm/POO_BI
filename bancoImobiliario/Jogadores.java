package bancoImobiliario;

public class Jogadores {
	private int id;
	private boolean falencia = false;
	private int dinheiro;
	private int territorio = 0;


	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public int getTerritorio() {
		return territorio;
	}

	public void setTerritorio( int territorio ) {
		this.territorio = territorio;
	}

	public void recebeDinheiro(int dinheiro ) {
		this.dinheiro += dinheiro;
	}
	
	public void pagaJogador( int dinheiro, Jogadores outroJogador ) {
		pagaDinheiro( dinheiro );
		outroJogador.recebeDinheiro( dinheiro );
	}
	
	public void pagaDinheiro( int dinheiro ) {
		this.dinheiro -= dinheiro;
	}
}
