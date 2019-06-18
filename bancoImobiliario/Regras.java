package bancoImobiliario;

import java.util.ArrayList;
import java.util.List;

public class Regras {

	private int vez = 0;
	private List<Jogadores> jogadores = new ArrayList();
	private List<Territorio> territorios = new ArrayList();
	private int numJogadores;
	private int dadosIguais = 0;
	private int banco = 50000;
	private int casas = 32;
	private int hoteis = 12;


	public void setPlayers( int numJogadores ) {
		setNumJogadores( numJogadores );
		for( int i = 0; i < numJogadores; i++ ) {
			Jogadores jog = new Jogadores();
			jog.setId( i );
			jogadores.add( jog );
		}
	}

	public Jogadores getJogadorPorId( int id ){
		for( Jogadores jog : jogadores ){
			if( jog.getId() == id ){
				return jog;
			}
		}
		return null;
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

	public int getVezPassada(){
		int vez = getVez();
		if( vez == 0 ){
			return numJogadores - 1;
		}
		return vez - 1;
	}

	public void vaiParaPrisao( Jogadores jogador ){
		jogador.setTerritorio( 10 );
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

	public List<Territorio> getTerritorios() {
		return territorios;
	}

	public void createTerritorios() {
		for( int i = 0; i < 40; i++ ){
			Territorio territorio = new Territorio();
			territorios.add( territorio );
		}
	}

	public void dadosIguais(){
		setDadosIguais( dadosIguais + 1 );
	}

	public int getDadosIguais() {
		return dadosIguais;
	}

	public void setDadosIguais( int dadosIguais ) {
		this.dadosIguais = dadosIguais;
	}
}
