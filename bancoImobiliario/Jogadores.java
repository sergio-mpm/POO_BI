package bancoImobiliario;

public class Jogadores {
	private int id;
	private boolean falencia = false;
	private int territorio = 0;
	private boolean saidaDaPrisao = false;
	private int nota1 = 8;
	private int nota5 = 10;
	private int nota10 = 10;
	private int nota50 = 10;
	private int nota100 = 8;
	private int nota500 = 2;


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

}
