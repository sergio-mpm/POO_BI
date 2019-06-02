package bancoImobiliario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;

public class Jogadores {
	protected int falencia = 0;
	protected int vitoria = 0;
	protected int dinheiro;
	protected int territorio = 0;

	public int getTerritorio() {
		return territorio;
	}

	public void setTerritorio( int territorio ) {
		this.territorio = territorio;
	}

	public void recebeDinheiro(int dinheiro ) {
		this.dinheiro += dinheiro;
	}
	
	public void pagaJogador( int dinheiro, Jogadores outroJogador ) {
		pagaDinheiro( dinheiro );
		outroJogador.recebeDinheiro( dinheiro );
	}
	
	public void pagaDinheiro( int dinheiro ) {
		this.dinheiro -= dinheiro;
	}
}
