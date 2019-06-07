package bancoImobiliario;

import java.util.ArrayList;
import java.util.List;

public class Regras {

	private int vez = 0;
	private List<Jogadores> jogadores = new ArrayList();
	private List<Territorio> territorios = new ArrayList();
	private int numJogadores;

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

	public int[] getNovaCoordenadaDoJogador(Jogadores jogador ){
		Territorio territorio = getTerritorios().get(jogador.getTerritorio());
		int id = jogador.getId();
		int[] coord = new int[2];


		switch ( id ) {
			case 0:
				coord[0] = territorio.getCoordX();
				coord[1] = territorio.getCoordY();
				break;
			case 1:
				coord[0] = territorio.getCoordX() + 15;
				coord[1] = territorio.getCoordY();
				break;
			case 2:
				coord[0] = territorio.getCoordX() + 30;
				coord[1] = territorio.getCoordY();
				break;
			case 3:
				if( jogador.getTerritorio() % 10 != 0 ) {
					coord[0] = territorio.getCoordX();
					coord[1] = territorio.getCoordY() + 20;
				}
				else{
					coord[0] = territorio.getCoordX() + 45;
					coord[1] = territorio.getCoordY();
				}
				break;
			case 4:
				if( jogador.getTerritorio() % 10 != 0 ) {
					coord[0] = territorio.getCoordX() + 15;
					coord[1] = territorio.getCoordY() + 20;
				}
				else{
					coord[0] = territorio.getCoordX() + 60;
					coord[1] = territorio.getCoordY();
				}
				break;

			case 5:
				if( jogador.getTerritorio() % 10 != 0 ) {
					coord[0] = territorio.getCoordX() + 30;
					coord[1] = territorio.getCoordY() + 20;
				}
				else {
					coord[0] = territorio.getCoordX() + 75;
					coord[1] = territorio.getCoordY();
				}
				break;
			}

		return coord;
	}

	public void createTerritorios() {
		for( int i = 0; i < 40; i++ ){
			Territorio territorio = new Territorio();
			territorios.add( territorio );
		}
	}

	public void setCoordenadasDosTerritorios(){
		for( int i = 0; i < 40; i++ ){

			Territorio territorio = new Territorio();
			switch( i ) {
				case 0:
					territorio.setCoordX( 595 );
					territorio.setCoordY( 635 );
					break;
				case 1:
					territorio.setCoordX( 545 );
					territorio.setCoordY( 635 );
					break;
				case 2:
					territorio.setCoordX( 490 );
					territorio.setCoordY( 635 );
					break;
				case 3:
					territorio.setCoordX( 435 );
					territorio.setCoordY( 635 );
					break;
				case 4:
					territorio.setCoordX( 380 );
					territorio.setCoordY( 635 );
					break;
				case 5:
					territorio.setCoordX( 325 );
					territorio.setCoordY( 635 );
					break;
				case 6:
					territorio.setCoordX( 265 );
					territorio.setCoordY( 635 );
					break;
				case 7:
					territorio.setCoordX( 210 );
					territorio.setCoordY( 635 );
					break;
				case 8:
					territorio.setCoordX( 155 );
					territorio.setCoordY( 635 );
					break;
				case 9:
					territorio.setCoordX( 100 );
					territorio.setCoordY( 635 );
					break;
				case 10:
					territorio.setCoordX( 5 );
					territorio.setCoordY( 655 );
					break;
				case 11:
					territorio.setCoordX( 15 );
					territorio.setCoordY( 540 );
					break;
				case 12:
					territorio.setCoordX( 15 );
					territorio.setCoordY( 485 );
					break;
				case 13:
					territorio.setCoordX( 15 );
					territorio.setCoordY( 430 );
					break;
				case 14:
					territorio.setCoordX( 15 );
					territorio.setCoordY( 375 );
					break;
				case 15:
					territorio.setCoordX( 15 );
					territorio.setCoordY( 315 );
					break;
				case 16:
					territorio.setCoordX( 15 );
					territorio.setCoordY( 260 );
					break;
				case 17:
					territorio.setCoordX( 15 );
					territorio.setCoordY( 205 );
					break;
				case 18:
					territorio.setCoordX( 15 );
					territorio.setCoordY( 155 );
					break;
				case 19:
					territorio.setCoordX( 15 );
					territorio.setCoordY( 95 );
					break;
				case 20:
					territorio.setCoordX( 5 );
					territorio.setCoordY( 40 );
					break;
				case 21:
					territorio.setCoordX( 100 );
					territorio.setCoordY( 20 );
					break;
				case 22:
					territorio.setCoordX( 155 );
					territorio.setCoordY( 20 );
					break;
				case 23:
					territorio.setCoordX( 210 );
					territorio.setCoordY( 20 );
					break;
				case 24:
					territorio.setCoordX( 265 );
					territorio.setCoordY( 20 );
					break;
				case 25:
					territorio.setCoordX( 320 );
					territorio.setCoordY( 20 );
					break;
				case 26:
					territorio.setCoordX( 375 );
					territorio.setCoordY( 20 );
					break;
				case 27:
					territorio.setCoordX( 430 );
					territorio.setCoordY( 20 );
					break;
				case 28:
					territorio.setCoordX( 490 );
					territorio.setCoordY( 20 );
					break;
				case 29:
					territorio.setCoordX( 545 );
					territorio.setCoordY( 20 );
					break;
				case 30:
					territorio.setCoordX( 600 );
					territorio.setCoordY( 20 );
					break;
				case 31:
					territorio.setCoordX( 630 );
					territorio.setCoordY( 95 );
					break;
				case 32:
					territorio.setCoordX( 630 );
					territorio.setCoordY( 155 );
					break;
				case 33:
					territorio.setCoordX( 630 );
					territorio.setCoordY( 205 );
					break;
				case 34:
					territorio.setCoordX( 630 );
					territorio.setCoordY( 260 );
					break;
				case 35:
					territorio.setCoordX( 630 );
					territorio.setCoordY( 315 );
					break;
				case 36:
					territorio.setCoordX( 630 );
					territorio.setCoordY( 375 );
					break;
				case 37:
					territorio.setCoordX( 630 );
					territorio.setCoordY( 430 );
					break;
				case 38:
					territorio.setCoordX( 630 );
					territorio.setCoordY( 485 );
					break;
				case 39:
					territorio.setCoordX( 630 );
					territorio.setCoordY( 540 );
					break;
			}

			territorios.add( territorio );
		}
	}
}
