package bancoImobiliario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;

public class Jogadores {
	protected int jogadores[];
	protected int falencia = 0;
	protected int vitoria = 0;
	protected int dinheiro;
	
	public Jogadores(int n) {
		for(int i = 0; i<n; i++) {
			jogadores[i] = 1;
			dinheiro = 10000;
		}
	}
	
	public void recebeDinheiro(int n) {
		this.dinheiro += n;
	}
	
	public void pagaJogador(int n, Jogadores j) {
		this.dinheiro -= n;
		j.recebeDinheiro(n);
	}
	
	public void pagaBanco(int n) {
		this.dinheiro -= n;
	}
}
