package bancoImobiliario;

import java.util.ArrayList;
import java.util.List;

public class Regras {

	private int vez = 0;
	private List<Jogadores> jogadores = new ArrayList();
	private List<Territorio> territorios = new ArrayList();
	private int numJogadores;
	private int dadosIguais = 0;
	private List<Integer> valoresDeCompra = new ArrayList();
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

	public void dadosIguais(){
		setDadosIguais( dadosIguais + 1 );
	}

	public int getDadosIguais() {
		return dadosIguais;
	}

	public void setDadosIguais( int dadosIguais ) {
		this.dadosIguais = dadosIguais;
	}

	public List<Integer> getValoresDeCompra() {
		return valoresDeCompra;
	}

	public void createTerritorios() {
		for( int i = 0; i < 40; i++ ){
			Territorio territorio = new Territorio();
			territorios.add( territorio );


			switch ( i ){
				case 1: //Leblon
					territorio.setNome("Leblon");
					territorio.setCusto( 100 );
					break;
				case 3: //AV. Presidente Vargas
					territorio.setNome("AV. Presidente Vargas");
					territorio.setCusto( 60 );
					break;
				case 4: //AV. Nossa S. de Copacabana
					territorio.setNome("AV. Nossa S. de Copacabana");
					territorio.setCusto( 60 );
					break;
				case 5: //Companhia Ferroviária
					territorio.setNome("Companhia Ferroviária");
					territorio.setCusto( 200 );
					break;
				case 6: //AV. Brigadeiro Faria Lima
					territorio.setNome("AV. Brigadeiro Faria Lima");
					territorio.setCusto( 240 );
					break;
				case 7: //Companhia de Viação
					territorio.setNome("Companhia de Viação");
					territorio.setCusto( 200 );
					break;
				case 8: //AV. Rebouça
					territorio.setNome("AV. Rebouça");
					territorio.setCusto( 220 );
					break;
				case 9: //AV. 9 de Julho
					territorio.setNome("AV. 9 de Julho");
					territorio.setCusto( 220 );
					break;
				case 11: //AV. Europa
					territorio.setNome("AV. Europa");
					territorio.setCusto( 200 );
					break;
				case 13: //Rua Augusta
					territorio.setNome("Rua Augusta");
					territorio.setCusto( 180 );
					break;
				case 14: //AV. Pacaembu
					territorio.setNome("AV. Pacaembu");
					territorio.setCusto( 180 );
					break;
				case 15: //Companhia de Táxi
					territorio.setNome("Companhia de Táxi");
					territorio.setCusto( 150 );
					break;
				case 17: //Interlagos
					territorio.setNome("Interlagos");
					territorio.setCusto( 350 );
					break;
				case 19: //Morumbi
					territorio.setNome("Morumbi");
					territorio.setCusto( 400 );
					break;
				case 21: //Flamengo
					territorio.setNome("Flamengo");
					territorio.setCusto( 120 );
					break;
				case 23: //Botafogo
					territorio.setNome("Botafogo");
					territorio.setCusto( 100 );
					break;
				case 25: //Companhia de Navegação
					territorio.setNome("Companhia de Navegação");
					territorio.setCusto( 150 );
					break;
				case 26: //AV. Brasil
					territorio.setNome("AV. Brasil");
					territorio.setCusto( 160 );
					break;
				case 28: //AV. Paulista
					territorio.setNome("AV. Paulista");
					territorio.setCusto( 140 );
					break;
				case 29: //Jardim Europa
					territorio.setNome("Jardim Europa");
					territorio.setCusto( 140 );
					break;
				case 31: //Copacabana
					territorio.setNome("Copacabana");
					territorio.setCusto( 260 );
					break;
				case 32: //Companhia de Aviação
					territorio.setNome("Companhia de Aviação");
					territorio.setCusto( 200 );
					break;
				case 33: //AV. Vieira Souto
					territorio.setNome("AV. Vieira Souto");
					territorio.setCusto( 320 );
					break;
				case 34: //AV. Atlântica
					territorio.setNome("AV. Atlântica");
					territorio.setCusto( 320 );
					break;
				case 35: //Companhia de Táxi Aéreo
					territorio.setNome("Companhia de Táxi Aéreo");
					territorio.setCusto( 200 );
					break;
				case 36: //Ipanema
					territorio.setNome("Ipanema");
					territorio.setCusto( 300 );
					break;
				case 38: //Jardim Paulista
					territorio.setNome("Jardim Paulista");
					territorio.setCusto( 280 );
					break;
				case 39: //Brooklin
					territorio.setNome("Brooklin");
					territorio.setCusto( 260 );
					break;
				default:
					territorio.setCusto( 0 );
					break;
			}
		}
	}
}
