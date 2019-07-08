package bancoImobiliario;

import java.util.ArrayList;
import java.util.List;

public class Jogadores {
	private int id;
	private boolean falencia = false;
	private int territorio = 0;
	private boolean livreDaPrisao = false;
	private int statusPrisao = 0;
	private List<Territorio> territoriosComprados = new ArrayList();
	private int dinheiro = 1000;


	public void setId( int id ) {
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

    public void setDinheiro( int dinheiro ) {
	    this.dinheiro = dinheiro;
    }

	public boolean isLivreDaPrisao() {
		return livreDaPrisao;
	}

	public void setLivreDaPrisao( boolean livreDaPrisao ) {
		this.livreDaPrisao = livreDaPrisao;
	}

	public int getStatusPrisao() {
		return statusPrisao;
	}

	public void setStatusPrisao( int statusPrisao ) {
		this.statusPrisao = statusPrisao;
	}

	public void addTerritorioComprado( Territorio territorio ){
		this.territoriosComprados.add( territorio );
	}

	public List<Territorio> getTerritoriosComprados() {
		return territoriosComprados;
	}

	public void setTerritoriosComprados( List<Territorio> territoriosComprados ){
		this.territoriosComprados = territoriosComprados;
	}
}
