package bancoImobiliario;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import static java.lang.System.exit;

public class gLayout extends JPanel{
	private Image i = null;
	private List<Image> pinos = new ArrayList();
	private List<Image> territorios = new ArrayList();
	private List<Image> sorteReves = new ArrayList();
	private List<Image> dados = new ArrayList();
	private gLayout p = this;
	private Regras regras = new Regras();
	private List<Integer> coordJogadores = new ArrayList();
	private List<Integer> coordTerritorios = new ArrayList();
	private int dado1 = 6;
	private int dado2 = 6;
	private List<Color> colors = new ArrayList( Arrays.asList( Color.RED, Color.BLUE, Color.YELLOW, Color.decode( "#bd00af" ), Color.decode( "#696969" ), Color.decode( "#ff6700" ) ) );

	public gLayout() {
		try {
			i=ImageIO.read(new File("tabuleiro.png"));

			// Leitura das imagens dos pinos
			List<File> pinoFolder = Files.walk(Paths.get("pinos")).filter(Files::isRegularFile)
					.map(Path::toFile)
					.collect(Collectors.toList());
			for( File file : pinoFolder ){
				pinos.add( ImageIO.read(file) );
			}

			// Leitura das imagens dos cartoes territorio
			List<File> territorioFolder = Files.walk(Paths.get("territorios")).filter(Files::isRegularFile)
					.map(Path::toFile)
					.collect(Collectors.toList());
			territorioFolder.sort( Comparator.comparing( File::getName ) );
			for( File file : territorioFolder ){
				territorios.add( ImageIO.read(file) );
			}

			// Leitura das imagens dos cartoes sorte e reves
			List<File> sorteRevesFolder = Files.walk(Paths.get("sorteReves")).filter(Files::isRegularFile)
					.map(Path::toFile)
					.collect(Collectors.toList());
			Collections.shuffle( sorteRevesFolder );
			for( File file : sorteRevesFolder ){
				sorteReves.add( ImageIO.read(file) );
			}

			// Leitura das imagens dos dados
			List<File> dadoFolder = Files.walk(Paths.get("dados")).filter(Files::isRegularFile)
					.map(Path::toFile)
					.collect(Collectors.toList());
			for( File file : dadoFolder ){
				dados.add( ImageIO.read(file) );
			}




			int numJogadores = 0;

			while ( numJogadores < 2 || numJogadores > 6 ) {
				numJogadores = startGame();
			}
			regras.setPlayers( numJogadores );
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
			exit(1);
		}

		addMouseListener(new MouseListener(){
			public void mouseEntered(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {

				int vez = regras.getVez();
				Dados dados = new Dados();
				dado1 = dados.dieRoll();
				dado2 = dados.dieRoll();
//				dado1 = 6;
//				dado2 = 4;

				int dadosTotal = dado1 + dado2;
//				int dadosTotal = 1;
				Jogadores jogador = regras.getJogadores().get( vez );
				if( dado1 == dado2 ){
					regras.dadosIguais();
				}

				if( regras.getDadosIguais() != 3 && jogador.getTerritorio() != 30 ) {
					int territorioJog = jogador.getTerritorio();
					int novoTerritorio = territorioJog + dadosTotal;

					novoTerritorio = (novoTerritorio > 39) ? novoTerritorio - 40 : novoTerritorio;
					jogador.setTerritorio(novoTerritorio);
				}
				else{
					regras.vaiParaPrisao( jogador );
				}

				if( jogador.getTerritorio() == 30 ){
					regras.vaiParaPrisao( jogador );
				}

				setNovaCoordenadaDoJogador( jogador );

				int x = coordJogadores.get( vez * 2 );
				int y = coordJogadores.get( (vez * 2 )+ 1 );
				System.out.println(x+" | "+y+"  -  " + jogador.getTerritorio());

				if( regras.getDadosIguais() == 3 || dado1 != dado2 ) {
					regras.passaVez();
					regras.setDadosIguais(0);
				}
				repaint();
			}
		});
	}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(i,0,0,null);
			Rectangle corDaVez = new Rectangle( 180, 180, 350, 150 );
			int vez = regras.getVez();
			g2d.setPaint( colors.get( vez ) );
			int numJogadores = regras.getNumJogadores();
			for( int i = 0; i < numJogadores*2; i = i + 2 ) {
				int coordX = coordJogadores.get( i );
				int coordY = coordJogadores.get( i + 1 );

				g2d.drawImage( pinos.get( i / 2 ), coordX, coordY, null );
			}
			g2d.fill( corDaVez );
			g2d.drawImage( dados.get( dado1 - 1 ), 200, 200, null );
			g2d.drawImage( dados.get( dado2 - 1 ), 400, 200, null );
			Jogadores jogador;
			if( dado1 == dado2 ) {
				jogador = regras.getJogadorPorId( regras.getVez() );
			}
			else{
				jogador = regras.getJogadorPorId( regras.getVezPassada() );
			}
			int indexTerritorio = jogador.getTerritorio();

			Image imagemTerritorio = territorios.get(indexTerritorio);
			if( imagemTerritorio != null ) {
				g2d.drawImage( imagemTerritorio, 100, 300, null);
			}
			else if( indexTerritorio % 10 != 0 && indexTerritorio != 18 && indexTerritorio != 24 ){
				Image sorteRevesImagem = sorteReves.get(0);
				g2d.drawImage( sorteRevesImagem, 100, 300, null);
				sorteReves.remove( sorteReves.get( 0 ) );
				sorteReves.add( sorteReves.size(), sorteReves.get( 0 ) );
			}
		}

	public Integer startGame(){
		try {
			String msg = "Quantos jogadores?\nMin: 2   Max: 6";
			int numJogadores = Integer.parseInt( JOptionPane.showInputDialog(p, msg) );
			regras.createTerritorios();
			setInformacoesDosTerritorios();
			for( int i = 0; i < numJogadores; i++ ) {
				coordJogadores.add( 595 + ( i * 15 ) );
				coordJogadores.add( 635 );
			}

			return numJogadores;
		}
		catch( NumberFormatException e ) {
			String y = e.getMessage();
			if( y.equals( "null" ) || y == null ){
				exit(0);
			}
			return 0;
		}
	}

	public void setNovaCoordenadaDoJogador( Jogadores jogador ){
		int indexJogador = regras.getVez() * 2;
		int indexCoordenadas = jogador.getTerritorio() * 2;

		switch ( indexJogador / 2 ) {
			case 0:
				coordJogadores.set( indexJogador, coordTerritorios.get( indexCoordenadas ) );
				coordJogadores.set( indexJogador + 1, coordTerritorios.get( indexCoordenadas + 1 ) );
				break;
			case 1:
				coordJogadores.set( indexJogador, coordTerritorios.get( indexCoordenadas ) + 15 );
				coordJogadores.set( indexJogador + 1, coordTerritorios.get( indexCoordenadas + 1 ) );
				break;
			case 2:
				coordJogadores.set( indexJogador, coordTerritorios.get( indexCoordenadas ) + 30 );
				coordJogadores.set( indexJogador + 1, coordTerritorios.get( indexCoordenadas + 1 ) );
				break;
			case 3:
				if( jogador.getTerritorio() % 10 != 0 ) {
					coordJogadores.set( indexJogador, coordTerritorios.get( indexCoordenadas ) );
					coordJogadores.set( indexJogador + 1, coordTerritorios.get( indexCoordenadas + 1 ) + 20 );
				}
				else{
					coordJogadores.set( indexJogador, coordTerritorios.get( indexCoordenadas ) + 45 );
					coordJogadores.set( indexJogador + 1, coordTerritorios.get( indexCoordenadas + 1 ) );
				}
				break;
			case 4:
				if( jogador.getTerritorio() % 10 != 0 ) {
					coordJogadores.set( indexJogador, coordTerritorios.get( indexCoordenadas ) + 15 );
					coordJogadores.set( indexJogador + 1, coordTerritorios.get( indexCoordenadas + 1 ) + 20 );
				}
				else{
					coordJogadores.set( indexJogador, coordTerritorios.get( indexCoordenadas ) + 60 );
					coordJogadores.set( indexJogador + 1, coordTerritorios.get( indexCoordenadas + 1 ) );
				}
				break;

			case 5:
				if( jogador.getTerritorio() % 10 != 0 ) {
					coordJogadores.set( indexJogador, coordTerritorios.get( indexCoordenadas ) + 30 );
					coordJogadores.set( indexJogador + 1, coordTerritorios.get( indexCoordenadas + 1 ) + 20 );
				}
				else {
					coordJogadores.set( indexJogador, coordTerritorios.get( indexCoordenadas ) + 75 );
					coordJogadores.set( indexJogador + 1, coordTerritorios.get( indexCoordenadas + 1 ) );
				}
				break;
		}
	}

	public void setInformacoesDosTerritorios(){
		for( int i = 0; i < 40; i++ ){
			switch( i ) {
				case 0:
					coordTerritorios.add( 595 );
					coordTerritorios.add( 635 );
					territorios.add( i, null );
					break;
				case 1:
					coordTerritorios.add( 545 );
					coordTerritorios.add( 635 );
					break;
				case 2:
					coordTerritorios.add( 490 );
					coordTerritorios.add( 635 );
					territorios.add( i, null );
					break;
				case 3:
					coordTerritorios.add( 435 );
					coordTerritorios.add( 635 );
					break;
				case 4:
					coordTerritorios.add( 380 );
					coordTerritorios.add( 635 );
					break;
				case 5:
					coordTerritorios.add( 325 );
					coordTerritorios.add( 635 );
					break;
				case 6:
					coordTerritorios.add( 265 );
					coordTerritorios.add( 635 );
					break;
				case 7:
					coordTerritorios.add( 210 );
					coordTerritorios.add( 635 );
					break;
				case 8:
					coordTerritorios.add( 155 );
					coordTerritorios.add( 635 );
					break;
				case 9:
					coordTerritorios.add( 100 );
					coordTerritorios.add( 635 );
					break;
				case 10:
					coordTerritorios.add( 5 );
					coordTerritorios.add( 655 );
					territorios.add( i, null );
					break;
				case 11:
					coordTerritorios.add( 15 );
					coordTerritorios.add( 540 );
					break;
				case 12:
					coordTerritorios.add( 15 );
					coordTerritorios.add( 485 );
					territorios.add( i, null );
					break;
				case 13:
					coordTerritorios.add( 15 );
					coordTerritorios.add( 430 );
					break;
				case 14:
					coordTerritorios.add( 15 );
					coordTerritorios.add( 375 );
					break;
				case 15:
					coordTerritorios.add( 15 );
					coordTerritorios.add( 315 );
					break;
				case 16:
					coordTerritorios.add( 15 );
					coordTerritorios.add( 260 );
					territorios.add( i, null );
					break;
				case 17:
					coordTerritorios.add( 15 );
					coordTerritorios.add( 205 );
					break;
				case 18:
					coordTerritorios.add( 15 );
					coordTerritorios.add( 155 );
					territorios.add( i, null );
					break;
				case 19:
					coordTerritorios.add( 15 );
					coordTerritorios.add( 95 );
					break;
				case 20:
					coordTerritorios.add( 5 );
					coordTerritorios.add( 40 );
					territorios.add( i, null );
					break;
				case 21:
					coordTerritorios.add( 100 );
					coordTerritorios.add( 20 );
					break;
				case 22:
					coordTerritorios.add( 155 );
					coordTerritorios.add( 20 );
					territorios.add( i, null );
					break;
				case 23:
					coordTerritorios.add( 210 );
					coordTerritorios.add( 20 );
					break;
				case 24:
					coordTerritorios.add( 265 );
					coordTerritorios.add( 20 );
					territorios.add( i, null );
					break;
				case 25:
					coordTerritorios.add( 320 );
					coordTerritorios.add( 20 );
					break;
				case 26:
					coordTerritorios.add( 375 );
					coordTerritorios.add( 20 );
					break;
				case 27:
					coordTerritorios.add( 430 );
					coordTerritorios.add( 20 );
					territorios.add( i, null );
					break;
				case 28:
					coordTerritorios.add( 490 );
					coordTerritorios.add( 20 );
					break;
				case 29:
					coordTerritorios.add( 545 );
					coordTerritorios.add( 20 );
					break;
				case 30:
					coordTerritorios.add( 600 );
					coordTerritorios.add( 20 );
					territorios.add( i, null );
					break;
				case 31:
					coordTerritorios.add( 630 );
					coordTerritorios.add( 95 );
					break;
				case 32:
					coordTerritorios.add( 630 );
					coordTerritorios.add( 155 );
					break;
				case 33:
					coordTerritorios.add( 630 );
					coordTerritorios.add( 205 );
					break;
				case 34:
					coordTerritorios.add( 630 );
					coordTerritorios.add( 260 );
					break;
				case 35:
					coordTerritorios.add( 630 );
					coordTerritorios.add( 315 );
					break;
				case 36:
					coordTerritorios.add( 630 );
					coordTerritorios.add( 375 );
					break;
				case 37:
					coordTerritorios.add( 630 );
					coordTerritorios.add( 430 );
					territorios.add( i, null );
					break;
				case 38:
					coordTerritorios.add( 630 );
					coordTerritorios.add( 485 );
					break;
				case 39:
					coordTerritorios.add( 630 );
					coordTerritorios.add( 540 );
					break;
			}
		}
	}
}