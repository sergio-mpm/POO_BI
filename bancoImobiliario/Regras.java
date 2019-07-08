package bancoImobiliario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Regras {

	private int vez = 0;
	private List<Jogadores> jogadores = new ArrayList();
	private List<Territorio> territorios = new ArrayList();
	private List<SorteReves> sorteReves = new ArrayList();
	private SorteReves saidaLivreDaPrisao;
	private int numJogadores;
	private int dadosIguais = 0;
	private List<Integer> valoresDeCompra = new ArrayList();
	private int banco = 50000;
	private int casas = 0;
	private int hoteis = 0;


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

	public void recebeDoBanco( Jogadores jogador, int custo ){
		custo = custo * -1;
		pagaAoBanco( jogador, custo );
	}

	public void pagaAoBanco( Jogadores jogador, int custo ){
		int dinheiroJogador = jogador.getDinheiro();
		int dinheiroBanco = getBanco();

		jogador.setDinheiro( dinheiroJogador - custo );
		setBanco( dinheiroBanco + custo );
	}

	public void vaiParaPrisao( Jogadores jogador ){
		jogador.setTerritorio( 10 );
		jogador.setStatusPrisao( 1 );
		setDadosIguais( 0 );
	}

	public int jogadorNaPrisao( Jogadores jogador, int dado1, int dado2 ){

		int statusPrisao = 0;
		int statusJogador = jogador.getStatusPrisao();

		if( jogador.getStatusPrisao() > 0 ){
			if ( !jogador.isLivreDaPrisao() ){
				if( dado1 == dado2  ){
					statusPrisao = 0;
				}

				else{
					statusPrisao = statusJogador + 1;
					jogador.setStatusPrisao( statusJogador + 1 );
				}

				if( jogador.getStatusPrisao() == 4 ){
					pagaAoBanco( jogador, 50 );
					statusPrisao = 0;
					jogador.setStatusPrisao( 0 );
				}
			}
			else{
				statusPrisao = 0;
				jogador.setStatusPrisao( 0 );
				jogador.setLivreDaPrisao( false );
				adicionarSaidaLivreDaPrisao();
			}
		}
		return statusPrisao;
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

	public List<SorteReves> getSorteReves(){
		return sorteReves;
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

	public void rearranjarDeck(){
		SorteReves sorteReves = this.sorteReves.get( 0 );
		this.sorteReves.remove( sorteReves );
		this.sorteReves.add( this.sorteReves.size(), sorteReves );
	}


	public int getBanco() {
		return banco;
	}

	public void setBanco( int banco ) {
		this.banco = banco;
	}

	public SorteReves getSaidaLivreDaPrisao(){
		return saidaLivreDaPrisao;
	}

	public void setSaidaLivreDaPrisao( SorteReves saidaLivreDaPrisao ){
		this.saidaLivreDaPrisao = saidaLivreDaPrisao;
	}

	public int getCasas() {
		return casas;
	}

	public void setCasas( int casas ) {
		this.casas = casas;
	}

	public int getHoteis() {
		return hoteis;
	}

	public void setHoteis( int hoteis ) {
		this.hoteis = hoteis;
	}

	public void createTerritorios() {
		for( int i = 0; i < 40; i++ ){
			Territorio territorio = new Territorio();
			territorios.add( territorio );

			switch ( i ){
				case 1:
					territorio.setNome("Leblon");
					territorio.setCusto( 100 );
					territorio.setAluguel( 6 );
					break;
				case 3:
					territorio.setNome("AV. Presidente Vargas");
					territorio.setCusto( 60 );
					territorio.setAluguel( 2 );
					break;
				case 4:
					territorio.setNome("AV. Nossa S. de Copacabana");
					territorio.setCusto( 60 );
					territorio.setAluguel( 4 );
					break;
				case 5:
					territorio.setNome("Companhia Ferroviária");
					territorio.setCusto( 200 );
					territorio.setAluguel( 50 );
					break;
				case 6:
					territorio.setNome("AV. Brigadeiro Faria Lima");
					territorio.setCusto( 240 );
					territorio.setAluguel( 20 );
					break;
				case 7:
					territorio.setNome("Companhia de Viação");
					territorio.setCusto( 200 );
					territorio.setAluguel( 50 );
					break;
				case 8:
					territorio.setNome("AV. Rebouça");
					territorio.setCusto( 220 );
					territorio.setAluguel( 18 );
					break;
				case 9:
					territorio.setNome("AV. 9 de Julho");
					territorio.setCusto( 220 );
					territorio.setAluguel( 18 );
					break;
				case 11:
					territorio.setNome("AV. Europa");
					territorio.setCusto( 200 );
					territorio.setAluguel( 16 );
					break;
				case 13:
					territorio.setNome("Rua Augusta");
					territorio.setCusto( 180 );
					territorio.setAluguel( 14 );
					break;
				case 14:
					territorio.setNome("AV. Pacaembu");
					territorio.setCusto( 180 );
					territorio.setAluguel( 14 );
					break;
				case 15:
					territorio.setNome("Companhia de Táxi");
					territorio.setCusto( 150 );
					territorio.setAluguel( 40 );
					break;
				case 17:
					territorio.setNome("Interlagos");
					territorio.setCusto( 350 );
					territorio.setAluguel( 35 );
					break;
				case 19:
					territorio.setNome("Morumbi");
					territorio.setCusto( 400 );
					territorio.setAluguel( 50 );
					break;
				case 21:
					territorio.setNome("Flamengo");
					territorio.setCusto( 120 );
					territorio.setAluguel( 8 );
					break;
				case 23:
					territorio.setNome("Botafogo");
					territorio.setCusto( 100 );
					territorio.setAluguel( 6 );
					break;
				case 25:
					territorio.setNome("Companhia de Navegação");
					territorio.setCusto( 150 );
					territorio.setAluguel( 40 );
					break;
				case 26:
					territorio.setNome("AV. Brasil");
					territorio.setCusto( 160 );
					territorio.setAluguel( 12 );
					break;
				case 28:
					territorio.setNome("AV. Paulista");
					territorio.setCusto( 140 );
					territorio.setAluguel( 10 );
					break;
				case 29:
					territorio.setNome("Jardim Europa");
					territorio.setCusto( 140 );
					territorio.setAluguel( 10 );
					break;
				case 31:
					territorio.setNome("Copacabana");
					territorio.setCusto( 260 );
					territorio.setAluguel( 22 );
					break;
				case 32:
					territorio.setNome("Companhia de Aviação");
					territorio.setCusto( 200 );
					territorio.setAluguel( 50 );
					break;
				case 33:
					territorio.setNome("AV. Vieira Souto");
					territorio.setCusto( 320 );
					territorio.setAluguel( 28 );
					break;
				case 34:
					territorio.setNome("AV. Atlântica");
					territorio.setCusto( 320 );
					territorio.setAluguel( 26 );
					break;
				case 35:
					territorio.setNome("Companhia de Táxi Aéreo");
					territorio.setCusto( 200 );
					territorio.setAluguel( 50 );
					break;
				case 36:
					territorio.setNome("Ipanema");
					territorio.setCusto( 300 );
					territorio.setAluguel( 26 );
					break;
				case 38:
					territorio.setNome("Jardim Paulista");
					territorio.setCusto( 280 );
					territorio.setAluguel( 24 );
					break;
				case 39:
					territorio.setNome("Brooklin");
					territorio.setCusto( 260 );
					territorio.setAluguel( 22 );
					break;
				default:
					territorio.setCusto( 0 );
					break;
			}
		}
	}

	public void createSorteReves(){
		for( int i = 1; i <= 4; i++ ) {
			SorteReves sorteReves = new SorteReves();
			sorteReves.setId(i);
			this.sorteReves.add( sorteReves );

			switch( i ){
				case 1:
					sorteReves.setValor( 25 );
					break;
				case 2:
					sorteReves.setValor( 0 );
					setSaidaLivreDaPrisao( sorteReves );
					break;
				case 3:
					sorteReves.setValor( 80 );
					break;
				case 4:
					sorteReves.setValor( 200 );
					break;
				case 5:
					sorteReves.setValor( 50 );
					break;
				case 6:
					sorteReves.setValor( 50 );
					break;
				case 7:
					sorteReves.setValor( 100 );
					break;
				case 8:
					sorteReves.setValor( 100 );
					break;
				case 9:
					sorteReves.setValor( 0 );
					break;
				case 10:
					sorteReves.setValor( 1 );
					break;
				case 11:
					sorteReves.setValor( 2 );
					break;
				case 12:
					sorteReves.setValor( 45 );
					break;
				case 13:
					sorteReves.setValor( 100 );
					break;
				case 14:
					sorteReves.setValor( 100 );
					break;
				case 15:
					sorteReves.setValor( 20 );
					break;
				case 16:
					sorteReves.setValor( -15 );
					break;
				case 17:
					sorteReves.setValor( -25 );
					break;
				case 18:
					sorteReves.setValor( -45 );
					break;
				case 19:
					sorteReves.setValor( -30 );
					break;
				case 20:
					sorteReves.setValor( -100 );
					break;
				case 21:
					sorteReves.setValor( -100 );
					break;
				case 22:
					sorteReves.setValor( -40 );
					break;
				case 23:
					sorteReves.setValor( 3 );
					break;
				case 24:
					sorteReves.setValor( -30 );
					break;
				case 25:
					sorteReves.setValor( -50 );
					break;
				case 26:
					sorteReves.setValor( -25 );
					break;
				case 27:
					sorteReves.setValor( -30 );
					break;
				case 28:
					sorteReves.setValor( -45 );
					break;
				case 29:
					sorteReves.setValor( -50 );
					break;
				case 30:
					sorteReves.setValor( -50 );
					break;
			}
		}
//		Collections.shuffle( sorteReves );
	}

	public void adicionarSaidaLivreDaPrisao(){
			sorteReves.add( saidaLivreDaPrisao );
			Collections.shuffle( sorteReves );
	}

	public void removerSaidaLivreDaPrisao() {
		for( Jogadores jogador : jogadores ){
			if( jogador.isLivreDaPrisao() && sorteReves.contains( saidaLivreDaPrisao ) ){
				sorteReves.remove( saidaLivreDaPrisao );
			}
		}
	}

}
