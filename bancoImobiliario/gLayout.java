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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static java.lang.System.exit;

public class gLayout extends JPanel{
	private Image i = null;
	private List<Image> pinos = new ArrayList();
	private List<Image> dados = new ArrayList();
	private gLayout p = this;
	private Regras regras = new Regras();
	private List<Integer> coordJogadores = new ArrayList();
	private int dado1 = 6;
	private int dado2 = 6;

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
			regras.setCoordenadasDosTerritorios();
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

				int x = e.getX();
				int y = e.getY();
				int vez = regras.getVez();
				Dados dados = new Dados();
				dado1 = dados.dieRoll();
				dado2 = dados.dieRoll();
				int dadosTotal = dado1 + dado2;
				Jogadores jogador = regras.getJogadores().get( vez );
				int territorioJog = jogador.getTerritorio();
				int novoTerritorio = territorioJog + dadosTotal;

				if( novoTerritorio > 39 ){
					novoTerritorio -= 40;
				}

				novoTerritorio = ( novoTerritorio > 40 ) ? novoTerritorio - 40 : novoTerritorio;
				jogador.setTerritorio( novoTerritorio );

				int coordX = regras.getNovaCoordenadaDoJogador( jogador )[0];
				int coordY = regras.getNovaCoordenadaDoJogador( jogador )[1];

				coordJogadores.set( vez * 2, coordX );
				coordJogadores.set( ( vez * 2 ) + 1, coordY );

				regras.setCoordenadasDosTerritorios();


				System.out.println(x+" | "+y);

				repaint();
			}
		});
	}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(i,0,0,null);
			int numJogadores = regras.getNumJogadores();
			for( int i = 0; i < numJogadores*2; i = i + 2 ) {
				int coordX = coordJogadores.get( i );
				int coordY = coordJogadores.get( i + 1 );

				g2d.drawImage( pinos.get( i / 2 ), coordX, coordY, null );
				g2d.drawImage( dados.get( dado1 - 1 ), 200, 200, null );
				g2d.drawImage( dados.get( dado2 - 1 ), 400, 200, null );
			}

			regras.passaVez();
		}

	public Integer startGame(){
		try {
			String msg = "Quantos jogadores?\nMin: 2   Max: 6";
			int numJogadores = Integer.parseInt( JOptionPane.showInputDialog(p, msg) );
			regras.setVez( numJogadores );
			regras.setCoordenadasDosTerritorios();
			for( int i = 0; i < numJogadores; i++ ) {
				coordJogadores.add(595 + (i * 15));
				coordJogadores.add(635);
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
}