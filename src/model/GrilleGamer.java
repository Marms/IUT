package model;

import java.io.*;

import model.Corrector;
import travail.Saisie;
import model.GeneratorSudoku;

public class GrilleGamer extends GeneratorSudoku implements Serializable {
	private int[][] grilleJouer;

	public GrilleGamer() {
		super(3);
		initAttr();
	}

	public GrilleGamer(int nb) {
		super(4);
		initAttr();
	}

	public void initAttr(){
		grilleJouer = new int[NBCARREGRILLE][NBCARREGRILLE];
		initGrilleJouer(4);

	}

//------------------------------------------------ METHODE PRIVEE -----------------
	private void initGrilleJouer(int a) {
		int ligne, colone, i = 0,min;
		for (int z = 0; z < NBCARREGRILLE; z++) {
			do {
				min = numDebutI(z + 1);
				ligne = (int)(Math.random()*NBCARRE) + min;
				min = numDebutJ(z+1);
				colone = (int)(Math.random()*NBCARRE) + min;
				if (grilleJouer[ligne][colone]==0) {	
					i++;
					grilleJouer[ligne][colone] = getChiffre(ligne, colone);
				}
				}while (i<a);
			i = 0;
		}
	}

	public String toString() {
		String local = "grille\n\t  0  1  2  3  4  5  6  7  8\n";
		for (int i = 0; i < NBCARREGRILLE; i++) {
			if (i % NBCARRE == 0)
				local += " \t ---------------------------\n";
			local += i+"\t";
			for (int j = 0; j < NBCARREGRILLE; j++) {
				if (j%NBCARRE == 0) {
					local += " | ";
					if (j == NBCARREGRILLE-1)
						local += "\t"+j;
				}

				if (grilleJouer[i][j] == 0)
					local += "  ";
				else {
					local += " " + grilleJouer[i][j];
				}

			}
			local += "|\n";
		}
		return local+"\t  --------------------------\n";
	}

//------------------------------------------------------------- ACESSEURS -----------------
	public String getValeur(int i, int j) {
		String local = ""+grilleJouer[i][j];
		return local;
	}
	public int getTaille() {
		return NBCARRE;
	}
	public int getTailleTotal() {
		return NBCARREGRILLE;
	}
	/***/
	public void setGrille(int i, int j, int valeur) {
		if (i <= NBCARREGRILLE && j <= NBCARREGRILLE && i >= 0 && j >= 0) {
			grilleJouer[i][j] = valeur;
		}
	}
	public boolean verifGrille(){
		Corrector corr = new Corrector(grilleJouer);
		System.out.println(this);
		return corr.estValide();
	}
}

class Main {
	public static void main(String [] args) {
		int i,j,val;
		GrilleGamer sar1 = new GrilleGamer();
		System.out.println(""+sar1);
		while (true) {
			i = Saisie.lireInt("entrer ligne: ");
			j = Saisie.lireInt("entrer colone: ");
			val = Saisie.lireInt("enter valeur: ");
			sar1.setGrille(i,j,val);
			if (sar1.verifGrille()) {
				break;
			}
		}
 	}
}