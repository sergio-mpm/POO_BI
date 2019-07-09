package bancoImobiliario;

import java.util.Random;
import java.awt.*;

public class SorteReves {
	private int idCarta;
	private int valorPgto;
	private boolean goPosicao;
	private static SorteReves[] lst;
	private static SorteReves[] brl;
	
	private SorteReves(int id, int valor, boolean go) {
		idCarta = id;
		valorPgto = valor;
		goPosicao = go;
	}
  
  private int valor;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
	
	public static SorteReves [] listaSR() {
		lst = new SorteReves[30];
		
		lst[0] = new SorteReves(1,25,false);
		lst[1] = new SorteReves(2,150,false);
		lst[2] = new SorteReves(3,80,false);
		lst[3] = new SorteReves(4,200,false);
		lst[4] = new SorteReves(5,50,false);
		lst[5] = new SorteReves(6,50,false);
		lst[6] = new SorteReves(7,100,false);
		lst[7] = new SorteReves(8,100,false);
		lst[8] = new SorteReves(9,0,true);
		lst[9] = new SorteReves(10,200,false);
		lst[10] = new SorteReves(11,50,false);
		lst[11] = new SorteReves(12,45,false);
		lst[12] = new SorteReves(13,100,false);
		lst[13] = new SorteReves(14,100,false);
		lst[14] = new SorteReves(15,20,false);
		lst[15] = new SorteReves(16,-15,false);
		lst[16] = new SorteReves(17,-25,false);
		lst[17] = new SorteReves(18,-45,false);
		lst[18] = new SorteReves(19,-30,false);
		lst[19] = new SorteReves(20,-100,false);
		lst[20] = new SorteReves(21,-100,false);
		lst[21] = new SorteReves(22,-40,false);
		lst[22] = new SorteReves(23,0,true);
		lst[23] = new SorteReves(24,-30,false);
		lst[24] = new SorteReves(25,-50,false);
		lst[25] = new SorteReves(26,-25,false);
		lst[26] = new SorteReves(27,-30,false);
		lst[27] = new SorteReves(28,-45,false);
		lst[28] = new SorteReves(29,-50,false);
		lst[29] = new SorteReves(30,-50,false);
		
		return lst;
	}
	
	public static SorteReves drawSorteReves(int c) {
		int idx = 0;
		while(lst[idx].idCarta != c) {
			if(idx>30)
				return null;
			idx++;
		}
		if(lst[idx]!=null)
			System.out.printf("Carta: %d - Ganha ou Perde: %d \n", lst[idx].idCarta,lst[idx].valorPgto);
		if(lst[idx]!=null)
			return lst[idx];
		else if(lst[idx] == null) {
			System.out.println("ERROR: FILE NOT FOUND.\n");
			return null;
		}
		return lst[idx];
	}
	
	public static void vaiPrisao(SorteReves carta) {
		if(carta.goPosicao == true)
			System.out.println("PRISAO");
		else if(carta.goPosicao == false)
			System.out.println("FREEDOM");
	}
	
	public static int randomGenerator(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min)+1)+min;
	}
	
	public void baralhoSorteReves() {
		brl = new SorteReves[30];
		
		for(int idx = 1; idx <= 30;idx++) {
		int rand = randomGenerator(1,30);
			for(int jdx = 1; jdx <=idx;jdx++) {
				if(brl[jdx] == lst[rand]) {
					rand = randomGenerator(1,30);
				}
			}
			brl[idx] = lst[rand];
		}
	}
	
	public void drawBaralho() {
		
	}
}
