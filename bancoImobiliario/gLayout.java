package bancoImobiliario;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
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
	private Map<Integer, Image> sorteReves = new HashMap();
	private List<Image> dados = new ArrayList();
	private gLayout p = this;
	private Regras regras = new Regras();
	private List<Integer> coordJogadores = new ArrayList();
	private List<Integer> coordTerritorios = new ArrayList();
	private int dado1 = 6;
	private int dado2 = 6;
	private List<Color> colors = new ArrayList( Arrays.asList( Color.RED, Color.BLUE, Color.YELLOW, Color.decode( "#bd00af" ), Color.decode( "#696969" ), Color.decode( "#ff6700" ) ) );
	private boolean primeiroPaint = true;
	private boolean vaiPontoPartidaSR = false;

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
			int cont = 0;
			for( File file : sorteRevesFolder ){
				cont++;
				sorteReves.put( cont, ImageIO.read(file) );
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
			List<SorteReves> listaSorteReves = regras.getSorteReves();
			Image sorteRevesImagem = sorteReves.get(listaSorteReves.get(0).getId());
			g2d.drawImage( sorteRevesImagem, 750, 200, null);
		}
		else if( vaiPontoPartidaSR ){
			Image sorteRevesImagem = sorteReves.get( 3 );
			g2d.drawImage( sorteRevesImagem, 750, 200, null );
			vaiPontoPartidaSR = false;
		}
		else{
			System.out.println("Territorio sem imagem");
		}

		if( !primeiroPaint ) {
			p.remove(0);
		}
		List<Jogadores> jogadoresList = regras.getJogadores();
		DefaultTableModel modelJogo = new DefaultTableModel();
		JTable statusDosJogadores = new JTable( modelJogo );

		modelJogo.addColumn("Jogador");
		modelJogo.addColumn("Dinheiro");

		for( Jogadores jog : jogadoresList ) {
			int index = jogadoresList.indexOf( jog ) + 1;
			String str1 = "Jogador " + index;
			String str2 = "R$ " + jog.getDinheiro() + ",00";

			modelJogo.addRow(new Object[]{str1, str2});
		}
		int alturaTabela = jogadoresList.size() * 16;
		statusDosJogadores.setBounds(200, 400, 150, alturaTabela );

		p.add( statusDosJogadores, 0 );

		Territorio territorio = regras.getTerritorios().get( indexTerritorio );
		String nome = "Nome: ---";
		String proprietario = "Proprietario: ---";
		String custoAlguel = "Custo: R$ " + territorio.getCusto() + ",00";
		String casas = "Casas: ---";
		for( Jogadores jog : jogadoresList ){

			if( jog.getTerritoriosComprados().contains( territorio ) ){
				int index = jogadoresList.indexOf( jog ) + 1;
				proprietario = "Proprietario: Jogador " + index;
				custoAlguel = "Aluguel: R$ " + territorio.getAluguel() + ",00";
				casas = "Casas: " + territorio.getCasas();
			}

		}
		if( territorio.getAluguel() == 0 ){
			custoAlguel = "Custo: ---";
		}
		else {
			nome = "Nome: " + territorio.getNome();
		}

		g2d.setFont( new Font("TimesRoman", Font.BOLD, 16 ) );
		g2d.setPaint( Color.BLACK );
		g2d.drawString( nome, 760, 530 );
		g2d.drawString( proprietario, 760, 550 );
		g2d.drawString( custoAlguel, 760, 570 );
		g2d.drawString( casas, 760, 590 );

		primeiroPaint = false;

		JButton rolarDados = new JButton("Rolar Dados");
		rolarDados.setBounds(300, 330, 110, 35);
		p.add( rolarDados, 1 );
		rolarDados.addActionListener(e -> movimentarJogador());

		JButton escolherDados = new JButton( "Escolher Dados" );
		escolherDados.setBounds(740, 50, 125, 35);
		p.add( escolherDados, 2 );
		escolherDados.addActionListener(e -> escolherDados());

//		JButton encerrarJogo = new JButton("Encerrar Jogo");
//		rolarDados.setBounds(740, 150, 120, 35);
//		p.add( rolarDados, 1 );
//		rolarDados.addActionListener(e -> encerrarJogo());
	}

	public Integer startGame(){
		try {
			String msg = "Quantos jogadores?\nMin: 2   Max: 6";
			int numJogadores = Integer.parseInt( JOptionPane.showInputDialog(p, msg) );
			regras.createTerritorios();
			setInformacoesDosTerritorios();
			regras.createSorteReves();
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

		if ( dado1 == dado2 ) {
			regras.dadosIguais();
		}

        int vez = regras.getVez();
        Jogadores jogador = regras.getJogadores().get( vez );

		int statusPrisao = regras.jogadorNaPrisao( jogador, dado1, dado2 );
		int territorioCorrente = jogador.getTerritorio();

		if( statusPrisao == 0 ) {

            if ( regras.getDadosIguais() != 3 && territorioCorrente != 30 ){
                int novoTerritorio = territorioCorrente + dadosTotal;
                int valorTransacaoBanco = 0;

                valorTransacaoBanco = ( novoTerritorio > 39 ) ? 200 : valorTransacaoBanco;
                valorTransacaoBanco = ( novoTerritorio == 18 ) ? 200 : valorTransacaoBanco;
                valorTransacaoBanco = ( novoTerritorio == 24 ) ? -200 : valorTransacaoBanco;

                novoTerritorio = ( novoTerritorio > 39 ) ? novoTerritorio - 40 : novoTerritorio;

				territorioCorrente += dadosTotal;
                regras.recebeDoBanco( jogador, valorTransacaoBanco );
                jogador.setTerritorio( novoTerritorio );
            }
            else{
                regras.vaiParaPrisao( jogador );
                JOptionPane popupMenu = new JOptionPane();
                JOptionPane.showConfirmDialog( popupMenu, "Você foi preso!", null, JOptionPane.PLAIN_MESSAGE );
            }

            if ( territorioCorrente == 30 ) {
                regras.vaiParaPrisao( jogador );
                JOptionPane popupMenu = new JOptionPane();
                JOptionPane.showConfirmDialog( popupMenu, "Você foi preso!", null, JOptionPane.PLAIN_MESSAGE );
            }

        }

		if( territorioCorrente == 2 || territorioCorrente == 12 || territorioCorrente == 16 || territorioCorrente == 22 || territorioCorrente == 27 || territorioCorrente == 37 ) {
			regras.rearranjarDeck();
		}

        caiuSorteReves( jogador );
        setNovaCoordenadaDoJogador( jogador );

        if ( regras.getDadosIguais() == 3 || dado1 != dado2 ){
            regras.passaVez();
            regras.setDadosIguais( 0 );
        }

		repaint();

		comprar( jogador, jogador.getTerritorio() );
	}

	private void caiuSorteReves( Jogadores jogador ) {
	    if( regras.getSaidaLivreDaPrisao() != null ){
	        regras.removerSaidaLivreDaPrisao();
        }
		SorteReves sorteReves = regras.getSorteReves().get( 0 );
		int valor = sorteReves.getValor();
		switch( jogador.getTerritorio() ){
			case 2:
			case 12:
			case 16:
			case 22:
			case 27:
			case 37:
				switch( valor ){
					case 0:
						jogador.setLivreDaPrisao( true );
						System.out.println("Saida Livre Da Prisao");
						break;
					case 1:
						jogador.setTerritorio( 0 );
						regras.recebeDoBanco( jogador, 200 );
						vaiPontoPartidaSR = true;
						System.out.println("Va Ao Ponto De Partida");
						break;
					case 2:
						for( Jogadores jog : regras.getJogadores() ){
							jog.setDinheiro( jog.getDinheiro() - 50 );
							jogador.setDinheiro( jogador.getDinheiro() + 50 );
						}
						System.out.println("Receba 50 De Cada Um");
						break;
					case 3:
						regras.vaiParaPrisao( jogador );
						System.out.println("Va Para A Prisao");
						break;
					default:
						regras.recebeDoBanco( jogador, valor );
						System.out.println("Receba Ou Pague " + valor);
						break;
				}
				break;
		}
	}

	private void escolherDados(){

		boolean dadosAceitos = false;
		JTextField dado1 = new JTextField();
		JTextField dado2 = new JTextField();
		Object[] msg = {
				"Dado 1:", dado1,
				"Dado 2:", dado2
		};

		while( !dadosAceitos ) {
			try {
				int ok = JOptionPane.showConfirmDialog(null, msg, "Escolher Dados", JOptionPane.OK_CANCEL_OPTION);
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

	private void encerrarJogo() {

	}

	private void comprar( Jogadores jogador, int indexTerritorio ) {
		Territorio territorio = regras.getTerritorios().get( indexTerritorio );
		int valorTerritorio = territorio.getCusto();
		boolean semDono = !territorio.isComprado();

		if( valorTerritorio != 0 && semDono ){
			String msg = "Deseja comprar " + territorio.getNome() + "?";
			JOptionPane popupMenu = new JOptionPane();
			int answer = JOptionPane.showConfirmDialog( popupMenu, msg, null, JOptionPane.YES_NO_OPTION );

			if( answer == JOptionPane.YES_OPTION ) {
			    int dinheiro = jogador.getDinheiro();
			    int custo = territorio.getCusto();

			    if( dinheiro < custo ){
                    JOptionPane pane = new JOptionPane();
                    JOptionPane.showConfirmDialog( pane, "Você não possui dinheiro suficiente", null, JOptionPane.PLAIN_MESSAGE );
                }
			    else {
                    territorio.setComprado(true);
                    jogador.addTerritorioComprado(territorio);
                    regras.pagaAoBanco(jogador, territorio.getCusto());
					p.repaint();
                }
			}
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