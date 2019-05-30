package bancoImobiliario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;

public class gLayout extends JPanel{
	private Image i = null;
	private gLayout p = this;
	
	public gLayout() {
		try {
			i=ImageIO.read(new File("tabuleiro"));
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		addMouseListener(new MouseListener(){
			public void mouseEntered(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				String msg = String.format("x=%d y=%d\n",e.getX(),e.getY());
				JOptionPane.showMessageDialog(p,msg);
			}
		});
		
	}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(i,0,0,null);
		}
	}