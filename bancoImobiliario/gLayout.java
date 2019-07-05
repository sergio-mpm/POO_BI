package bancoImobiliario;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

public class gLayout extends JPanel {
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
	private boolean primeiroPaint = true;

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
			public void mouseClicked(MouseEvent e) {}
		});
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		p.setLayout(null);
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

		Image imagemTerritorio = territorios.get( indexTerritorio );
		if( imagemTerritorio != null ) {
			g2d.drawImage( imagemTerritorio, 750, 200, null);
		}
		else if( indexTerritorio % 10 != 0 && indexTerritorio != 18 && indexTerritorio != 24 ){
			Image sorteRevesImagem = sorteReves.get(0);
			g2d.drawImage( sorteRevesImagem, 750, 200, null);
			sorteReves.remove( sorteReves.get( 0 ) );
			sorteReves.add( sorteReves.size(), sorteReves.get( 0 ) );
		}

		if( !primeiroPaint ) {
			p.remove(0);
		}
			List<Jogadores> jogadoresList = regras.getJogadores();
			DefaultTableModel model = new DefaultTableModel();
			JTable statusDosJogadores = new JTable(model);

			model.addColumn("C1");
			model.addColumn("C2");

			for( Jogadores jog : jogadoresList ) {
				int index = jogadoresList.indexOf(jog) + 1;
				String str1 = "Jogador " + index;
				String str2 = "R$ " + jog.getDinheiro() + ",00";

				model.addRow(new Object[]{str1, str2});
			}
			int alturaTabela = jogadoresList.size() * 16;
			statusDosJogadores.setBounds(200, 400, 150, alturaTabela);

			p.add( statusDosJogadores, 0 );
			primeiroPaint = false;


		JButton rolarDados = new JButton("Rolar Dados");
		rolarDados.setBounds(300, 330, 110, 35);
		p.add( rolarDados, 1 );
		rolarDados.addActionListener(e -> movimentarJogador());

		JButton escolherDados = new JButton( "Escolher Dados" );
		escolherDados.setBounds(740, 50, 125, 35);
		p.add( escolherDados, 2 );
		escolherDados.addActionListener(e -> escolherDados());
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

	public void movimentarJogador() {

		Dados dados = new Dados();
		dado1 = dados.dieRoll();
		dado2 = dados.dieRoll();

		movimentarJogador( dado1, dado2 );
	}

	public void movimentarJogador( int dado1, int dado2 ){

		int dadosTotal = dado1 + dado2;

		if (dado1 == dado2) {
			regras.dadosIguais();
		}

		int vez = regras.getVez();
		Jogadores jogador = regras.getJogadores().get( vez );

		if( regras.getDadosIguais() != 3 && jogador.getTerritorio() != 30 ) {
			int territorioJog = jogador.getTerritorio();
			int novoTerritorio = territorioJog + dadosTotal;

			novoTerritorio = (novoTerritorio > 39) ? novoTerritorio - 40 : novoTerritorio;
			jogador.setTerritorio(novoTerritorio);
		}
		else{
			regras.vaiParaPrisao( jogador );
            JOptionPane popupMenu = new JOptionPane();
            JOptionPane.showConfirmDialog( popupMenu, "Você foi preso!", null, JOptionPane.PLAIN_MESSAGE );
		}

		if( jogador.getTerritorio() == 30 ){
			regras.vaiParaPrisao( jogador );
            JOptionPane popupMenu = new JOptionPane();
            JOptionPane.showConfirmDialog( popupMenu, "Você foi preso!", null, JOptionPane.PLAIN_MESSAGE );
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

		comprar( jogador, jogador.getTerritorio() );
	}

	private void escolherDados(){

		boolean dadosAceitos = false;
		JTextField dado1 = new JTextField();
		JTextField dado2 = new JTextField();
		Object[] message = {
				"Dado 1:", dado1,
				"Dado 2:", dado2
		};

		while( !dadosAceitos ) {
			try {
				int ok = JOptionPane.showConfirmDialog(null, message, "Escolher Dados", JOptionPane.OK_CANCEL_OPTION);
				if ( ok == JOptionPane.OK_OPTION ) {

					int d1 = Integer.parseInt( dado1.getText() );
					int d2 = Integer.parseInt( dado2.getText() );

					if (d1 >= 1 && d1 <= 6 && d2 >= 1 && d2 <= 6) {
						this.dado1 = d1;
						this.dado2 = d2;

						dadosAceitos = true;
						movimentarJogador( d1, d2 );
					}

				} else {
					System.out.println("Dados Incorretos");
					dadosAceitos = true;
				}
			}
			catch( Exception e ) {
				System.out.println( "Input Incorreto!" );
			}
		}
	}

	private void comprar( Jogadores jogadores, int indexTerritorio ) {
		Territorio territorio = regras.getTerritorios().get( indexTerritorio );
		int valorTerritorio = territorio.getCusto();
		boolean semDono = !territorio.isComprado();

		if( valorTerritorio != 0 && semDono ){
			String msg = "Deseja comprar " + territorio.getNome() + "?";
			JOptionPane popupMenu = new JOptionPane();
			int answer = JOptionPane.showConfirmDialog(popupMenu, msg, null, JOptionPane.YES_NO_OPTION);

			if( answer == JOptionPane.YES_OPTION ) {
				territorio.setComprado( true );
				jogadores.addTerritorioComprado( territorio );
				int dinheiroAtual = jogadores.getDinheiro();
				jogadores.setDinheiro( dinheiroAtual - territorio.getCusto() );
			}
		}
		p.repaint();
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