package bancoImobiliario;

import java.util.ArrayList;
import java.util.List;

public class Regras {

	private int vez = 0;
	private List<Jogadores> jogadores = new ArrayList<Jogadores>();
	private List<Territorio> territorios = new ArrayList<Territorio>();
	private int numJogadores;

	public void setPlayers( int numJogadores ) {
		setNumJogadores( numJogadores );
		for( int i = 0; i < numJogadores; i++ ) {
			Jogadores jog = new Jogadores();
			jogadores.add( jog );
		}
	}

	public int getVez(){
		return this.vez;
	}

	public void setVez( int vez ){
		this.vez = vez;
	}

	public void passaVez(){
		this.vez += 1;
		if( vez > numJogadores - 1 ){
			this.vez = 0;
		}
	}

	public List<Jogadores> getJogadores() {
		return jogadores;
	}

	public int getNumJogadores() {
		return this.numJogadores;
	}

	public void setNumJogadores( int numJogadores ) {
		this.numJogadores = numJogadores;
	}

	public void setTerritorios(){
		for( int i = 0; i < 40; i++ ){
			Territorio territorio = new Territorio();
			territorio.setCoordX( 605 );
			territorio.setCoordY( 655 );
			if( i == 10 ){
				territorio.setCoordX( 10 );
				territorio.setCoordY( 10 );
			}
			territorios.add( territorio );
		}
	}

	public List<Territorio> getTerritorios() {
		return territorios;
	}
}
