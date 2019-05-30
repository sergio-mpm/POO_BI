package bancoImobiliario;

import javax.swing.*;
import java.awt.*;

public class bancoImobiliario extends JFrame {
	public final int LARG_DEFAULT=800;
	public final int ALT_DEFAULT=800;
	
	public bancoImobiliario() {
		setTitle("Banco Imobiliário");
		   Toolkit tk=Toolkit.getDefaultToolkit();
		   Dimension screenSize=tk.getScreenSize();
		   int sl=screenSize.width;
		   int sa=screenSize.height;
		   int x=sl/2-LARG_DEFAULT/2;
		   int y=sa/2-ALT_DEFAULT/2;	  
		   setBounds(x,y,LARG_DEFAULT,ALT_DEFAULT);
		   setDefaultCloseOperation(EXIT_ON_CLOSE);  
		   getContentPane().add(new gLayout());
		   setVisible(true);
	}
	
	public static void main(String args[]) {
		//bancoImobiliario jg = new bancoImobiliario();
		Dados r = new Dados();
		if(r.getDierolled() == 0)
			System.out.println("Die not yet rolled.");
		for(int test = 0; test < 100; test++) {
			r.dieShow(r.dieRoll(),r.dieRoll()); //teste para verificação da rolagem de dado.
		}
	}
}
