package bancoImobiliario;

import java.util.ArrayList;
import java.util.List;

public class Jogadores {
	private int id;
	private boolean falencia = false;
	private int territorio = 0;
	private boolean saidaDaPrisao = false;
	private List<Territorio> territoriosComprados = new ArrayList();
	private int nota1 = 8;
	private int nota5 = 10;
	private int nota10 = 10;
	private int nota50 = 10;
	private int nota100 = 8;
	private int nota500 = 2;
	private int dinheiro = 1000;


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

	public int getDinheiro(){
		return this.dinheiro;
	}

	public void addTerritorioComprado( Territorio territorio ){
	    this.territoriosComprados.add( territorio );
    }

    public void setDinheiro( int dinheiro ) {
	    this.dinheiro = dinheiro;
    }
}
