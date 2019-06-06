package bancoImobiliario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import java.util.Random;

public class Dados extends Regras{
	private int dierolled = 0;
	
	public int dieRoll() {
		Random rand = new Random();
		int dr = rand.nextInt(6);
		dr +=1;
		dierolled = 1;
		return dr;
	}
	
	public void dieShow(int m, int n) {
		if(m != 0 && n !=0)
			System.out.printf("Die rolled %d and %d\n", m, n);
		else
			System.out.println("Die not rolled properly \n");
	}
	
	public int getDierolled() {
		return dierolled;
	}
}
